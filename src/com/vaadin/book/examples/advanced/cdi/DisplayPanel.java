package com.vaadin.book.examples.advanced.cdi;

import javax.enterprise.event.Observes;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@UIScoped
class DisplayPanel extends Panel {
    private static final long serialVersionUID = -2410712515068052908L;

    Label display = new Label("-nothing to display-");

    public DisplayPanel() {
        super("Display");
        setContent(display);
        setSizeFull();
    }
    
    //void myEventObserver(@Observes(notifyObserver = Reception.IF_EXISTS) MyEvent event) {
    void myEventObserver(@Observes MyEvent event) {
        display.setValue("Observed: " + event.getName());
    }
}