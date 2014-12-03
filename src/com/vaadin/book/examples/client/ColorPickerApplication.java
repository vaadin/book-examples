package com.vaadin.book.examples.client;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Demonstration application that shows how to use a simple
 * custom client-side GWT component, the ColorPicker.
 */
@SuppressWarnings("serial")
public class ColorPickerApplication extends UI {

    /* The custom component. */
    ColorPicker colorselector = new ColorPicker();

    /* Another component. */
    Label colorname;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Color Picker Demo");

        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        
       // Listen for value change events in the custom component,
        // triggered when user clicks a button to select another color.
        colorselector.addValueChangeListener (new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                // Provide some server-side feedback
                colorname.setValue("Selected color: " +
                                   colorselector.getColor());
            }
        });
        content.addComponent(colorselector);

        // Add another component to give feedback from server-side code
        colorname = new Label("Selected color: " +
                              colorselector.getColor());
        content.addComponent(colorname);

        // Server-side manipulation of the component state
        final Button button = new Button("Set to white");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                colorselector.setColor("white");
            }
        });
        content.addComponent(button);
    }
}
