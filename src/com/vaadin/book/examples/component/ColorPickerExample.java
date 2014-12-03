package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.ColorPickerArea;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.colorpicker.ColorChangeEvent;
import com.vaadin.ui.components.colorpicker.ColorChangeListener;

public class ColorPickerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("colorpicker".equals(context))
            colorpicker(layout);
        else if ("colorpickerarea".equals(context))
            colorpickerarea(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    void colorpicker(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.colorpicker.colorpicker
        ColorPicker picker = new ColorPicker();
        picker.addColorChangeListener(new ColorChangeListener() {
            private static final long serialVersionUID = 593403711305397448L;

            @Override
            public void colorChanged(ColorChangeEvent event) {
                // Do something with the color
                layout.addComponent(new Label(
                    "Color is " + event.getColor().getCSS()));
            }
        });

        picker.setPosition(
            Page.getCurrent().getBrowserWindowWidth() / 2 - 246/2,
            Page.getCurrent().getBrowserWindowHeight() / 2 - 507/2);
        
        layout.addComponent(picker);
        // END-EXAMPLE: component.colorpicker.colorpicker
    }

    void colorpickerarea(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.colorpicker.colorpickerarea
        ColorPickerArea picker = new ColorPickerArea();
        picker.setWidth("300px");
        picker.setHeight("100px");
        picker.setColor(new Color(0, 100, 200));
        layout.addComponent(picker);
        
        picker.addColorChangeListener(new ColorChangeListener() {
            private static final long serialVersionUID = 593403711305397448L;

            @Override
            public void colorChanged(ColorChangeEvent event) {
                // Do something with the color
                layout.addComponent(new Label(
                    "Color is " + event.getColor().getCSS()));
            }
        });
        // END-EXAMPLE: component.colorpicker.colorpickerarea
    }
}
