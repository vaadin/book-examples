package com.vaadin.book.examples.advanced.spring;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
// TODO Spring
// @ApplicationScoped
public class SpringBroadcaster implements Serializable {
    private static final long serialVersionUID = -368230891317363146L;
    
    private Collection<UI> uis = new HashSet<UI>();
    
    public synchronized void register(UI listener) {
        uis.add(listener);
    }
    
    public synchronized void unregister(UI listener) {
        uis.remove(listener);
    }

    // TODO Spring
    @Qualifier
    // @Retention(RUNTIME)
    // @Target({PARAMETER, FIELD})
    public @interface OriginalSender {} 

    // Inject event to be fired
    @Autowired
    private javax.enterprise.event.Event<BroadcastMessage>
            messageEvent;

    ExecutorService executorService =
        Executors.newSingleThreadExecutor();

    // TODO Spring
    // Observe messages (only from clients)
    @SuppressWarnings("unused")
    private synchronized void observeMessage(
    //      @Observes @OriginalSender
            final BroadcastMessage message) {
        for (final UI listener: uis)
            executorService.execute(() ->
                listener.access(()->
                    messageEvent.fire(message)));
    }
}
// END-EXAMPLE: advanced.cdi.broadcasting
