package com.vaadin.book.examples.lib;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;


public class BrknExample extends BookExample {
    private static final long serialVersionUID = -7260105851251039038L;

    public BrknExample(String exampleId, String shortName, Class<?> exclass) {
        super(exampleId, shortName, exclass);
    }

    @Override
    public String getDescription() {
        return "<h1>Broken Example</h1>";
    }
    
    @Override
    public Component invokeExample() {
        return new Label("SORRY - This example is broken at the moment and will be fixed later");
    }
}
