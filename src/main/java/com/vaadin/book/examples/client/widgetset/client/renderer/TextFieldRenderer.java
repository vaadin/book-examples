package com.vaadin.book.examples.client.widgetset.client.renderer;

import com.vaadin.client.renderers.WidgetRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;

public class TextFieldRenderer extends WidgetRenderer<String, RendererTextBox> {

    @Override
    public RendererTextBox createWidget() {
        RendererTextBox box = new RendererTextBox();
        return box;
    }

    @Override
    public void render(RendererCellReference cell, String data,
                       RendererTextBox widget) {
        widget.setText(data);
        // widget.setRowKey(cell.getRowIndex());
    }
}
