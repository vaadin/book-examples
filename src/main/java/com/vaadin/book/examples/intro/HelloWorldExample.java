package com.vaadin.book.examples.intro;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

public class HelloWorldExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public static final String helloworldDescription =
            "<h1>Hello World</h1>" +
            "<p>You make a basic Vaadin application by extending the <b>UI</b> component and implementing the init() method.</p>" +
            "<ul>" +
            "  <li>The caption of the root component is usually shown in title bar of the browser window or as the caption of the tab.</li>" +
            "  <li>The user interface is built by adding components to the root component.</li>" +
            "</ul>";
    
    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        Embedded embedded = new Embedded();
        embedded.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/helloworld?restartApplication"));
        embedded.setType(Embedded.TYPE_BROWSER);
        embedded.setWidth("200px");
        embedded.setHeight("100px");
        layout.addComponent(embedded);

        setCompositionRoot(layout);
    }
}
