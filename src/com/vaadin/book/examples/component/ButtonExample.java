package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ButtonExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("link".equals(context))
            link(layout);
        else if ("small".equals(context))
            small(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.basic
        // BOOK: components.button
        Button button = new Button("Do not press this button");

        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059414138237L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Do not press this button again");
            }
        });
        
        layout.addComponent(button);
        // END-EXAMPLE: component.button.basic
    }

    void link(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.link
        // Create a button
        Button button = new Button("Click Me!");
        
        // Make it look like a hyperlink
        button.addStyleName(Reindeer.BUTTON_LINK);
        
        // Handle clicks
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059414138237L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Thank You!");
            }
        });
        layout.addComponent(button);
        // END-EXAMPLE: component.button.link
    }

    void small(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.small
        Button button = new Button("Click Me!");
        button.addStyleName(Reindeer.BUTTON_SMALL);
        layout.addComponent(button);
        // END-EXAMPLE: component.button.small
    }

}
