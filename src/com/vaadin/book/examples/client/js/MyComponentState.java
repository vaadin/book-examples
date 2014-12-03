package com.vaadin.book.examples.client.js;

import com.vaadin.shared.ui.JavaScriptComponentState;

//BEGIN-EXAMPLE: gwt.javascript.basic
/** Shared state class for MyComponent */
public class MyComponentState extends JavaScriptComponentState {
    private static final long serialVersionUID = -1260235093446938046L;

    private String value;
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
//END-EXAMPLE: gwt.javascript.basic
