package jp.mikuappend;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import jp.mikuappend.domain.ListenerModule;
import jp.mikuappend.service.Meteora;
import mastodon4j.MastodonFactory;

/**
 *
 * @author hecateball
 */
@ApplicationScoped
public class MikuAppend {

    @Inject
    private ListenerModule listener;
    @Inject
    private Meteora meteora;

    public void onCreate(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        this.listener.listen(MastodonFactory.getInstance().streaming().userStream());
        this.meteora.startCollectingData();
    }

    public void onDelete(@Observes @Destroyed(ApplicationScoped.class) ServletContext context) {
        this.listener.unlisten(MastodonFactory.getInstance().streaming().userStream());
    }

}
