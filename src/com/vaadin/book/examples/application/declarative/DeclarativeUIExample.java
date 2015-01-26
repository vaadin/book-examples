package com.vaadin.book.examples.application.declarative;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class DeclarativeUIExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -5434372168449769138L;

    public static final String basicDescription =
            "<h1>Basic Use of Declarative UIs</h1>" +
            "<p></p>";
    
    // EXAMPLE-REF: application.declarative.basic com.vaadin.book.applications.declarative.MyBasicDeclarativeUI application.declarative.basic
    public void basic(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/basicdeclarativeui?restartApplication"));
        frame.setWidth("570px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }

    // EXAMPLE-REF: application.declarative.context com.vaadin.book.applications.declarative.MyDeclarativeUI application.declarative.context
    public void context(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/declarativeui?restartApplication"));
        frame.setWidth("570px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }
}
