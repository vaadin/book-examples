package com.vaadin.book.examples.component;

import java.io.Serializable;
import java.util.Locale;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.book.examples.lib.Description;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TextFieldExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4454143876393393750L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.basic
        // Create a text field
        TextField tf = new TextField("A Field");
        
        // Put some initial content in it
        tf.setValue("Stuff in the field");
        // END-EXAMPLE: component.textfield.basic

        layout.addComponent(tf);
    }

    public void inputhandling(VerticalLayout layout) {
        // Create a text field
        TextField tf = new TextField("A Field");
        
        // Put some initial content in it
        tf.setValue("Stuff in the field");

        // BEGIN-EXAMPLE: component.textfield.inputhandling
        // Handle changes in the value
        tf.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6549081726526133772L;

            public void valueChange(ValueChangeEvent event) {
                // Assuming that the value type is a String
                String value = (String) event.getProperty().getValue();

                // Do something with the value
                Notification.show("Value is: " + value);
            }
        });
        
        // Fire value changes immediately when the field loses focus
        tf.setImmediate(true);
        // END-EXAMPLE: component.textfield.inputhandling

        layout.addComponent(tf);
        
        // Clicking this button causes a request that carries
        // also the value change event.
        Button ok = new Button("Click here to change focus");
        layout.addComponent(ok);
        
        layout.setSpacing(true);
    }

    public void valuetype(VerticalLayout root) {
        // BEGIN-EXAMPLE: component.textfield.valuetype
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);

        // Text field for editing a double value
        final ObjectProperty<Double> value1 =
            new ObjectProperty<Double>(0.0);
        final TextField tf1 = new TextField(value1);
        layout.addComponent(tf1);
        
        layout.addComponent(new Label("+"));
        
        // Another text field for editing a double value
        final ObjectProperty<Double> value2 =
            new ObjectProperty<Double>(0.0);
        final TextField tf2 = new TextField(value2);
        layout.addComponent(tf2);

        layout.addComponent(new Label("="));

        // A result field
        final ObjectProperty<Double> result =
            new ObjectProperty<Double>(0.0, Double.class);
        layout.addComponent(new Label(result));

        // Handle user input
        Property.ValueChangeListener listener =
            new Property.ValueChangeListener() {
                private static final long serialVersionUID = -2545525837681593427L;

                public void valueChange(ValueChangeEvent event) {
                    // Calculate the result from the input values
                    result.setValue(value1.getValue() +
                                    value2.getValue());
                }
        };
        tf1.addValueChangeListener(listener);
        tf1.setImmediate(true);
        tf2.addValueChangeListener(listener);
        tf2.setImmediate(true);
        // END-EXAMPLE: component.textfield.valuetype

        tf1.setWidth("5em");
        tf2.setWidth("5em");
        root.addComponent(layout);
    }

    public void databinding(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.databinding
        // Have an initial data model. As Double is unmodificable and
        // doesn't support assignment from String, the object is
        // reconstructed in the wrapper when the value is changed.
        Double trouble = 42.0;
        
        // Wrap it in a property data source
        final ObjectProperty<Double> property =
            new ObjectProperty<Double>(trouble);
        
        // Create a text field bound to it
        TextField tf = new TextField("The Answer", property);
        tf.setImmediate(true);

        // Show that the value is really written back to the
        // data source when edited by user.
        Label feedback = new Label(property);
        feedback.setCaption("The Value");
        // END-EXAMPLE: component.textfield.databinding

        layout.addComponent(tf);
        layout.addComponent(feedback);
        layout.setSpacing(true);
    }

    // BEGIN-EXAMPLE: component.textfield.beanbinding
    // We has a bean
    public class JellyBean implements Serializable {
        private static final long serialVersionUID = 8687218705447726543L;

        private double sugarContent;
        private int    count;

        // Setter and getter must use an object type
        public Double getSugarContent() {
            return (sugarContent == 0)? null : sugarContent;
        }
        public void setSugarContent(Double sugarContent) {
            this.sugarContent = (sugarContent != null)? sugarContent : 0;
        }
        public Integer getCount() {
            return (count == 0)? null : count;
        }
        public void setCount(Integer count) {
            this.count = (count != null)? count : 0;
        }
    }

    /** Integer converter that accepts empty values. */
    public class LenientIntegerConverter implements Converter<String, Integer> {
        private static final long serialVersionUID = -8487454652016030363L;

        @Override
        public Integer convertToModel(String value,
            Class<? extends Integer> targetType, Locale locale)
                throws ConversionException {
            if (value == null || "".equals(value))
                return null;
            else {
                try {
                    return Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    throw new ConversionException(e);
                }
            }
        }

        @Override
        public String convertToPresentation(Integer value,
            Class<? extends String> targetType, Locale locale)
                throws ConversionException {
            if (value == null)
                return "";

            return ((Integer) value).toString();
        }

        @Override
        public Class<Integer> getModelType() {
            return Integer.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }

    }

    /** Double validator that accepts empty values */
    public class LenientDoubleValidator extends DoubleRangeValidator {
        private static final long serialVersionUID = -3795353195313914432L;

        public LenientDoubleValidator(String message) {
            super(message, null, null);
        }

        @Override
        public boolean isValid(Object value) {
            if ("".equals(value))
                return true;
            else
                return super.isValid(value);
        }
    }

    public void beanbinding(final VerticalLayout layout) {
        // Instantiate and wrap the bean
        final JellyBean bean = new JellyBean();
        BeanItem<JellyBean> item = new BeanItem<JellyBean>(bean);
        
        FormLayout form = new FormLayout();
        layout.addComponent(form);
        
        FieldGroup binder = new FieldGroup();

        // Define a field factory that always returns a TextField,
        // almost like DefaultFieldFactory does, but make some
        // additional settings.
        binder.setFieldFactory(new FieldGroupFieldFactory() {
            private static final long serialVersionUID = 4644195245272334839L;

            @SuppressWarnings("rawtypes")
            @Override
            public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
                // Create a text field
                TextField f = new TextField();
                f.setImmediate(true);
                
                // Modify according to exact type
                if (dataType.equals(Integer.class)) {
                    f.setDescription("Enter an integer value");
                    f.addValidator(new IntegerRangeValidator("Not an integer", null, null));
                    f.setConverter(new LenientIntegerConverter());
                }
                else if (dataType.equals(Double.class)) {
                    f.setDescription("Enter a floating-point value");
                    f.addValidator(new LenientDoubleValidator("Not a double"));
                }
                
                // Be empty at zero value instead of displaying 0.0
                f.setNullRepresentation("");
                
                return fieldType.cast(f);
            }
        });

        binder.setItemDataSource(item);
        form.addComponent(binder.buildAndBind("Sugar Content", "sugarContent"));
        form.addComponent(binder.buildAndBind("Count", "count"));
        
        // Use buffering - input needs to be committed
        binder.setBuffered(true);
        
        layout.addComponent(new Button("OK", new ClickListener() {
            private static final long serialVersionUID = -2559034876908094791L;

            public void buttonClick(ClickEvent event) {
                try {
                    binder.commit();
                } catch (CommitException e) {
                    // Commit failed
                }
                layout.addComponent(new Label("Sugar Content = " + bean.getSugarContent() +
                        ", Count = " + bean.getCount()));
            }
        }));
    }
    // END-EXAMPLE: component.textfield.beanbinding

    public void inputprompt(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.inputprompt
        // Create the text field
        TextField tf = new TextField("A Field");

        // Prompt that is shown when the field is empty
        tf.setInputPrompt("Enter a Seed Value");
        // END-EXAMPLE: component.textfield.inputprompt
        
        layout.addComponent(tf);
    }
    
    public static final String nullvaluerepresentationDescription =
        "<h1>Inputting null values</h1>" +
        "<p>You can use <tt>setNullValueRepresentation()</tt> to set the representation of null in a property value. " +
        "The default representation is \"null\". You typically want to set it to empty string (\"\").</p>" +
        "<p>If you want to let the user to input such null values, you need to use <tt>setNullSettingAllowed(true)</tt>.</p>";
    
    public void nullvaluerepresentation(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.nullvaluerepresentation
        // Have a property with null value
        ObjectProperty<Double> dataModel =
            new ObjectProperty<Double>(new Double(0.0));
        dataModel.setValue(null); // Have to set it null here

        // Create a text field bound to the null data
        TextField tf = new TextField("Field Energy (J)", dataModel);
        tf.setNullRepresentation("-- null-point --");

        // Allow user to input the null value by its representation
        tf.setNullSettingAllowed(true);

        // Feedback to see the value
        TextField value = new TextField(dataModel);
        value.setNullRepresentation("It's null!");
        value.setReadOnly(true);
        value.setCaption("Current Value:");
        // END-EXAMPLE: component.textfield.nullvaluerepresentation

        // tf.setWidth("10em");
        
        Button ok = new Button("Update");

        layout.addComponent(tf);
        layout.addComponent(value);
        layout.addComponent(ok);
        layout.setSpacing(true);
    }
    
    public void maxlength(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.maxlength
        TextField tf = new TextField("Give Me a Number");
        tf.setMaxLength(10);
        // END-EXAMPLE: component.textfield.maxlength
        
        layout.addComponent(tf);
    }

    @Description(title="Fitting the Width of a TextField", value=
        "<p>Setting the max width of a TextField does not set its width, "+
        "and setting the width with em widths is too wide for the number of letters. "+
        "You can use this trick to fit the width almost exactly. "+
        "You need to add some small padding as the TextField borders take some space."+
        "The sizer label can be hidden by setting its height to 0px (here it's not).</p>" +
        "<p><b>This does not work any longer because of changes in Vaadin's layout logic " +
        "that nowadays uses more pure HTML logic. " +
        "More exactly, a component with relative width can no longer shrink smaller " +
        "than its natural default width, which is rather wide for TextField.</p>")
    public void widthfitting(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.widthfitting
        // A vertical layout with undefined width
        VerticalLayout box = new VerticalLayout();
        box.setSizeUndefined();

        // This text field takes all the width given
        // in the layout, which is the same as the
        // width of the label.
        TextField tf = new TextField("Give Date");
        tf.setWidth("100%");
        tf.setMaxLength(10);
        tf.setValue("2011-01-01");
        box.addComponent(tf);

        // The layout shrinks to fit this label
        Label label = new Label("2011-01-01");
        label.addStyleName("monospace");
        label.setSizeUndefined();
        label.setHeight("15px"); // Hide: Could be 0px
        box.addComponent(label);
        // END-EXAMPLE: component.textfield.widthfitting

        layout.addStyleName("textfieldexample");
        layout.addComponent(box);
    }
    
    public static final String selectallDescription =
        "<h1>Selecting All Text in TextField</h1>"+
        "<p>You can use the selectAll() method to select the entire text in a <b>TextField</b>.</p>";

    public void selectall(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.selectall
        final TextField tf = new TextField("Here it is");
        tf.setValue("This is selected");
        tf.selectAll();
        
        Button reselect = new Button("Select Again",
            event -> tf.selectAll()); // Java 8
        // END-EXAMPLE: component.textfield.selectall
        
        layout.addComponent(tf);
        layout.addComponent(reselect);
        layout.setSpacing(true);
    }

    public void cursorposition(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.cursorposition
        final TextField tf = new TextField("Here it is");
        tf.setValue("This is a long value in the textfield");
        tf.setColumns(10);
        
        // Move to 3rd last character
        tf.setCursorPosition(tf.getValue().length() - 3);
        tf.focus();

        // Move to end when requested
        Button atEnd = new Button("Move to End", event -> { 
            tf.setCursorPosition(tf.getValue().length());
            tf.focus();
        }); // Java 8
        
        // Insert stuff at current position
        Button insert = new Button("Insert", event -> {
            String toBeInserted = "<inserted>";
            int pos = tf.getCursorPosition();
            int len = tf.getValue().length();
            tf.setValue(
                tf.getValue().substring(0, pos) +
                toBeInserted +
                tf.getValue().substring(pos, len));
            tf.setCursorPosition(pos + toBeInserted.length());
            tf.focus();
        }); // Java 8
        // END-EXAMPLE: component.textfield.cursorposition
        
        layout.addComponent(tf);
        layout.addComponent(atEnd);
        layout.addComponent(insert);
        layout.setSpacing(true);
    }

    public void css(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.css
        TextField tf = new TextField("Fence around a text field");
        tf.addStyleName("dashing");
        // END-EXAMPLE: component.textfield.css
        
        layout.addComponent(tf);
    }    

    public void styles(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.styles
        TextField tf_inlineIcon = new TextField("Inline Icon");
        tf_inlineIcon.setIcon(FontAwesome.USER);
        tf_inlineIcon.setInputPrompt("Select a value");
        tf_inlineIcon.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        layout.addComponent(tf_inlineIcon);
        // END-EXAMPLE: component.textfield.styles
    }
}
