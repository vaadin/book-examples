package com.vaadin.book.examples.application;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class MVCExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("link".equals(context))
            link(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.architecture.mvc.basic
        // The model
        class Model {
            String name;
            int    age;
            double weight;
            
            public Model(String name, int age, double weight) {
                this.name   = name;
                this.age    = age;
                this.weight = weight;
            }
            
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public int getAge() {
                return age;
            }
            public void setAge(int age) {
                this.age = age;
            }
            public double getWeight() {
                return weight;
            }
            public void setWeight(double weight) {
                this.weight = weight;
            }
        }
        
        class View {
            TextField name   = new TextField("Name");
            TextField age    = new TextField("Age");
            TextField weight = new TextField("Weight");
        }
        
        // The view
        //TextField tf = new TextField("Name", name);

        Button button = new Button("Click Me!");
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059414138237L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Thank You!");
            }
        });
        
        layout.addComponent(button);
        // BEGIN-EXAMPLE: application.architecture.mvc.basic
    }

    void link(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.link
        // Create a button
        Button button = new Button("Click Me!");
        
        // Make it look like a hyperlink
        button.addStyleName(Reindeer.BUTTON_LINK);
        
        // Handle clicks
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059414138237L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Thank You!");
            }
        });
        layout.addComponent(button);
        // END-EXAMPLE: component.button.link
    }
}
