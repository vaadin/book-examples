package com.vaadin.book.examples.advanced.spring;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class SpringExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("events".equals(context))
            events(layout);
        else if ("broadcasting".equals(context))
            broadcasting(layout);
        else if ("producers".equals(context))
            producers(layout);

        setCompositionRoot(layout);
    }
    
    // EXAMPLE-REF: advanced.spring.events com.vaadin.book.examples.advanced.spring.SpringEventUI advanced.spring.events
    // EXAMPLE-REF: advanced.spring.events com.vaadin.book.examples.advanced.spring.MyEvent advanced.spring.events
    // EXAMPLE-REF: advanced.spring.events com.vaadin.book.examples.advanced.spring.InputPanel advanced.spring.events
    // EXAMPLE-REF: advanced.spring.events com.vaadin.book.examples.advanced.spring.DisplayPanel advanced.spring.events
    void events(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("CDI Event UI");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/myspringuis/springevents"));
        frame.setWidth("500px");
        frame.setHeight("150px");

        layout.addComponent(frame);
    }

    // EXAMPLE-REF: advanced.spring.broadcasting com.vaadin.book.examples.advanced.spring.SpringChatUI advanced.spring.broadcasting
    // EXAMPLE-REF: advanced.spring.broadcasting com.vaadin.book.examples.advanced.spring.BroadcastMessage advanced.spring.broadcasting
    // EXAMPLE-REF: advanced.spring.broadcasting com.vaadin.book.examples.advanced.spring.SpringBroadcaster advanced.spring.broadcasting
    // EXAMPLE-REF: advanced.spring.broadcasting com.vaadin.book.examples.advanced.spring.ChatBox advanced.spring.broadcasting
    void broadcasting(VerticalLayout layout) {
        HorizontalLayout uis = new HorizontalLayout();
        uis.setSpacing(true);
        
        BrowserFrame frame1 = new BrowserFrame("UI 1");
        frame1.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/myspringuis/springchat"));
        frame1.setWidth("400px");
        frame1.setHeight("350px");
        uis.addComponent(frame1);

        BrowserFrame frame2 = new BrowserFrame("UI 2");
        frame2.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/myspringuis/springchat"));
        frame2.setWidth("400px");
        frame2.setHeight("350px");
        uis.addComponent(frame2);
        
        layout.addComponent(uis);
    }

    // EXAMPLE-REF: advanced.spring.producers com.vaadin.book.examples.advanced.spring.CDIProductionUI advanced.spring.producers
    void producers(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Spring Producer UI");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/myspringuis/springproducers"));
        frame.setWidth("500px");
        frame.setHeight("150px");

        layout.addComponent(frame);
    }
}
