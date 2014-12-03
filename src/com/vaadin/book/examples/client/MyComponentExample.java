package com.vaadin.book.examples.client;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MyComponentExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("resource".equals(context))
            resource(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public final static String basicDescription =
            "<h1>Custom Widget</h1>" +
            "<p></p>";
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: gwt.eclipse.basic
        final MyComponent mycomponent = new MyComponent();
        layout.addComponent(mycomponent);
        // END-EXAMPLE: gwt.eclipse.basic
    }

    void resource(VerticalLayout layout) {
        // BEGIN-EXAMPLE: gwt.integration.resource
        final MyComponent mycomponent = new MyComponent();
        mycomponent.setMyIcon(new ThemeResource("img/reindeer-128px.png"));
        layout.addComponent(mycomponent);
        // END-EXAMPLE: gwt.integration.resource
    }
}
