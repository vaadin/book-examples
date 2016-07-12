package com.vaadin.book.examples.component;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BufferingExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 4598073828719119575L;

    public void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.buffering.basic
        // Have a property to edit
        ObjectProperty<String> property = new ObjectProperty<String>(""); 

        // Shows input form and viever
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        
        // Input form
        VerticalLayout form = new VerticalLayout();

        // Editor for the property value
        final TextField editor = new TextField("Editor", property);
        editor.addValidator(new StringLengthValidator(
            "Must be 1-10 long", 1, 10, false));
        editor.setValidationVisible(false); // Don't show initially
        editor.setBuffered(true);
        form.addComponent(editor);
        
        // Button bar
        form.addComponent(new HorizontalLayout(
            new Button("Commit",
                event -> { // Java 8
                    try {
                        editor.setValidationVisible(true);
                        editor.commit();
                    } catch (InvalidValueException e) {
                        Notification.show(e.getMessage());
                    }
                }),

            new Button("Discard",
                event -> editor.discard()))); // Java 8

        hlayout.addComponent(form);

        // Display data source content in a model viewer
        VerticalLayout model = new VerticalLayout();
        Label viewer = new Label(property);
        viewer.setCaption("Data Source Value");
        model.addComponent(viewer);
        hlayout.addComponent(model);
        // END-EXAMPLE: component.field.buffering.basic

        layout.addComponent(hlayout);
    }
}
