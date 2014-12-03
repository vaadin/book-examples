package com.vaadin.book.examples.client.widgetset.client.mycomponent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.book.examples.client.MyComponent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

@Connect(MyComponent.class)
public class MyComponentConnector extends AbstractComponentConnector {
    private static final long serialVersionUID = 5953609150057019293L;
    
	
    public MyComponentConnector() {    
        registerRpc(MyComponentClientRpc.class, new MyComponentClientRpc() {
            private static final long serialVersionUID = -1056192951789062628L;

            public void alert(String message) {
            	// TODO Do something useful
                Window.alert(message);
            }
        });

		// TODO ServerRpc usage example, do something useful instead
        getWidget().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
                        .buildMouseEventDetails(event.getNativeEvent(),
                                getWidget().getElement());
                MyComponentServerRpc rpc =
                        getRpcProxy(MyComponentServerRpc.class);
                rpc.clicked(mouseDetails.getButtonName());
            }
        });

    }

    @Override
    protected Widget createWidget() {
        return GWT.create(MyComponentWidget.class);
    }

    @Override
    public MyComponentWidget getWidget() {
        return (MyComponentWidget) super.getWidget();
    }

    @Override
    public MyComponentState getState() {
        return (MyComponentState) super.getState();
    }
    
    @OnStateChange("text")
    void updateText() {
        getWidget().setText(getState().text);
    }    

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
		
		// TODO do something useful
        final String text = getState().text;
        getWidget().setText(text);

        // Set a resource
        getWidget().setMyIcon(getResourceUrl("myIcon"));
    }
}

