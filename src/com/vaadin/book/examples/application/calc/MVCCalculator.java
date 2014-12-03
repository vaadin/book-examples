package com.vaadin.book.examples.application.calc;

import java.util.Iterator;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * A simple calculator using Vaadin.
 * 
 */
public class MVCCalculator extends UI {
    private static final long serialVersionUID = 6529452791144362380L;
/*
    /** The model **/
    class CalculatorModel {
        private double current = 0.0;
        private double stored = 0.0;
        private char lastOperationRequested = 'C';
        
        public void reset() {
            stored = 0.0;
        }

        public void add(double value) {
            stored += value;
        }

        public void multiply(double value) {
            stored *= value;
        }
    }

    class CalculatorController implements ClickListener {
        CalculatorModel model;
        CalculatorView  view;
        
        public CalculatorController(CalculatorModel model,
                                    CalculatorView  view) {
            this.model = model;
            this.view  = view;
        }

        @Override
        public void buttonClick(ClickEvent event) {
            // TODO Auto-generated method stub
            
        }
    }    
    
    class CalculatorView extends CustomComponent {
        private final Label display = new Label("0.0");
        
        GridLayout layout;

        public CalculatorView() {
            // Create the main layout for the view
            // (4 columns, 5 rows)
            layout = new GridLayout(4, 5);
            setCompositionRoot(layout);
    
            // Create a result label that spans over all
            // the 4 columns in the first row
            layout.addComponent(display, 0, 0, 3, 0);
    
            // The operations for the calculator in the order
            // they appear on the screen (left to right, top
            // to bottom)
            String[] operations = new String[] {
                "7", "8", "9", "/", "4", "5", "6",
                "*", "1", "2", "3", "-", "0", "=", "C", "+" };
    
            for (String caption : operations) {
                Button button = new Button(caption);
                layout.addComponent(button);
            }
        }
        
        /** Adds the given listener to listen all buttons */
        public void addButtonListener(ClickListener listener) {
            for (Iterator<Component> i = layout.iterator(); i.hasNext();)
                ((Button)i.next()).addListener(listener);
        }
    }

    /*
     * Application.init is called once for each application. Here it creates the
     * UI and connects it to the business logic.
     */
    
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Calculator");
        
    }

    // Event handler for button clicks. Called for all the buttons in the
    // application.
    public void buttonClick(ClickEvent event) {

        // Get the button that was clicked
        Button button = event.getButton();

        // Get the requested operation from the button caption
        char requestedOperation = button.getCaption().charAt(0);

        // Calculate the new value
//        double newValue = calculate(requestedOperation);

        // Update the result label with the new value
//        display.setValue(newValue);

    }
/*
    // Calculator "business logic" implemented here to keep the example minimal
    private double calculate(char requestedOperation) {
        if ('0' <= requestedOperation && requestedOperation <= '9') {
            current = current * 10
                    + Double.parseDouble("" + requestedOperation);
            return current;
        }
        switch (lastOperationRequested) {
        case '+':
            stored += current;
            break;
        case '-':
            stored -= current;
            break;
        case '/':
            stored /= current;
            break;
        case '*':
            stored *= current;
            break;
        case 'C':
            stored = current;
            break;
        }
        lastOperationRequested = requestedOperation;
        current = 0.0;
        if (requestedOperation == 'C') {
            stored = 0.0;
        }
        return stored;
    }
*/
}
