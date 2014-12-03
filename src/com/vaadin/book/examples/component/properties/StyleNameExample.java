package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class StyleNameExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        if ("add".equals(context))
            addStyleName_Example();
        else if ("set".equals(context))
            setStyleName_Example();
        else if ("set-changing".equals(context))
            setStyleName_changing();
    }
    
    void addStyleName_Example() {
        HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: component.features.stylename.add
        Label label = new Label("This text has style");
        label.addStyleName("mystyle");
        // END-EXAMPLE: component.features.stylename.add

        layout.addComponent(label);
        setCompositionRoot(layout);
    }

    void setStyleName_Example() {
        HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: component.features.stylename.set
        Label label = new Label("This text has a lot of style");
        label.setStyleName("myonestyle myotherstyle");
        // END-EXAMPLE: component.features.stylename.set

        layout.addComponent(label);
        setCompositionRoot(layout);
    }

    void setStyleName_changing() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.features.stylename.set-changing
        final Label label = new Label("Changing Style");
        label.setStyleName("myonestyle");
        
        final CheckBox change = new CheckBox("Use the Other Style");
        change.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -398772337582957L;

            public void valueChange(ValueChangeEvent event) {
                if ((Boolean) event.getProperty().getValue())
                    label.setStyleName("myotherstyle");
                else
                    label.setStyleName("myonestyle");
                
                Notification.show("Has Style: " +
                                  label.getStyleName());
            }
        });
        change.setImmediate(true);
        // END-EXAMPLE: component.features.stylename.set-changing

        layout.addComponent(label);
        layout.addComponent(change);
        layout.setSpacing(true);
        setCompositionRoot(layout);
    }
}
