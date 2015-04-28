package com.vaadin.book.examples.client.widgetset.client.renderer;

import com.google.gwt.user.client.ui.Button;
import com.vaadin.client.renderers.ButtonRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;

public class MyButtonRenderer extends ButtonRenderer {
    @Override
    public void render(RendererCellReference cell, String text, Button button) {
        boolean enabled = true;
        if (text.startsWith("Disabled:")) {
            text = text.substring("Disabled:".length());
            enabled = false;
        }
        button.setText(text);
        button.setEnabled(enabled);
    }
}
