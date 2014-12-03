package com.vaadin.book.examples.advanced.cdi;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Qualifier;

import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
@ApplicationScoped
public class CDIBroadcaster implements Serializable {
    private static final long serialVersionUID = -368230891317363146L;
    
    private Collection<UI> uis = new HashSet<UI>();
    
    public synchronized void register(UI listener) {
        uis.add(listener);
    }
    
    public synchronized void unregister(UI listener) {
        uis.remove(listener);
    }

    @Qualifier
    @Retention(RUNTIME)
    @Target({PARAMETER, FIELD})
    public @interface OriginalSender {} 

    // Inject event to be fired
    @Inject
    private javax.enterprise.event.Event<BroadcastMessage>
            messageEvent;

    ExecutorService executorService =
        Executors.newSingleThreadExecutor();

    // Observe messages (only from clients)
    @SuppressWarnings("unused")
    private synchronized void observeMessage(
            @Observes @OriginalSender
            final BroadcastMessage message) {
        for (final UI listener: uis)
            executorService.execute(() ->
                listener.access(()->
                    messageEvent.fire(message)));
    }
}
// END-EXAMPLE: advanced.cdi.broadcasting
