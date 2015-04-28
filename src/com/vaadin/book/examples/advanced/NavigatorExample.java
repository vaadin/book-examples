package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class NavigatorExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 9754344337L;

    // EXAMPLE-REF: advanced.navigator.basic com.vaadin.book.examples.advanced.NavigatorUI advanced.navigator.basic
    public void basic (VerticalLayout layout) {
        Button button = new Button("Click to Open");
        layout.addComponent(button);
        
        BrowserWindowOpener opener = new BrowserWindowOpener(
            VaadinServlet.getCurrent()
            .getServletContext().getContextPath() +
            "/navigator");
        opener.setFeatures("width=640,height=480,resizable");
        opener.extend(button);
    }

    // EXAMPLE-REF: advanced.navigator.customviewprovider com.vaadin.book.examples.advanced.CustomViewProviderUI advanced.navigator.customviewprovider
    public void customviewprovider (VerticalLayout layout) {
        Button button = new Button("Click to Open");
        layout.addComponent(button);
        
        BrowserWindowOpener opener = new BrowserWindowOpener(
            VaadinServlet.getCurrent()
            .getServletContext().getContextPath() +
            "/customviewprovider");
        opener.setFeatures("width=640,height=480,resizable");
        opener.extend(button);
    }
}
