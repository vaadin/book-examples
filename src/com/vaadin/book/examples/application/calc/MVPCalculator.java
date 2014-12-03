package com.vaadin.book.examples.application.calc;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MVPCalculator  extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 6529452791144362380L;

    // BEGIN-EXAMPLE: application.architecture.mvp
    /** The model **/
    class CalculatorModel {
        private double value = 0.0;
        
        public void clear() {
            value = 0.0;
        }

        public void add(double arg) {
            value += arg;
        }

        public void multiply(double arg) {
            value *= arg;
        }

        public void divide(double arg) {
            if (arg != 0.0)
                value /= arg;
        }
        
        public double getValue() {
            return value;
        }
        
        public void setValue(double value) {
            this.value = value;
        }
    }

    /** Implementation-independent calculator view interface */
    interface CalculatorView {
        public void setDisplay(double value);

        interface CalculatorViewListener {
            void buttonClick(char operation);
        }
        public void addListener(CalculatorViewListener listener);
    }
    
    class CalculatorViewImpl extends CustomComponent
            implements CalculatorView, ClickListener {
        private static final long serialVersionUID = -1323925563596979253L;

        private Label display = new Label("0.0");

        public CalculatorViewImpl() {
            GridLayout layout  = new GridLayout(4, 5);

            // Create a result label that spans over all
            // the 4 columns in the first row
            layout.addComponent(display, 0, 0, 3, 0);
    
            // The operations for the calculator in the order
            // they appear on the screen (left to right, top
            // to bottom)
            String[] operations = new String[] {
                "7", "8", "9", "/", "4", "5", "6",
                "*", "1", "2", "3", "-", "0", "=", "C", "+" };

            // Add buttons and have them send click events
            // to this class
            for (String caption: operations)
                layout.addComponent(new Button(caption, this));

            setCompositionRoot(layout);
        }
        
        public void setDisplay(double value) {
            display.setValue(Double.toString(value));
        }

        /* Only the presenter registers one listener... */
        List<CalculatorViewListener> listeners =
                new ArrayList<CalculatorViewListener>();

        public void addListener(CalculatorViewListener listener) {
            listeners.add(listener);
        }

        /** Relay button clicks to the presenter with an
         *  implementation-independent event */
        @Override
        public void buttonClick(ClickEvent event) {
            for (CalculatorViewListener listener: listeners)
                listener.buttonClick(event.getButton()
                                     .getCaption().charAt(0));
        }
    }

    /** The presenter */
    class CalculatorPresenter
            implements CalculatorView.CalculatorViewListener {
        CalculatorModel model;
        CalculatorView  view;

        private double current = 0.0;
        private char   lastOperationRequested = 'C';
        
        public CalculatorPresenter(CalculatorModel model,
                                   CalculatorView  view) {
            this.model = model;
            this.view  = view;
            
            view.setDisplay(current);            
            view.addListener(this);
        }

        @Override
        public void buttonClick(char operation) {
            // Handle digit input
            if ('0' <= operation && operation <= '9') {
                current = current * 10
                        + Double.parseDouble("" + operation);
                view.setDisplay(current);
                return;
            }

            // Execute the previously input operation
            switch (lastOperationRequested) {
            case '+':
                model.add(current);
                break;
            case '-':
                model.add(-current);
                break;
            case '/':
                model.divide(current);
                break;
            case '*':
                model.multiply(current);
                break;
            case 'C':
                model.setValue(current);
                break;
            } // '=' is implicit

            lastOperationRequested = operation;

            current = 0.0;
            if (operation == 'C')
                model.clear();
            view.setDisplay(model.getValue());
        }
    }    
    
    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
    
        // Create the model and the Vaadin view implementation
        CalculatorModel      model     = new CalculatorModel();
        CalculatorViewImpl view      = new CalculatorViewImpl();
        
        // The presenter binds the model and view together
        new CalculatorPresenter(model, view);
        
        // The view implementation is a Vaadin component
        layout.addComponent(view);
        
        setCompositionRoot(layout);
    }
    // END-EXAMPLE: application.architecture.mvp
}
