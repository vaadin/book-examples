package com.vaadin.book.examples.themes;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.colorpicker.ColorChangeEvent;
import com.vaadin.ui.components.colorpicker.ColorChangeListener;

public class ThemeTricksExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 6580750126724601997L;
    
    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("pointertypes".equals(context))
            pointerTypes(layout);
        else if ("cssinjection".equals(context))
            cssinjection(layout);
        else if ("webfonts".equals(context))
            webfonts(layout);
        else
            layout.addComponent(new Label("Invalid context "+ context));
            
        setCompositionRoot(layout);
    }
    
    void pointerTypes(VerticalLayout layout) {
        // BEGIN-EXAMPLE: themes.misc.pointertypes
        final ComboBox select = new ComboBox("Pointer Type");
        select.setInputPrompt("Select a Type");

        // List all the pointer types in CSS
        String pointers[] = {"auto", "crosshair", "default",
                "help", "move", "pointer", "progress",
                "text", "wait", "inherit"};
        for (int i=0; i<pointers.length; i++)
            select.addItem(pointers[i]);
        
        select.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1716727013301055957L;

            public void valueChange(ValueChangeEvent event) {
                // Set the style for the root component
                getUI().setStyleName("select-" +
                        (String) select.getValue());
            }
        });
        select.setImmediate(true);
        // END-EXAMPLE: themes.misc.pointertypes
        select.setNullSelectionAllowed(false);

        layout.addComponent(select);
    }

    void cssinjection(VerticalLayout layout) {
        // BEGIN-EXAMPLE: themes.misc.cssinjection
        // Something for which we change the CSS
        final Label label = new Label("This Needs to be Colored");
        label.addStyleName("coloredlabel");
        
        // Some UI logic to change CSS
        ColorPicker colorPicker = new ColorPicker("Set the Color Here");
        colorPicker.addColorChangeListener(new ColorChangeListener() {
            private static final long serialVersionUID = 2499972178251400906L;

            @Override
            public void colorChanged(ColorChangeEvent event) {
                Page.getCurrent().getStyles().add(
                    ".v-label-coloredlabel {color: " +
                    event.getColor().getCSS() + ";}");
            }
        });
        
        layout.addComponents(label, colorPicker);
        // END-EXAMPLE: themes.misc.cssinjection
    }
    
    void webfonts(VerticalLayout layout) {
        // EXAMPLE-REF: themes.misc.webfonts com.vaadin.book.BookExamplesUI themes.misc.webfonts
        // BEGIN-EXAMPLE: themes.misc.webfonts
        // Something for which we change the CSS
        Label label = new Label("Hello, world!");
        label.addStyleName("usethewebfont");
        layout.addComponent(label);
        // END-EXAMPLE: themes.misc.webfonts
    }
}
