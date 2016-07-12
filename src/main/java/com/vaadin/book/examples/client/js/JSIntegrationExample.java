package com.vaadin.book.examples.client.js;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class JSIntegrationExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public final static String basicDescription =
            "<h1>Integrating a JavaScript Component</h1>" +
            "<p></p>";

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: gwt.javascript.basic
        final MyComponent mycomponent = new MyComponent();
        mycomponent.setValue("Server-side value");
        mycomponent.onChange(newValue ->
                Notification.show("New value: " + newValue));
        // END-EXAMPLE: gwt.javascript.basic
        // EXAMPLE-REF: gwt.javascript.basic com.vaadin.book.examples.client.js.MyComponent gwt.javascript.basic 
        // EXAMPLE-REF: gwt.javascript.basic com.vaadin.book.examples.client.js.MyComponentState gwt.javascript.basic 
        // EXAMPLE-FILE: gwt.javascript.basic /com/vaadin/book/examples/client/js/mylibrary.js 
        // EXAMPLE-FILE: gwt.javascript.basic /com/vaadin/book/examples/client/js/mycomponent-connector.js 
        layout.addComponent(mycomponent);
    }
}
