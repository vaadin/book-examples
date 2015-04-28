package com.vaadin.book.examples.advanced.spring;

import javax.enterprise.event.Observes;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@UIScope
class DisplayPanel extends Panel {
    private static final long serialVersionUID = -2410712515068052908L;

    Label display = new Label("-nothing to display-");

    public DisplayPanel() {
        super("Display");
        setContent(display);
        setSizeFull();
    }
    
    //void myEventObserver(@Observes(notifyObserver = Reception.IF_EXISTS) MyEvent event) {
    // TODO Spring
    void myEventObserver(@Observes MyEvent event) {
        display.setValue("Observed: " + event.getName());
    }
}