package com.vaadin.book.examples.component;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class NativeSelectExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.nativeselect.basic
        // Create the selection component with some items
        NativeSelect select = new NativeSelect("Native Selection");
        select.addItems("Mercury", "Venus", "Earth", "Mars",
                        "Jupiter", "Saturn", "Neptune", "Uranus");
        select.setNullSelectionAllowed(false);

        select.addValueChangeListener( event -> // Java 8
            Notification.show((String) select.getValue()));
        select.setImmediate(true);
        // END-EXAMPLE: component.select.nativeselect.basic

        layout.setHeight("200px");
        layout.addComponent(select);
    }

    Container createSelectData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("caption", String.class, null);
        return container;
    }
}
