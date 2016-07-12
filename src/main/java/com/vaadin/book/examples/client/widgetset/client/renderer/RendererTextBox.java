package com.vaadin.book.examples.client.widgetset.client.renderer;

import com.google.gwt.user.client.ui.TextBox;

class RendererTextBox extends TextBox {
    private String rowKey;
    private String columnKey;
    
    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }
    public String getRowKey() {
        return rowKey;
    }
    
    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
    public String getColumnKey() {
        return columnKey;
    }
}