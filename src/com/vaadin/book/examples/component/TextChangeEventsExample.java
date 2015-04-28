package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.book.examples.component.table.TableExample;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TextChangeEventsExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4454143876393393750L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("counter".equals(context))
            counter(layout);
        else if ("filtering".equals(context))
            filtering(layout);
        
        setCompositionRoot(layout);
    }
    
    void counter(VerticalLayout layout) {
        HorizontalLayout hlayout = new HorizontalLayout();
        // BEGIN-EXAMPLE: component.textfield.textchangeevents.counter
        // BOOK: components.textfield#textchangeevents
        // Text field with maximum length
        final TextField tf = new TextField("My Eventful Field");
        tf.setValue("Initial content");
        tf.setMaxLength(20);

        // Counter for input length
        final Label counter = new Label();
        counter.setValue(tf.getValue().length() +
                         " of " + tf.getMaxLength());
        
        // Display the current length interactively in the counter
        tf.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1048639156493298177L;

            public void textChange(TextChangeEvent event) {
                int len = event.getText().length();
                counter.setValue(len + " of " + tf.getMaxLength());
            }
        });

        // The lazy mode is actually the default
        tf.setTextChangeEventMode(TextChangeEventMode.LAZY);
        // END-EXAMPLE: component.textfield.textchangeevents.counter
        
        /*
         * A test for a forum issue
        Button remove = new Button("Remove Listener", new ClickListener() {
            private static final long serialVersionUID = -1647156891238430973L;

            @Override
            public void buttonClick(ClickEvent event) {
                tf.removeTextChangeListener(listener);
            }
        });
        layout.addComponent(remove);
        */

        tf.setCursorPosition(tf.getValue().length());

        hlayout.addComponent(tf);
        hlayout.addComponent(counter);
        hlayout.setSpacing(true);
        hlayout.setComponentAlignment(counter, Alignment.BOTTOM_LEFT);
        layout.addComponent(hlayout);
    }
    
    void filtering(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textfield.textchangeevents.filtering
        // BOOK: components.textfield#textchangeevents
        // Text field for inputting a filter
        final TextField tf = new TextField("Name Filter");
        tf.focus();
        layout.addComponent(tf);

        // Create a table with some example content
        final Table table = new Table();
        table.setContainerDataSource(TableExample.generateContent());
        layout.addComponent(table);

        // Filter table according to typed input
        tf.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1048639156493298177L;
            
            Filter filter = null;

            public void textChange(TextChangeEvent event) {
                Filterable f = (Filterable)
                    table.getContainerDataSource();
                
                // Remove old filter
                if (filter != null)
                    f.removeContainerFilter(filter);
                
                // Set new filter for the "Name" column
                filter = new SimpleStringFilter("name", event.getText(),
                                                true, false);
                f.addContainerFilter(filter);
            }
        });
        // END-EXAMPLE: component.textfield.textchangeevents.filtering

        layout.setSpacing(true);
    }
}
