package jp.mikuappend;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;
import mastodon4j.MastodonFactory;
import mastodon4j.streaming.UserStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hecateball
 */
@ApplicationScoped
public class Meteora {

    private static final Logger LOGGER = LoggerFactory.getLogger(Meteora.class);
    private UserStream userStream;

    public void onCreate(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        this.userStream = MastodonFactory.getInstance().streaming().userStream();
        this.userStream.register(new MikuAppend(context));
        this.userStream.open();
    }

    public void onDelete(@Observes @Destroyed(ApplicationScoped.class) ServletContext context) {
        try {
            this.userStream.close();
        } catch (IOException exception) {
            LOGGER.warn("Exception on close", exception);
        }
    }

}
