package com.vaadin.book.examples.gettingstarted;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class NewProjectExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("newproject".equals(context))
            newproject(layout);
        else if ("scala".equals(context))
            scalaui(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public static final String helloworldDescription =
            "<h1>Hello World</h1>" +
            "";
    
    // EXAMPLE-REF: gettingstarted.firstproject.newproject com.vaadin.book.examples.gettingstarted.MyprojectUI gettingstarted.firstproject.newproject
    void newproject(Layout layout) {
        BrowserFrame embedded = new BrowserFrame();
        embedded.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/newproject?restartApplication"));
        embedded.setWidth("400px");
        embedded.setHeight("200px");
        layout.addComponent(embedded);
    }

    // EXAMPLE-REF: gettingstarted.scala com.vaadin.book.examples.gettingstarted.MyScalaUI gettingstarted.scala
    void scalaui(Layout layout) {
        BrowserFrame embedded = new BrowserFrame();
        embedded.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/scalaproject?restartApplication"));
        embedded.setWidth("400px");
        embedded.setHeight("200px");
        layout.addComponent(embedded);
    }
}
