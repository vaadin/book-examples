package com.vaadin.book.examples.client.widgetset.client.renderer;

import com.vaadin.shared.communication.ServerRpc;

public interface CellValueChangeRpc extends ServerRpc {
    public void changed(String newValue, String rowKey, String columnKey);
}
