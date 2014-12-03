package com.vaadin.book.examples.application;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EventListenerExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    // BEGIN-EXAMPLE: application.eventlistener.classlistener
    // Here we have a composite component that handles the
    // events of its sub-components (a single button).
    public class MyComposite extends CustomComponent
           implements Button.ClickListener {
        private static final long serialVersionUID = 23482364238642324L;
        
        Button button; // Defined here for access

        public MyComposite() {
            Layout layout = new HorizontalLayout();
            
            // Just a single component in this composition
            button = new Button("Do not push this");
            button.addClickListener(this);
            layout.addComponent(button);
            
            setCompositionRoot(layout);
        }
        
        // The listener method implementation
        public void buttonClick(ClickEvent event) {
            button.setCaption("Do not push this again");
        }
    }
    // END-EXAMPLE: application.eventlistener.classlistener

    public void classlistener(VerticalLayout layout) {
        layout.addComponent(new MyComposite());
    }
    
    // BEGIN-EXAMPLE: application.eventlistener.differentiation
    // Inner class is OK as long as it is public
    public class TheButtons extends CustomComponent
           implements Button.ClickListener {
        private static final long serialVersionUID = 3160722889741374224L;

        Button onebutton;
        Button toobutton;

        public TheButtons() {
            onebutton = new Button("Button One", this);
            toobutton = new Button("A Button Too", this);

            // Put them in some layout
            Layout root = new HorizontalLayout(); 
            root.addComponent(onebutton);
            root.addComponent(toobutton);
            setCompositionRoot(root);
        }
        
        @Override
        public void buttonClick(ClickEvent event) {
            // Differentiate targets by event source
            if (event.getButton() == onebutton)
                onebutton.setCaption ("Pushed one");
            else if (event.getButton() == toobutton)
                toobutton.setCaption ("Pushed too");
        }
    }
    // END-EXAMPLE: application.eventlistener.differentiation
        
    public void differentiation(VerticalLayout layout) {
        TheButtons buttons = new TheButtons();
        layout.addComponent(buttons);
    }

    public void anonymous(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.eventlistener.anonymous
        // Have a component that fires click events
        final Button button = new Button("Click Me!");
        
        // Handle the events with an anonymous class
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                button.setCaption("You made me click!");
            }
        });
        // END-EXAMPLE: application.eventlistener.anonymous
        layout.addComponent(button);
    }
    
    public void java8(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.eventlistener.java8
        layout.addComponent(new Button("Click Me!",
            event -> event.getButton().setCaption("You made click!")));
        // END-EXAMPLE: application.eventlistener.java8
    }

    // BEGIN-EXAMPLE: application.eventlistener.java8differentiation
    public class Java8Buttons extends CustomComponent {
        private static final long serialVersionUID = 3160722889741374224L;

        public Java8Buttons() {
            setCompositionRoot(new HorizontalLayout( 
                new Button("OK", this::ok),
                new Button("Cancel", this::cancel)));
        }
        
        public void ok(ClickEvent event) {
            event.getButton().setCaption ("OK!");
        }

        public void cancel(ClickEvent event) {
            event.getButton().setCaption ("Not OK!");
        }
    }

    public void java8differentiation(VerticalLayout layout) {
        layout.addComponent(new TheButtons());
    }
    // END-EXAMPLE: application.eventlistener.java8differentiation
    
    public void constructor(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.eventlistener.constructor
        final Button button = new Button("Click It!",
          new Button.ClickListener() {
            private static final long serialVersionUID = -949512667736897163L;
      
            @Override
            public void buttonClick(ClickEvent event) {
                // Can't access the "button" reference
                // inside the constructor
                event.getButton().setCaption("Done!");
            }
          });
        // END-EXAMPLE: application.eventlistener.constructor
        layout.addComponent(button);
    }

    // BEGIN-EXAMPLE: application.eventlistener.another
    public class SomeComposite extends CustomComponent {
     private static final long serialVersionUID = 23482364238642324L;
     
        Button button; // Does not need to be final

        public SomeComposite() {
            button = new Button("Oh just do it",
              new Button.ClickListener() {
                private static final long serialVersionUID = -949512667736897163L;
        
                @Override
                public void buttonClick(ClickEvent event) {
                    button.setCaption("You did it!");
                }
            });
             
            Layout layout = new HorizontalLayout();
            layout.addComponent(button);
            setCompositionRoot(layout);
        }
    }
    // END-EXAMPLE: application.eventlistener.another
 
    public void another(VerticalLayout layout) {
        layout.addComponent(new SomeComposite());
    }

    // NOTE: This has no purpose
    public void firing(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.eventlistener.firing
        final TextField tf = new TextField("Enter Value");
        final Label label = new Label();
        
        tf.addValueChangeListener(
          new Property.ValueChangeListener() {
            private static final long serialVersionUID = 4667853618866383973L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                label.setValue("Entered: " +
                               tf.getValue());
            }
        });
        
        layout.addComponent(tf);
        layout.addComponent(label);
        // END-EXAMPLE: application.eventlistener.firing
    }
}
