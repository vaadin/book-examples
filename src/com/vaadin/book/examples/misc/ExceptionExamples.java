package com.vaadin.book.examples.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ExceptionExamples extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("concurrentmodification".equals(context))
            concurrentmodification(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public final static String concurrentmodificationDescription =
            "<h1>How to Get ConcurrentModificationException and How to Not</h1>" +
            "<p>You can get it easily by modifying a list while iterating over it.</p>";
    
    void concurrentmodification(VerticalLayout layout) {
        // BEGIN-EXAMPLE: misc.exception.concurrentmodification
        Label position = new Label("-");
        position.setCaption("Position");
        layout.addComponent(position);

        Label error = new Label("- no error -",
                                Label.CONTENT_PREFORMATTED);
        error.setCaption("Exception Message");
        layout.addComponent(error);
        
        try {
            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList("One", "Two", "xxx", "Three", "xxx", "Four"));
            for (String str: list) {
                position.setValue(str);
                
                if ("xxx".equals(str))
                    list.remove(str);
            }
        } catch (Exception e) {
            error.setValue(e.toString());
        }

        {
            // Have a list like before
            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList("One", "Two", "xxx", "Three", "xxx", "Four"));
            
            // The better way: remove with the iterator
            for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
                String str = iter.next();
                if ("xxx".equals(str))
                    iter.remove();
            }
            
            // Show it to prove it
            Label result = new Label(list.toString());
            result.setCaption("Result by removing with iterator");
            layout.addComponent(result);
        }

        {
            // Have a list like before
            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList("One", "Two", "xxx", "Three", "xxx", "Four"));
            
            // If removing just a single item
            for (String str: list) {
                if ("xxx".equals(str)) {
                    list.remove(str);
                    break; // Must stop iteration to avoid CME
                }
            }
            
            // Show it to prove it
            Label result = new Label(list.toString());
            result.setCaption("Only one item removed");
            layout.addComponent(result);
        }
        // END-EXAMPLE: misc.exception.concurrentmodification
        layout.setSpacing(true);
    }
}
