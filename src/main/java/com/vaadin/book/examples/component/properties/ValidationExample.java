package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.book.examples.lib.Description;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ValidationExample extends CustomComponent implements AnyBookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void basic(final VerticalLayout layout) {
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
    
    public void explicit(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.explicit
        // A field with automatic validation disabled
        TextField field = new TextField("Name");
        field.setValidationVisible(false);
        field.setNullRepresentation("");
        field.setNullSettingAllowed(true);
        layout.addComponent(field);
        
        // Define validation
        field.addValidator(new StringLengthValidator(
            "The name must be 6-10 letters (was {0})",
            6, 10, true));
        
        // Run validation
        layout.addComponent(new Button("Validate", click -> { // Java 8
            if (! field.isValid())
                layout.addComponent(new Label("It's invalid"));
            
            try {
                field.validate();
            } catch (InvalidValueException e) {
                Notification.show(e.getMessage());
            }
        }));
        // END-EXAMPLE: component.field.validation.explicit
    }

    public void automatic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.automatic
        // Have a string property with invalid initial value
        // TODO This doesn't work currently
        ObjectProperty<String> property =
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
        field.addValueChangeListener(click -> { // Java 8
            field.setValidationVisible(true);
            if (field.getValue() == null)
                layout.addComponent(new Label("Null value"));
            else
                layout.addComponent(new Label("Value edited, now: " +
                                              field.getValue()));
        });
        layout.addComponent(field);

        // Runs validation implicitly
        layout.addComponent(new Button("Validate"));
        // END-EXAMPLE: component.field.validation.automatic
    }

    public void stringlengthvalidator(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.stringlengthvalidator
        FormLayout form = new FormLayout();
        
        TextField field1 = new TextField("6-10 letters, null allowed, 'null' is null");
        field1.setBuffered(true);
        field1.addValidator(new StringLengthValidator(
            "The name must be 6-10 letters (input: {0})",
            6, 10, true));
        field1.setNullRepresentation("null"); // Default but set explicitly
        field1.setNullSettingAllowed(true);
        field1.setValidationVisible(true);
        field1.setValue(null);
        form.addComponent(field1);
        
        TextField field2 = new TextField("6-10 letters, null allowed, empty is null");
        field2.setBuffered(true);
        field2.addValidator(new StringLengthValidator(
            "The name must be 6-10 letters (input: {0})",
            6, 10, true));
        field2.setNullRepresentation("");
        field2.setNullSettingAllowed(true);
        field2.setValidationVisible(true);
        field2.setValue(null);
        form.addComponent(field2);

        TextField field3 = new TextField("6-10 letters, null not allowed, 'null' is null");
        field3.setBuffered(true);
        field3.addValidator(new StringLengthValidator(
            "The name must be 6-10 letters (input: {0})",
            6, 10, true));
        field3.setNullRepresentation("null"); // Default but set explicitly
        field3.setNullSettingAllowed(true);
        field3.setValidationVisible(true);
        field3.setValue(null);
        form.addComponent(field3);
        
        TextField field4 = new TextField("6-10 letters, null not allowed, empty is null");
        field4.setBuffered(true);
        field4.addValidator(new StringLengthValidator(
            "The name must be 6-10 letters (input: {0})",
            6, 10, true));
        field4.setNullRepresentation("");
        field4.setNullSettingAllowed(true);
        field4.setValidationVisible(true);
        field4.setValue(null);
        form.addComponent(field4);
        
        layout.addComponent(form);

        // Runs validation implicitly
        layout.addComponent(new Button("Validate", click -> {// Java 8
           String valid = "";
           TextField[] fields = new TextField[]{field1,field2,field3,field4};
           for (int i=0; i<4; i++)
               valid += "Field " + (i+1) + " value '" + fields[i].getValue() + "' is " +
                        (fields[i].isValid()? "valid":"invalid") + ", ";
           layout.addComponent(new Label(valid));
        }));
        // END-EXAMPLE: component.field.validation.stringlengthvalidator
    }
    
    public void integer(final VerticalLayout layout) {
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
    
    @Description(title="Custom Field Validator", value=
        "<p>You can make custom field validators by implementing the <b>Validator</b> interface.</p>" +
        "<p>Notice that validators are not run if the field value is empty. This causes the " +
        "NullValidator to not work when the null representation is set to \"\".</p>")
    public void customvalidator(final VerticalLayout layout) {
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
