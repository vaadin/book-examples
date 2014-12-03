package com.vaadin.book.examples.advanced.cdi;

import java.io.Serializable;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
public class BroadcastMessage implements Serializable {
    private static final long serialVersionUID = 1448862418313292393L;

    private String text;
    private Object sender;
    
    public BroadcastMessage(String text, Object sender) {
        this.text = text;
        this.sender = sender;
    }
    
    public String getText() {
        return text;
    }
    
    public void setSender(Object sender) {
        this.sender = sender;
    }
    
    public Object getSender() {
        return sender;
    }
}
// END-EXAMPLE: advanced.cdi.broadcasting
