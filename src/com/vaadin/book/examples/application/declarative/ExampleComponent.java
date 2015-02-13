package com.vaadin.book.examples.application.declarative;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ExampleComponent extends CustomComponent {
    public ExampleComponent() {
        setCompositionRoot(new Label("I am an example."));
    }
}
