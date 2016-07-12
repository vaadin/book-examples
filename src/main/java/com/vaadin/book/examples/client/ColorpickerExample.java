package com.vaadin.book.examples.client;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ColorpickerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public final static String basicDescription =
            "<h1>Basic Widget Integration</h1>" +
            "<p></p>";
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: gwt.basic
        final ColorPicker colorpicker = new ColorPicker();
        layout.addComponent(colorpicker);
        
        final Label colorname = new Label("-");
        
        // Listen for value change events in the custom component,
        // triggered when user clicks a button to select another color.
        colorpicker.addValueChangeListener (new ValueChangeListener() {
            private static final long serialVersionUID = 7844511885218471927L;

            public void valueChange(ValueChangeEvent event) {
                // Provide some server-side feedback
                colorname.setValue("Selected color: " +
                                   colorpicker.getColor());
            }
        });
        // END-EXAMPLE: gwt.basic
    }
}
