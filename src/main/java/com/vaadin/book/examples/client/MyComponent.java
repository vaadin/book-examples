package com.vaadin.book.examples.client;

import com.vaadin.book.examples.client.widgetset.client.mycomponent.MyComponentClientRpc;
import com.vaadin.book.examples.client.widgetset.client.mycomponent.MyComponentServerRpc;
import com.vaadin.book.examples.client.widgetset.client.mycomponent.MyComponentState;
import com.vaadin.server.Resource;

public class MyComponent extends com.vaadin.ui.AbstractComponent {
    private static final long serialVersionUID = 7486429214661043969L;

    private MyComponentServerRpc rpc = new MyComponentServerRpc() {
        private static final long serialVersionUID = -3384499731721458101L;

        private int clickCount = 0;

        @Override
        public void clicked(String buttonName) {
            // Nag every 5:th click using RPC
            if (++clickCount % 5 == 0) {
                getRpcProxy(MyComponentClientRpc.class).alert(
                        "Ok, that's enough!");
            }

            // Update shared state
            getState().text = "You have clicked " + clickCount +
              " times, now " + buttonName;
        }
    };  

    public MyComponent() {
        getState().text = "This is MyComponent";
        registerRpc(rpc);
    }

    @Override
    public MyComponentState getState() {
        return (MyComponentState) super.getState();
    }

    public void setMyIcon(Resource myIcon) {
        setResource("myIcon", myIcon);
    }
    
    public Resource getMyIcon() {
        return getResource("myIcon");
    }
}
