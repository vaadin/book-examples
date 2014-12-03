package com.vaadin.book.examples.advanced.cdi;

import javax.inject.Inject;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * A Vaadin component that can be injected in a view.
 * 
 * @author magi
 */
@ViewScoped
public class Greeter extends CustomComponent {
    private static final long serialVersionUID = -7461902820768542976L;

    @Inject
    User user;

    public Greeter() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Geeting..."));
        setCompositionRoot(layout);
    }
}
