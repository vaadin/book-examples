package com.vaadin.book.examples.component;

import java.util.Locale;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TextAreaExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4454143876393393750L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("textareaexample");

        if ("basic".equals(context))
            basic(layout);
        else if ("wordwrap".equals(context))
            wordwrap(layout);
        else if ("css".equals(context))
            css (layout);

        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textarea.basic
        // Create the area
        TextArea area = new TextArea("Big Area");
        
        // Put some content in it
        area.setValue("A row\n"+
                      "Another row\n"+
                      "Yet another row");
        // END-EXAMPLE: component.textarea.basic
        
        layout.addComponent(area);
    }
    
    void wordwrap(VerticalLayout root) {
        HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: component.textarea.wordwrap
        TextArea area1 = new TextArea("Wrapping");
        area1.setWordwrap(true); // The default
        area1.setValue("A quick brown fox jumps over the lazy dog");

        TextArea area2 = new TextArea("Nonwrapping");
        area2.setWordwrap(false);
        area2.setValue("Victor jagt zwölf Boxkämpfer quer "+
                       "über den Sylter Deich");
        // END-EXAMPLE: component.textarea.wordwrap
        
        layout.addComponent(area1);
        layout.addComponent(area2);
        layout.setSpacing(true);

        // This doesn't seem to have effect on browser's spell checker
        area2.setLocale(new Locale("de", "DE"));
        root.addComponent(layout);
    }
    
    void css(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.textarea.css
        TextField tf = new TextField("Fence around a text field");
        tf.addStyleName("tfwf");
        // END-EXAMPLE: component.textarea.css
        
        tf.setColumns(20);
        layout.addComponent(tf);
    }    
}
