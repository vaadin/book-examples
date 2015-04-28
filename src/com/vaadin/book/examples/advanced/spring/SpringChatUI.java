package com.vaadin.book.examples.advanced.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
@SpringUI(path = "springchat")
@Push
@Theme("valo")
public class SpringChatUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

    @Autowired
    SpringBroadcaster broadcaster;
    
    @Autowired
    ChatBox chatbox;

    @Override
    protected void init(VaadinRequest request) {
        setContent(chatbox);
        
        // Register to receive broadcasts
        broadcaster.register(this);
    }

    // Must also unregister when the UI expires or is closed    
    @Override
    public void detach() {
        broadcaster.unregister(this);
        super.detach();
    }
}
// END-EXAMPLE: advanced.cdi.broadcasting
