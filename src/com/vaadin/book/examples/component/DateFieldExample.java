package com.vaadin.book.examples.component;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

public class DateFieldExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6575751250735498511L;

    VerticalLayout layout = new VerticalLayout();
    public void init (String context) {

        if ("basic".equals(context))
            basic(layout);
        else if ("popup-basic".equals(context))
            popup_basic(layout);
        else if ("formatting".equals(context))
            formatting(layout);
        else if ("customerror".equals(context))
            customerror(layout);
        else if ("parsing".equals(context))
            parsing(layout);
        else if ("validation".equals(context))
            validation(layout);
        else if ("inputprompt".equals(context))
            inputprompt(layout);
        else if ("inlinedatefield".equals(context))
            inlinedatefield(layout);
        else if ("resolution-day".equals(context))
            resolution(layout);
        else if ("weeknumbers".equals(context))
            weeknumbers(layout);
        else
            layout.addComponent(new Label("Invalid context "+context));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.basic
        // Create a DateField with the default style
        DateField date = new DateField();
            
        // Set the date and time to present
        date.setValue(new Date());
        // END-EXAMPLE: component.datefield.basic
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));
    }
    
    public static String popup_basicDescription =
        "<h1>PopupDateField</h1>";
    
    void popup_basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.popupdatefield.popup-basic
        /* Create a DateField with the default style. */
        PopupDateField date = new PopupDateField();
            
        /* Set the date and time to present. */
        date.setValue(new Date());
        
        date.setResolution(Resolution.DAY);
        // END-EXAMPLE: component.datefield.popupdatefield.popup-basic
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));
    }

    public static String formattingDescription =
        "<h1>Formatting Date and Time in DateField</h1>";
    
    void formatting(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.popupdatefield.formatting
        // Create a date field
        PopupDateField date = new PopupDateField("My Date");
        date.addStyleName("mydate");

        // Set the date and time to current
        date.setValue(new Date());
        
        // Display only year, month, and day in ISO format
        date.setDateFormat("yyyy-MM-dd");
        // END-EXAMPLE: component.datefield.popupdatefield.formatting
        
        // Don't be too tight about the format of user-input values
        date.setLenient(true);
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 200px;'/>", ContentMode.HTML));
        layout.addComponent(new Button("You can click here to validate the date"));
    }

    public static String customerrorDescription =
        "<h1>Custom Error for Date and Time Input</h1>"+
        "<p>Try inputting something invalid here, like \"<tt>xxxx</tt>\" and \"<tt>aa/bb/cc</tt>\"</p>";
    
    void customerror(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.popupdatefield.customerror
        // Create a date field with a custom error message for invalid format
        PopupDateField date = new PopupDateField("My Date") {
            private static final long serialVersionUID = 4001006351711723688L;

            @Override
            protected Date handleUnparsableDateString(String dateString)
                throws ConversionException {
                // Have a notification for the error
                Notification.show(
                        "Your date needs two slashes",
                        Notification.Type.WARNING_MESSAGE);
                
                // A failure must always also throw an exception
                throw new ConversionException("Bad date");
            }
        };
        date.setImmediate(true);
        
        // Display only year, month, and day in slash-delimited format
        date.setDateFormat("yyyy/MM/dd");

        // Don't be too tight about the validity of dates
        // on the client-side
        date.setLenient(true);
        // END-EXAMPLE: component.datefield.popupdatefield.customerror
        
        date.setInputPrompt("Scribble something here");
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));
    }
    
    public static String parsingDescription =
        "<h1>Custom Parsing for Parsing Date and Time</h1>"+
        "<p>Try inputting something invalid here, like \"<tt>xxxx</tt>\" and \"<tt>aa/bb/cc</tt>\"</p>";
    
    void parsing(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.popupdatefield.parsing
        // Create a date field with a custom parsing and a
        // custom error message for invalid format
        final PopupDateField date = new PopupDateField("My Date") {
            private static final long serialVersionUID = 4001006351711723688L;
            
            @Override
            protected Date handleUnparsableDateString(String dateString)
            throws ConversionException {
                // Try custom parsing
                String fields[] = dateString.split("/");
                if (fields.length >= 3) {
                    try {
                        int year  = Integer.parseInt(fields[0]);
                        int month = Integer.parseInt(fields[1])-1;
                        int day   = Integer.parseInt(fields[2]);
                        GregorianCalendar c =
                            new GregorianCalendar(year, month, day);
                        return c.getTime();
                    } catch (NumberFormatException e) {
                        // Bad field value 
                        throw new ConversionException("Not a number");
                    }
                }
                
                // Really bad date
                throw new ConversionException("Your date needs two slashes");
            }
        };
        
        // Display only year, month, and day in slash-delimited format
        date.setDateFormat("yyyy/MM/dd");
        
        // Don't be too tight about the user-input
        date.setLenient(true);

        // Initial value
        ObjectProperty<Date> property = new ObjectProperty<Date>(new Date());
        date.setPropertyDataSource(property);
        // END-EXAMPLE: component.datefield.popupdatefield.parsing
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));

        // Show the actual date property value
        Label dateValue = new Label(property);
        dateValue.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6561440698247909665L;

            public void valueChange(ValueChangeEvent event) {
                layout.addComponent(new Label("Buffered value changed"));
            }
        });
        layout.addComponent(dateValue);

        // Enable buffering
        date.setBuffered(false);
        date.setInvalidCommitted(false);

        Button validate = new Button("You can click here to validate the date");
        validate.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -5365344852447941330L;

            public void buttonClick(ClickEvent event) {
                date.commit();
            }
        });
        layout.addComponent(validate);
    }

    void validation(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.popupdatefield.validation
        // KB: 187 How do I set scroll position of Table? 
        // Have a date field that only accepts dates in
        // the future.
        final PopupDateField df = new PopupDateField();
        df.addValidator(new Validator() {
            private static final long serialVersionUID = 1811037448717711897L;

            @Override
            public void validate(Object value) throws InvalidValueException {
                Date dateValue = (Date) value;
                Date now = new Date();
                
                if (dateValue.before(now))
                    throw new InvalidValueException("Before now");
            }
        });
        layout.addComponent(df);
        
        // Do not validate immediately
        df.setBuffered(true);
        
        Button validate = new Button("Validate");
        validate.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1615654957316768139L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    df.validate();
                    layout.addComponent(new Label("Date is OK"));
                } catch (InvalidValueException e) {
                    layout.addComponent(new Label("Failed because: " + e.getMessage()));
                }
            }
        });
        layout.addComponent(validate);
        // END-EXAMPLE: component.datefield.popupdatefield.validation
    }
    
    public static String inputpromptDescription =
        "<h1>Input Prompt in PopupDateField</h1>";
    
    void inputprompt(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.popupdatefield.inputprompt
        PopupDateField date = new PopupDateField();

        // Set the prompt
        date.setInputPrompt("Select a date");
        
        // The date field doesn't automatically scale to accommodate
        // the prompt, so set it explicitly
        date.setWidth("10em");
        // END-EXAMPLE: component.datefield.popupdatefield.inputprompt
        
        // Use day resolution
        date.setResolution(Resolution.DAY);
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));
    }

    public static String inlinedatefieldDescription =
        "<h1>InlineDateField</h1>";
    
    void inlinedatefield(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.inlinedatefield
        // Create a DateField with the default style
        InlineDateField date = new InlineDateField();
            
        // Set the date and time to present
        date.setValue(new java.util.Date());
        // END-EXAMPLE: component.datefield.inlinedatefield
        
        // Always use English locale in the example
        date.setLocale(new Locale("en", "US"));

        layout.addComponent(date);
        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));
    }
    
    void resolution(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.resolution
        PopupDateField df = new PopupDateField("Select Date");
        df.setResolution(Resolution.SECOND);
        // END-EXAMPLE: component.datefield.resolution
        
        Label feedback = new Label();
        layout.addComponents(df, feedback);
        
        df.setImmediate(true);
        df.addValueChangeListener(event -> 
            feedback.setValue(event.getProperty().getValue().toString()));

        layout.addComponent(new Label("<div style='height: 250px;'/>", ContentMode.HTML));
    }

    void weeknumbers(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.datefield.weeknumbers
        InlineDateField df = new InlineDateField("Select Date");
        df.setResolution(Resolution.DAY);
        
        // Enable week numbers
        df.setShowISOWeekNumbers(true);
        // END-EXAMPLE: component.datefield.weeknumbers
        layout.addComponent(df);
    }
}
