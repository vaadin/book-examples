package com.vaadin.book.examples.client.clientside.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.client.ui.VButton;

public class MyEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        // Use the custom widget
        final MyWidget mywidget = new MyWidget();
        RootPanel.get().add(mywidget);

        // Add a Vaadin button
        VButton button = new VButton();
        button.setText("Click me!");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mywidget.setText("Clicked!");
            }
        });
        
        RootPanel.get().add(button);
    }
}
