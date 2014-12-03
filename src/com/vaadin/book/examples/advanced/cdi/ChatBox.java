package com.vaadin.book.examples.advanced.cdi;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.book.examples.advanced.cdi.CDIBroadcaster.OriginalSender;
import com.vaadin.cdi.UIScoped;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
@UIScoped
class ChatBox extends CustomComponent {
    VerticalLayout messages = new VerticalLayout();
    
    public ChatBox() {
        setSizeFull();

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setCompositionRoot(content);

        Panel messagesPanel = new Panel();
        messagesPanel.setSizeFull();
        messagesPanel.setContent(messages);
        content.addComponent(messagesPanel);
        content.setExpandRatio(messagesPanel, 1.0f);

        HorizontalLayout sendBar = new HorizontalLayout();
        sendBar.setWidth("100%");

        TextField input = new TextField();
        input.setWidth("100%");
        sendBar.addComponent(input);
        sendBar.setExpandRatio(input, 1.0f);
        
        Button send = new Button("Send");
        send.setClickShortcut(KeyCode.ENTER);
        send.addClickListener(event -> { // Java 8
            // Broadcast the input to others
            broadcast(input.getValue());
            addMessage(input.getValue()); // Add to self
            
            input.setValue("");
        });
        sendBar.addComponent(send);
        content.addComponent(sendBar);
    }
    
    @Inject
    @OriginalSender
    private javax.enterprise.event.Event<BroadcastMessage>
        messageEvent;
    
    // Send a message
    private void broadcast(String msg) {
        messageEvent.fire(new BroadcastMessage(msg, this));
    }

    // Receive messages
    @SuppressWarnings("unused")
    private void observeMessage(
            @Observes BroadcastMessage event) {
        if (event.getSender() != this) // Not from self
            addMessage(event.getText());
    }

    private void addMessage(String msg) {
        messages.addComponent(new Label(msg));
    }
}
// END-EXAMPLE: advanced.cdi.broadcasting
