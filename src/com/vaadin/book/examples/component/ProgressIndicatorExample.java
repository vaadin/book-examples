package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;

public class ProgressIndicatorExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;
    
    String context;

    public void init (String context) {
        this.context = context;
        if ("simple".equals(context))
            simple();
        else if ("thread".equals(context))
            thread();
    }
    
    @Override
    public void attach() {
        if ("counter".equals(context))
            counter();
    }
    
    void simple() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.progressindicator.basic
        // Progress indicator that causes server poll every 1 second
        ProgressIndicator indicator = new ProgressIndicator();

        layout.addComponent(indicator);
        // END-EXAMPLE: component.progressindicator.basic
        
        setCompositionRoot(layout);
    }

    void counter() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.progressindicator.counter
        // A counter to increment each call
        class Counter {
            Integer value = 0;
            
            public Integer increment() {
                return ++value;
            }
        }
        final Counter counter = new Counter();
        
        // A component to display the counter value
        final Label label = new Label("Count: -"); 
        layout.addComponent(label);

        // Progress indicator that causes server poll every second
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setIndeterminate(true);

        // On every poll (OR any other server request),
        // increment the counter
        /* TODO Vaadin 7: FIX 
        getApplication().getContext().addTransactionListener(
                new TransactionListener() {
            private static final long serialVersionUID = -6000175416405388484L;

            public void transactionStart(Application application,
                                         Object transactionData) {
                label.setValue("Count: " + counter.increment());
            }
            
            public void transactionEnd(Application application,
                                       Object transactionData) {
            }
        });
        */
        layout.addComponent(indicator);
        // END-EXAMPLE: component.progressindicator.counter
        
        setCompositionRoot(layout);
    }

    void thread() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.progressindicator.thread
        // Create the indicator
        HorizontalLayout hlayout = new HorizontalLayout();
        layout.addComponent(hlayout);
        
        final ProgressIndicator indicator =
            new ProgressIndicator(new Float(0.0));
        hlayout.addComponent(indicator);
        
        // Disable polling until needed
        indicator.setEnabled(false);

        final Label status = new Label("not running");
        hlayout.addComponent(status);

        // Add a button to start the progress
        final Button button = new Button("Click to start");
        layout.addComponent(button);

        // Another thread to do some work
        class WorkThread extends Thread {
            @Override
            public void run() {
                double current = 0.0;
                while (true) {
                    // Do some "heavy work"
                    try {
                        sleep(50); // Sleep for 50 milliseconds
                    } catch (InterruptedException e) {
                    }

                    // Grow the progress value until it reaches 1.0.
                    current += 0.01;
                    if (current > 1.0) { // Stay at max 1.0
                        indicator.setValue(new Float(1.0));
                        status.setValue("all done");
                    } else {
                        indicator.setValue(new Float(current));
                        status.setValue("" + ((int)(current*100)) + "% done");
                    }

                    // After the progress is full for a while, stop.
                    if (current > 1.5) {
                        // Restore the state to initial.
                        indicator.setValue(new Float(0.0));
                        
                        // Stop polling
                        indicator.setEnabled(false);

                        button.setEnabled(true);
                        status.setValue("not running");
                        break;
                    }
                }
            }
        }

        // Clicking the button creates and runs a work thread
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -1639461207460313184L;

            public void buttonClick(ClickEvent event) {
                final WorkThread thread = new WorkThread();
                thread.start();

                // Enable polling and set frequency to 0.5 seconds
                indicator.setEnabled(true);
                indicator.setPollingInterval(500);

                // Disable the button until the work is done
                button.setEnabled(false);

                status.setValue("running...");
            }
        });

        // END-EXAMPLE: component.progressindicator.thread
        
        setCompositionRoot(layout);
    }
}
