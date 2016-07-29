package com.vaadin.book.examples.advanced.push;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//BEGIN-EXAMPLE: advanced.push.pusharound
@Push
@Widgetset("com.vaadin.book.MyAppWidgetset")
public class PushAroundUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;

    VerticalLayout messages = new VerticalLayout();
    
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);
        
        Panel messagesPanel = new Panel();
        messagesPanel.setSizeFull();
        messagesPanel.setContent(messages);
        content.addComponent(messagesPanel);
        content.setExpandRatio(messagesPanel, 1.0f);

        HorizontalLayout sendBar = new HorizontalLayout();
        sendBar.setWidth("100%");

        final TextField input = new TextField();
        input.setWidth("100%");
        sendBar.addComponent(input);
        sendBar.setExpandRatio(input, 1.0f);
        
        Button send = new Button("Send");
        send.setClickShortcut(KeyCode.ENTER);
        send.addClickListener(event -> {
            // Broadcast a message
            Broadcaster.broadcast(input.getValue());
            
            input.setValue("");
        });
        sendBar.addComponent(send);
        content.addComponent(sendBar);
        
        // Register to receive broadcasts
        Broadcaster.register(this::receiveBroadcast);
    }
    
    // Must also unregister when the UI expires    
    @Override
    public void detach() {
        Broadcaster.unregister(this::receiveBroadcast);
        super.detach();
    }

    public void receiveBroadcast(final String message) {
        // Must lock the session to execute logic safely
        access(() -> messages.addComponent(new Label(message)));
    }

    @WebServlet(urlPatterns = "/pusharound/*", name = "PushAroundUI", asyncSupported = true)
    @VaadinServletConfiguration(ui = PushAroundUI.class, productionMode = false)
    public static class PushAroundUIServlet extends VaadinServlet {
    }
}
// END-EXAMPLE: advanced.push.pusharound
