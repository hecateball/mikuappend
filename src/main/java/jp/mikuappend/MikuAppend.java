package jp.mikuappend;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jp.mikuappend.dialog.DialogRequest;
import jp.mikuappend.dialog.DialogResponse;
import mastodon4j.MastodonFactory;
import mastodon4j.entity.Notification;
import mastodon4j.entity.Status;
import mastodon4j.streaming.UserStreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hecateball
 */
public class MikuAppend implements UserStreamListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MikuAppend.class);
    private final Map<Long, String> contexts;
    private final String endpoint;
    private final String token;

    public MikuAppend(ServletContext context) {
        this.contexts = new HashMap<>();
        Properties properties = new Properties();
        try (InputStream input = context.getResourceAsStream("/WEB-INF/classes/mikuappend.properties")) {
            properties.load(input);
        } catch (IOException exception) {
            LOGGER.error("Failed to load properties", exception);
            throw new InternalServerErrorException(exception);
        }
        this.endpoint = properties.getProperty("docomo.api.endpoint");
        this.token = properties.getProperty("docomo.api.key");
    }

    @Override
    public void onUpdate(Status status) {
        LOGGER.info("[{}]:{}", status.getAccount().getUserName(), status.getContent());
    }

    @Override
    public void onNotification(Notification notification) {
        switch (notification.getType()) {
            case "mention":
                Status status = notification.getStatus();
                String content = status.getContent().replaceAll("<.+?>", "").replaceAll("^(@.+\\s)+", "");
                DialogRequest request = new DialogRequest();
                request.setMode("dialog");
                request.setUtt(content);
                request.setNickname(status.getAccount().getDisplayName());
                request.setNicknameY(status.getAccount().getUserName());
                request.setContext(this.contexts.getOrDefault(status.getAccount().getId(), ""));
                Response response = ClientBuilder.newBuilder().build()
                        .target(this.endpoint)
                        .path("/dialogue/v1/dialogue")
                        .queryParam("APIKEY", this.token)
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(request));
                switch (Response.Status.fromStatusCode(response.getStatus())) {
                    case OK:
                        DialogResponse dialogueResponse = response.readEntity(DialogResponse.class);
                        String reply = "@" + status.getAccount().getUserName() + " " + dialogueResponse.getUtt();
                        try {
                            MastodonFactory.getInstance()
                                    .postStatus(reply, Optional.of(status.getId()), null, true, null, null);
                        } catch (Exception exception) {
                            LOGGER.warn("exception at postStatus", exception);
                        }
                        this.contexts.put(status.getAccount().getId(), dialogueResponse.getContext());
                        return;
                    default:
                        LOGGER.warn("[status]:{}", response.getStatus());
                }
                return;
            default:
                LOGGER.debug("notification type: {}", notification.getType());
        }

    }

    @Override
    public void onDelete(long id) {
        LOGGER.info("onDelete:{}", id);
    }

}
