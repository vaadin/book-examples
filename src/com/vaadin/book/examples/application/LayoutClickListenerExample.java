package com.vaadin.book.examples.application;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LayoutClickListenerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("doubleclick".equals(context))
            doubleclick(layout);
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }

    void basic(VerticalLayout rootLayout) {
        // BEGIN-EXAMPLE: application.eventlistener.clicklistener.basic
        // Layout for handling clicks
        VerticalLayout layout = new VerticalLayout();
        
        // Add some components
        layout.addComponent(new Label("I'm labeled"));
        layout.addComponent(new TextField("Field of text"));

        // Handle clicks
        layout.addListener(new LayoutClickListener() {
            private static final long serialVersionUID = 5527999180793601282L;

            @Override
            public void layoutClick(LayoutClickEvent event) {
                Notification.show("Click!");
            }
        });
        // END-EXAMPLE: application.eventlistener.clicklistener.basic
        
        rootLayout.addComponent(layout);
    }

    void doubleclick(VerticalLayout rootLayout) {
        // BEGIN-EXAMPLE: application.eventlistener.clicklistener.doubleclick
        // Layout for handling clicks
        VerticalLayout layout = new VerticalLayout();
        
        // Add some components
        layout.addComponent(new Label("I'm labeled"));
        layout.addComponent(new TextField("Field of text"));

        layout.addListener(new LayoutClickListener() {
            private static final long serialVersionUID = -7430315482468088485L;

            @Override
            public void layoutClick(LayoutClickEvent event) {
                if (event.isDoubleClick())
                    Notification.show("Double Click!");
                else
                    Notification.show("Single Click!");
            }
        });
        // END-EXAMPLE: application.eventlistener.clicklistener.doubleclick
        
        rootLayout.addComponent(layout);
    }
}
