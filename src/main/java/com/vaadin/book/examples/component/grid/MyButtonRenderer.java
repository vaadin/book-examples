package com.vaadin.book.examples.component.grid;

import com.vaadin.ui.renderers.ButtonRenderer;

public class MyButtonRenderer extends ButtonRenderer {
    public MyButtonRenderer() {
        super();
    }

    public MyButtonRenderer(RendererClickListener listener) {
        this();
        addClickListener(listener);
    }
}
