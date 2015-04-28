package com.vaadin.book.examples.advanced.spring;

import java.io.Serializable;

// BEGIN-EXAMPLE: advanced.cdi.broadcasting
public class BroadcastMessage implements Serializable {
    private static final long serialVersionUID = 7612907361015425457L;

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
