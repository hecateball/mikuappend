package jp.mikuappend.vo;

import java.io.Serializable;

/**
 *
 * @author hecateball
 */
public class OHLC implements Serializable {

    private static final long serialVersionUID = 7945816495311454492L;
    private long closeTime;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double closePrice;
    private double volume;

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return String.format("{\"closeTime\": %d, \"openPrice\": %f, \"highPrice\": %f, \"lowPrice\": %f, \"closePrice\": %f, \"volume\": %f}",
                this.closeTime, this.openPrice, this.highPrice, this.lowPrice, this.closePrice, this.volume);
    }

}
