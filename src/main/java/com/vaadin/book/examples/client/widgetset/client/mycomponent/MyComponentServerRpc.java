package com.vaadin.book.examples.client.widgetset.client.mycomponent;

import com.vaadin.shared.communication.ServerRpc;

public interface MyComponentServerRpc extends ServerRpc {

	// TODO example API
    public void clicked(String buttonName);

}
