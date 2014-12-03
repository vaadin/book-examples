package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ValidationExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("explicit".equals(context))
            explicit(layout);
        else if ("customvalidator".equals(context))
            customvalidator(layout);
        else if ("automatic".equals(context))
            automatic(layout);
        else if ("integer".equals(context))
            integer(layout);
        else
            layout.addComponent(new Label("Invalid context " + context));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
	}
	
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.basic
        TextField field = new TextField("Name");
        field.setBuffered(true);
        field.addValidator(new StringLengthValidator(
            "The name must be 1-10 letters (input: {0})",
            1, 10, true));
        layout.addComponent(field);

        // Runs validation implicitly
        layout.addComponent(new Button("Validate"));
        // END-EXAMPLE: component.field.validation.basic
    }
    
    void explicit(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.explicit
        // A field with automatic validation disabled
        final TextField field = new TextField("Name");
        field.setValidationVisible(false);
        layout.addComponent(field);
        
        // Define validation
        field.addValidator(new StringLengthValidator(
            "The name must be 1-10 letters (was {0})",
            1, 10, true));
        
        // Run validation
        Button validate = new Button("Validate");
        validate.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 7729516791241492195L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    field.validate();
                } catch (InvalidValueException e) {
                    Notification.show(e.getMessage());
                }
            }
        });
        layout.addComponent(validate);
        // END-EXAMPLE: component.field.validation.explicit
    }

    void automatic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.automatic
        // Have a string property with invalid initial value
        // TODO This doesn't work currently
        final ObjectProperty<String> property =
                new ObjectProperty<String>("");
        property.setValue(null);

        // Have a field with a validator that is invalid initially
        final TextField field = new TextField("Name", property);
        field.setBuffered(true);
        field.setNullRepresentation("");
        field.setNullSettingAllowed(true);
        field.addValidator(new NullValidator("May not be null", false));
        
        // Initial value is invalid, so don't validate
        // before it has changed
        field.setValidationVisible(false);
        field.setImmediate(true);
        field.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = -1183074712643340495L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                field.setValidationVisible(true);
                if (field.getValue() == null)
                    layout.addComponent(new Label("Null value"));
                else
                    layout.addComponent(new Label("Value edited, now: " +
                                                  field.getValue()));
            }
        });
        layout.addComponent(field);

        // Runs validation implicitly
        layout.addComponent(new Button("Validate"));
        // END-EXAMPLE: component.field.validation.automatic
    }
    
    void integer(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.integer
        // Have a property of integer type
        ObjectProperty<Integer> property = new ObjectProperty<Integer>(0);
        
        // Bind it to a field
        final TextField field = new TextField("Age", property);
        layout.addComponent(field);
        
        // Define validation
        field.addValidator(new IntegerRangeValidator(
            "The value must be integer between 0-120 (was {0})",
            0, 120));
        
        // Run validation
        Button validate = new Button("Validate", event -> { // Java 8
            try {
                field.validate();
            } catch (InvalidValueException e) {
                Notification.show(e.getMessage());
            }
        });
        layout.addComponent(validate);
        // END-EXAMPLE: component.field.validation.integer
    }
    
    public static final String customvalidatorDescription =
        "<h1>Custom Field Validator</h1>\n" +
        "<p>You can make custom field validators by implementing the <b>Validator</b> interface.</p>" +
        "<p>Notice that validators are not run if the field value is empty. This causes the " +
        "NullValidator to not work when the null representation is set to \"\".</p>";
    
    void customvalidator(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.customvalidator
        class MyValidator implements Validator {
            private static final long serialVersionUID = -8281962473854901819L;

            @Override
            public void validate(Object value)
                    throws InvalidValueException {
                if (!(value instanceof String &&
                        ((String)value).equals("hello")))
                    throw new InvalidValueException("You're impolite");
            }
        }
        
        TextField field = new TextField("Say hello");
        field.addValidator(new MyValidator());
        field.setImmediate(true);
        layout.addComponent(field);

        // Add some built-in validators
        field.addValidator(new StringLengthValidator(
                "Not long enough or null", 3, 100, true));
        field.addValidator(new NullValidator(
                "Must not be null", false));
        // END-EXAMPLE: component.field.validation.customvalidator
    }
}
