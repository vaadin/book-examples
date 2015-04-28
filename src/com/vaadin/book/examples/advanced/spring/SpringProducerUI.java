package com.vaadin.book.examples.advanced.spring;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.producers
@SpringUI(path = "springproducers")
@Theme("valo")
public class SpringProducerUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;
    
    @Autowired
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
