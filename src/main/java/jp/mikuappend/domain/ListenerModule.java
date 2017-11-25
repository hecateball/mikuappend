package jp.mikuappend.domain;

import java.io.IOException;
import javax.enterprise.context.Dependent;
import mastodon4j.entity.Notification;
import mastodon4j.entity.Status;
import mastodon4j.streaming.UserStream;
import mastodon4j.streaming.UserStreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hecateball
 */
@Dependent
public class ListenerModule implements UserStreamListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerModule.class);

    public void listen(UserStream stream) {
        stream.register(this);
        stream.open();
    }

    public void unlisten(UserStream stream) {
        try {
            stream.close();
        } catch (IOException exception) {
            LOGGER.warn("Exception on close", exception);
        }
    }

    @Override
    public void onUpdate(Status status) {
        LOGGER.info("[{}]:{}", status.getAccount().getUserName(), status.getContent());
    }

    @Override
    public void onNotification(Notification notification) {
        switch (notification.getType()) {
            case "mention":
                LOGGER.debug("mention: {}", notification.getStatus().getContent());
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
