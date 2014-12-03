package com.vaadin.book.examples.advanced.cdi;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class CDIExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("navigation".equals(context))
            navigation(layout);
        else if ("events".equals(context))
            events(layout);
        else if ("broadcasting".equals(context))
            broadcasting(layout);
        else if ("producers".equals(context))
            producers(layout);

        setCompositionRoot(layout);
    }
    
    public static final String hierarchicalDescription =
            "<h1>Building UIs Hierarchically</h1>" +
            "<p></p>";
    
    // EXAMPLE-REF: advanced.cdi.navigation com.vaadin.book.examples.advanced.cdi.MyCDIUI advanced.cdi.navigation
    // EXAMPLE-REF: advanced.cdi.navigation com.vaadin.book.examples.advanced.cdi.LoginView advanced.cdi.navigation
    // EXAMPLE-REF: advanced.cdi.navigation com.vaadin.book.examples.advanced.cdi.MainView advanced.cdi.navigation
    void navigation(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("CDI View Navigation");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/mycdiuis?restartApplication"));
        frame.setWidth("570px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }

    // EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.CDIEventUI advanced.cdi.events
    // EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.MyEvent advanced.cdi.events
    // EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.InputPanel advanced.cdi.events
    // EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.DisplayPanel advanced.cdi.events
    void events(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("CDI Event UI");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/mycdiuis/cdievents"));
        frame.setWidth("500px");
        frame.setHeight("150px");

        layout.addComponent(frame);
    }

    // EXAMPLE-REF: advanced.cdi.broadcasting com.vaadin.book.examples.advanced.cdi.CDIChatUI advanced.cdi.broadcasting
    // EXAMPLE-REF: advanced.cdi.broadcasting com.vaadin.book.examples.advanced.cdi.BroadcastMessage advanced.cdi.broadcasting
    // EXAMPLE-REF: advanced.cdi.broadcasting com.vaadin.book.examples.advanced.cdi.CDIBroadcaster advanced.cdi.broadcasting
    // EXAMPLE-REF: advanced.cdi.broadcasting com.vaadin.book.examples.advanced.cdi.ChatBox advanced.cdi.broadcasting
    void broadcasting(VerticalLayout layout) {
        HorizontalLayout uis = new HorizontalLayout();
        uis.setSpacing(true);
        
        BrowserFrame frame1 = new BrowserFrame("UI 1");
        frame1.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/mycdiuis/cdichat"));
        frame1.setWidth("400px");
        frame1.setHeight("350px");
        uis.addComponent(frame1);

        BrowserFrame frame2 = new BrowserFrame("UI 2");
        frame2.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/mycdiuis/cdichat"));
        frame2.setWidth("400px");
        frame2.setHeight("350px");
        uis.addComponent(frame2);
        
        layout.addComponent(uis);
    }

    // EXAMPLE-REF: advanced.cdi.producers com.vaadin.book.examples.advanced.cdi.CDIProductionUI advanced.cdi.producers
    void producers(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("CDI Producer UI");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/mycdiuis/cdiproducers"));
        frame.setWidth("500px");
        frame.setHeight("150px");

        layout.addComponent(frame);
    }
}
