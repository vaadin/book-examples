package com.vaadin.book.examples.component;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ProgressBarExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;
    
    String context;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        this.context = context;
        if ("basic".equals(context))
            basic(layout);
        else if ("thread".equals(context))
            thread(layout);
        else if ("indeterminate".equals(context))
            indeterminate(layout);
        else {
            setCompositionRoot(new Label("Invalid context " + context));
            return;
        }
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.progressbar.basic
        final ProgressBar bar = new ProgressBar(0.0f);
        layout.addComponent(bar);
        
        layout.addComponent(new Button("Increase",
            new ClickListener() {
            private static final long serialVersionUID = -2146326968842162496L;

            @Override
            public void buttonClick(ClickEvent event) {
                float current = bar.getValue();
                if (current < 1.0f)
                    bar.setValue(current + 0.10f);
            }
        }));
        // END-EXAMPLE: component.progressbar.basic
        
        layout.setSpacing(true);
    }

    void thread(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.progressbar.thread
        HorizontalLayout barbar = new HorizontalLayout();
        layout.addComponent(barbar);
        
        // Create the indicator, disabled until progress is started
        final ProgressBar progress = new ProgressBar(new Float(0.0));
        progress.setEnabled(false);
        barbar.addComponent(progress);
        
        final Label status = new Label("not running");
        barbar.addComponent(status);

        // A button to start progress
        final Button button = new Button("Click to start");
        layout.addComponent(button);

        // A thread to do some work
        class WorkThread extends Thread {
            // Volatile because read in another thread in access()
            volatile double current = 0.0;

            @Override
            public void run() {
                // Count up until 1.0 is reached
                while (current < 1.0) {
                    current += 0.01;

                    // Do some "heavy work"
                    try {
                        sleep(50); // Sleep for 50 milliseconds
                    } catch (InterruptedException e) {}

                    // Update the UI thread-safely
                    UI.getCurrent().access(new Runnable() {
                        @Override
                        public void run() {
                            progress.setValue(new Float(current));
                            if (current < 1.0)
                                status.setValue("" +
                                    ((int)(current*100)) + "% done");
                            else
                                status.setValue("all done");
                        }
                    });
                }
                
                // Show the "all done" for a while
                try {
                    sleep(2000); // Sleep for 2 seconds
                } catch (InterruptedException e) {}

                // Update the UI thread-safely
                UI.getCurrent().access(new Runnable() {
                    @Override
                    public void run() {
                        // Restore the state to initial
                        progress.setValue(new Float(0.0));
                        progress.setEnabled(false);
                                
                        // Stop polling
                        UI.getCurrent().setPollInterval(-1);
                        
                        button.setEnabled(true);
                        status.setValue("not running");
                    }
                });
            }
        }

        // Clicking the button creates and runs a work thread
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -1639461207460313184L;

            public void buttonClick(ClickEvent event) {
                final WorkThread thread = new WorkThread();
                thread.start();

                // Enable polling and set frequency to 0.5 seconds
                UI.getCurrent().setPollInterval(500);

                // Disable the button until the work is done
                progress.setEnabled(true);
                button.setEnabled(false);

                status.setValue("running...");
            }
        });
        // END-EXAMPLE: component.progressbar.thread
    }

    void indeterminate(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.progressbar.indeterminate
        final ProgressBar bar = new ProgressBar();
        bar.setIndeterminate(true);
        layout.addComponent(bar);
        // END-EXAMPLE: component.progressbar.indeterminate
    }
}
