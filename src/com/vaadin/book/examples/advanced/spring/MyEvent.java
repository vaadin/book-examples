package com.vaadin.book.examples.advanced.spring;

import java.io.Serializable;

public class MyEvent implements Serializable {
    private static final long serialVersionUID = 1448862418313292393L;

    private String text;
    
    public MyEvent(String text) {
        this.text = text;
    }
    
    public String getName() {
        return text;
    }
}