package jp.mikuappend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.Dependent;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import jp.mikuappend.vo.OHLC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hecateball
 */
@Dependent
public class Meteora implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Meteora.class);
    private static final Logger OHLC_LOGGER = LoggerFactory.getLogger(OHLC.class);
    private static final long INTERVAL = 300;
    @Resource
    private ManagedScheduledExecutorService service;

    public void startCollectingData() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        long delay = Duration.between(now.truncatedTo(ChronoUnit.SECONDS), now.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1L)).getSeconds();
        LOGGER.info("OHLC chart data collecing will start in {} seconds...", delay);
        this.service.scheduleAtFixedRate(this, delay, INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            long now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo")).truncatedTo(ChronoUnit.MINUTES).toEpochSecond();
            URL url = new URL(String.format("https://api.cryptowat.ch/markets/bitflyer/btcjpy/ohlc?periods=60&after=%d&before=%d", now - INTERVAL, now - 1));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != 200) {
                LOGGER.warn("unexpected response code: {}", connection.getResponseCode());
                return;
            }
            JsonObject json = Json.createReader(connection.getInputStream()).readObject();
            JsonObject allowance = json.getJsonObject("allowance");
            LOGGER.info("Allowance[cost={}, remaining={}]", allowance.getJsonNumber("cost").longValue(), allowance.getJsonNumber("remaining").longValue());
            JsonObject result = json.getJsonObject("result");
            if (result.isNull("60")) {
                LOGGER.warn("received no OHLC record.");
                return;
            }
            json.getJsonObject("result")
                    .getJsonArray("60")
                    .getValuesAs(JsonArray.class).stream()
                    .map(value -> {
                        OHLC ohlc = new OHLC();
                        ohlc.setCloseTime(value.getJsonNumber(0).longValue());
                        ohlc.setOpenPrice(value.getJsonNumber(1).doubleValue());
                        ohlc.setHighPrice(value.getJsonNumber(2).doubleValue());
                        ohlc.setLowPrice(value.getJsonNumber(3).doubleValue());
                        ohlc.setClosePrice(value.getJsonNumber(4).doubleValue());
                        ohlc.setVolume(value.getJsonNumber(5).doubleValue());
                        return ohlc;
                    })
                    .map(OHLC::toString)
                    .forEach(OHLC_LOGGER::info);
        } catch (IOException exception) {
            LOGGER.error("failed to get data", exception);
        }
    }

}
