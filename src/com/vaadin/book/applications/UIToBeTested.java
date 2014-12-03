package com.vaadin.book.applications;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: testbench.application
@Theme("valo")
public class UIToBeTested extends UI {
    private static final long serialVersionUID = 511085335415683713L;

    @Override
    protected void init(VaadinRequest request) {
        setId("myui");
        
        final VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setId("myui.content");
        setContent(content);
        
        // Create a button
        Button button = new Button("Push Me!");
        
        // Optional: give the button a unique ID
        button.setId("myui.content.pushmebutton");
        
        // Set the tooltip
        button.setDescription("This is a tip");

        // Do something when the button is clicked
        button.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -8358743723903182533L;

            @Override
            public void buttonClick(ClickEvent event) {
                // This label will not have a set ID
                content.addComponent(new Label("Thanks!"));
            }
        });
        content.addComponent(button);
    }
}
// END-EXAMPLE: testbench.application
