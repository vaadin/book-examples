package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class EmbeddingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9754344337L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("div".equals(context))
            div(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void div (VerticalLayout layout) {
        // EXAMPLE-APPFILE: advanced.embedding.div embedding-in-div.html
        // BEGIN-EXAMPLE: advanced.embedding.div
        // A button to open the printer-friendly page.
        Button open = new Button("Open a Static HTML Page");
        BrowserWindowOpener opener = new BrowserWindowOpener(
            "/book-examples-vaadin7/embedding-in-div.html");
        opener.setFeatures("height=300,width=400,resizable");
        opener.extend(open);
        // END-EXAMPLE: advanced.embedding.div
        
        setCompositionRoot(open);
    }
}
