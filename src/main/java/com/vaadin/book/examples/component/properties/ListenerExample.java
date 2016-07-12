package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ListenerExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -23094523947238974L;

	public void init (String context) {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.features.listener
        class Listening extends CustomComponent implements Listener {
            private static final long serialVersionUID = 134234234234234L;
            
            // Stored for determining the source of an event
            Button    ok;
            Label     status; // For displaying info about the event
            
            public Listening() {
                VerticalLayout layout = new VerticalLayout();
            
                // Some miscellaneous component
                TextField name = new TextField("Say it all here");
                name.addListener(this);
                name.setImmediate(true);
                layout.addComponent(name);
                
                // Handle button clicks as generic events instead
                // of Button.ClickEvent events
                ok = new Button("OK");
                ok.addListener(this);
                layout.addComponent(ok);

                // For displaying information about an event
                status = new Label("");
                layout.addComponent(status);
                
                setCompositionRoot(layout);
            }

            public void componentEvent(Event event) {
                // Act according to the source of the event
                if (event.getSource() == ok &&
                    event.getClass() == Button.ClickEvent.class)
                    Notification.show("Click!");

                // Display source component and event class names
                status.setValue("Event from " +
                                event.getSource().getClass().getName() +
                                ": " + event.getClass().getName());
            }
        }
        
        // Use the component
        Listening listening = new Listening();
        layout.addComponent(listening);
        // END-EXAMPLE: component.features.listener

        setCompositionRoot(layout);
	}
}
