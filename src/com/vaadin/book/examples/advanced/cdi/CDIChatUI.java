package com.vaadin.book.examples.advanced.cdi;

import javax.inject.Inject;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
@CDIUI("cdichat")
@Push
@Theme("valo")
public class CDIChatUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

    @Inject
    CDIBroadcaster broadcaster;
    
    @Inject
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
