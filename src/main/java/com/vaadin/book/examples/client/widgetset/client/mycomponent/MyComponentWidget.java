package com.vaadin.book.examples.client.widgetset.client.mycomponent;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;

// TODO extend any GWT Widget
public class MyComponentWidget extends Label {

    public static final String CLASSNAME = "mycomponent";

    public MyComponentWidget() {

        // setText("MyComponent sets the text via MyComponentConnector using MyComponentState");
        setStyleName(CLASSNAME);

    }
    
    Element imgElement = null;
    
    public void setMyIcon(String url) {
        if (imgElement == null) {
            imgElement = DOM.createImg();
            getElement().appendChild(imgElement);
        }
        
        DOM.setElementAttribute(imgElement, "src", url);
    }
}