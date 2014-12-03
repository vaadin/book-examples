package com.vaadin.book.examples.advanced;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class LoggingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        
        setCompositionRoot(layout);
    }
    
    public static final String basicDescription =
        "<h1>Logging</h1>"+
        "<p>You can use <tt>java.util.logging.Logger</tt> for logging.</p>";

    // BEGIN-EXAMPLE: advanced.logging.basic
    // BOOK: advanced.logging
    // Some class that needs logging
    class MyClass implements Serializable {
        private static final long serialVersionUID = 478027883833208043L;

        // In a top-level class this should be static
        private final Logger logger = Logger.getLogger(MyClass.class.getName());
        
        public void myMethod() {
            logger.entering(getClass().getSimpleName(), "myMethod");
            
            try {
                // Do something that might fail
                FileInputStream in = new FileInputStream("foobar.tzt");
                in.close();
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "EPIC FAIL!", e);
            } catch (IOException e) {
                logger.log(Level.WARNING, "MULTIFAIL!", e);
            }
            
            logger.exiting(getClass().getSimpleName(), "myMethod");
        }
    }
    
    void basic (VerticalLayout layout) {
        final MyClass logged = new MyClass();

        // User interaction that causes log events 
        Button fail = new Button("Fail", new Button.ClickListener() {
            private static final long serialVersionUID = -3841054978844222939L;

            public void buttonClick(ClickEvent event) {
                logged.myMethod();
            }
        });
        layout.addComponent(fail);
        
        // Show the log
        final Panel logPanel = new Panel("Log");
        logPanel.setWidth("50em");
        logPanel.setHeight("20em");
        layout.addComponent(logPanel);
        
        final Label logView = new Label("", ContentMode.PREFORMATTED);
        logPanel.setContent(logView);

        // TODO Vaadin 7 Refresher
        /*
        // Needed to hack around problem with panel scroll-to-bottom
        final Refresher refresher = new Refresher();
        refresher.addListener(new RefreshListener() {
            private static final long serialVersionUID = 1L;
            
            public void refresh(Refresher source) {
                // Keep it at the end
                logPanel.setScrollTop(1000000);
                logPanel.requestRepaint();
                source.setRefreshInterval(0); // Disable
            }
        });
        layout.addComponent(refresher);
        */
        
        // Get the same logger here
        final Logger logger = Logger.getLogger(MyClass.class.getName());
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (! isLoggable(record))
                    return;
                
                if (getFormatter() == null)
                    setFormatter(new SimpleFormatter());
                
                String content = (String) logView.getValue();
                String entry = getFormatter().format(record);
                String newContent = content + "\n" + entry;
                logView.setValue(newContent);
                
                // Scroll to the end + hack
                logPanel.setScrollTop(1000000); // Keep it at the end
                
                // TODO Vaadin 7
                // refresher.setRefreshInterval(100); // Enable
            }
            
            @Override
            public void flush() {
            }
            
            @Override
            public void close() throws SecurityException {
            }
        });

        // Button to clear the log
        layout.addComponent(new Button("Clear Log", new Button.ClickListener() {
            private static final long serialVersionUID = 71328414239382952L;

            public void buttonClick(ClickEvent event) {
                logView.setValue("");
            }
        }));
        
        layout.setSpacing(true);
    }
    // END-EXAMPLE: advanced.logging.basic
}
