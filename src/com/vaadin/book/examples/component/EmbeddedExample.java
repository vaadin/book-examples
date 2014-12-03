package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Flash;
import com.vaadin.ui.VerticalLayout;

public class EmbeddedExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3644786684841597587L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("embedded".equals(context))
            embedded(layout);
        else if ("svg".equals(context))
            svg(layout);
        else if ("pdf".equals(context))
            pdf(layout);
        else if ("flash".equals(context))
            flash(layout);
        else if ("browserframe".equals(context))
            browserframe(layout);
        setCompositionRoot(layout);
    }
    
    public void flash(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.flash
        Flash flash = new Flash(null,
            new ThemeResource("img/vaadin_spin.swf"));
        layout.addComponent(flash);
        // END-EXAMPLE: component.embedded.flash
    }

    public void browserframe(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.browserframe
        BrowserFrame browser = new BrowserFrame("Browser",
            new ExternalResource("http://demo.vaadin.com/sampler/"));
        browser.setWidth("600px");
        browser.setHeight("400px");
        layout.addComponent(browser);
        // END-EXAMPLE: component.embedded.browserframe
    }

    public void embedded(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.embedded
        // A resource reference to some object
        Resource res = new ThemeResource("img/vaadin_spin.swf");
        
        // Display the object
        Embedded object = new Embedded("My Flash", res);
        layout.addComponent(object);
        // END-EXAMPLE: component.embedded.embedded
    }

    public void svg(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.svg
        // A resource reference to some object
        Resource res = new ThemeResource("img/reindeer.svg");
        
        // Display the object
        Embedded object = new Embedded("My SVG", res);
        object.setMimeType("image/svg+xml"); // Unnecessary
        layout.addComponent(object);
        // END-EXAMPLE: component.embedded.svg
    }

    public void pdf(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.pdf
        // A resource reference to some object
        Resource res = new ThemeResource("pdfexample.pdf");
        
        // Display the object
        Embedded object = new Embedded("My PDF (requires browser plugin)", res);
        object.setType(Embedded.TYPE_OBJECT);
        object.setMimeType("application/pdf");
        object.setWidth("400px");
        object.setHeight("300px");
        layout.addComponent(object);
        // END-EXAMPLE: component.embedded.pdf
    }
}
