package com.vaadin.book.examples.datamodel;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class ContainerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.items.basic
        // TODO
        // IndexedContainer container = new IndexedContainer();
        // END-EXAMPLE: datamodel.items.basic

        // layout.addComponent(form);
    }
}
