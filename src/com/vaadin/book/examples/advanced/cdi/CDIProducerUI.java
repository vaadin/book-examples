package com.vaadin.book.examples.advanced.cdi;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.producers
@CDIUI("cdiproducers")
@Theme("valo")
public class CDIProducerUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;
    
    @Inject
    Logger logger;

    @Override
    protected void init(VaadinRequest request) {
        logger.info("Initializing UI");
        // setContent(new MyComponent(TableExample.generateContent()));
    }

    /*
    // Some class without a default constructor
    final class MyComponent extends Table {
        private static final long serialVersionUID = -278058594282382429L;
        
        public MyComponent(Container container) {
            super(null, container);
        }
    }*/
    
    /*
    @Inject
    MyComponent myComponent;
    */
    
    /*
    Container container = TableExample.generateContent();
    
    @Produces
    private MyComponent createMyTable() {
        return new MyComponent(container);
    }*/
}
// END-EXAMPLE: advanced.cdi.producers
