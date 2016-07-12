package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class GlobalAccessExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("threadlocal".equals(context))
            threadlocal(layout);
        else
            layout.addComponent(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    public final static String threadlocalDescription =
        "<h1>ThreadLocal Pattern</h1>"+
        "<p>ThreadLocal pattern is used in place of a static variable or singleton to access an object globally.</p>";
    
    void threadlocal (VerticalLayout layout) {
        // EXAMPLE-REF: advanced.global.threadlocal com.vaadin.book.examples.advanced.AppData advanced.global.threadlocal
        // EXAMPLE-REF: advanced.global.threadlocal com.vaadin.book.applications.ThreadLocalApplication advanced.global.threadlocal

        // The following shouldn't actually be shown 
        // NOT-SHOWN-BYGIN-EXAMPLE: advanced.global.threadlocal
        Panel panel = new Panel("ThreadLocal Application");
        panel.setSizeUndefined();
        ((VerticalLayout)panel.getContent()).setMargin(false);
        layout.addComponent(panel);

        BrowserFrame browser = new BrowserFrame();
        browser.setSource(new ExternalResource(
            VaadinServlet.getCurrent().getServletContext()
            .getContextPath() +
            "/threadlocal?restartApplication"));
        browser.setWidth("200px");
        browser.setHeight("150px");
        panel.setContent(browser);
        // NOT-SHOWN-YND-EXAMPLE: advanced.global.threadlocal
        
        layout.setSpacing(true);
    }
}
