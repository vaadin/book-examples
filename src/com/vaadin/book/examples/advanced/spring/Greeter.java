package com.vaadin.book.examples.advanced.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * A Vaadin component that can be injected in a view.
 * 
 * @author magi
 */
@ViewScope
public class Greeter extends CustomComponent {
    private static final long serialVersionUID = -7461902820768542976L;

    @Autowired
    User user;

    public Greeter() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Geeting..."));
        setCompositionRoot(layout);
    }
}
