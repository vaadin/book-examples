package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class OptionGroupExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("singlemultiple".equals(context))
            singlemultiple(layout);
        else if ("icons".equals(context))
            icons(layout);
        else if ("disabling".equals(context))
            disabling(layout);
        else if ("styling".equals(context))
            styling(layout);
        else
            layout.addComponent(new Label("Invalid context " + context));
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.basic
        OptionGroup group = new OptionGroup("My Group");
        group.addItems("One", "Two", "Three");
        // END-EXAMPLE: component.select.optiongroup.basic

        layout.addComponent(group);
    }

    void singlemultiple(VerticalLayout vlayout) {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.select.optiongroup.singlemultiple
        // A single-select radio button group
        OptionGroup single = new OptionGroup("Single Selection");
        single.addItems("Single", "Sola", "Yksi");
        
        // A multi-select check box group
        OptionGroup multi = new OptionGroup("Multiple Selection");
        multi.setMultiSelect(true);
        multi.addItems("Many", "Muchos", "Monta");
        // END-EXAMPLE: component.select.optiongroup.singlemultiple

        layout.setSpacing(true);
        layout.addComponents(single, multi);
        vlayout.addComponent(layout);
    }

    void icons(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.icons
        OptionGroup group = new OptionGroup("My Icons");
        group.addItem("Russian");
        group.addItem("Greek");
        group.addItem("Cultural");
        
        // Set the icons
        // WARNING: This doesn't work. See ticket #5608.
        group.setItemIcon("Russian",  new ThemeResource("img/smiley2-20px.png"));
        group.setItemIcon("Greek",    new ThemeResource("img/smiley2-20px.png"));
        group.setItemIcon("Cultural", new ThemeResource("img/smiley2-20px.png"));
        // END-EXAMPLE: component.select.optiongroup.icons

        layout.addComponent(group);
    }

    void disabling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.disabling
        // Have an option group
        OptionGroup group = new OptionGroup("My Disabled Group");
        group.addItem("One");
        group.addItem("Two");
        group.addItem("Three");

        // Disable one item
        group.setItemEnabled("Two", false);
        // END-EXAMPLE: component.select.optiongroup.disabling
        
        layout.addComponent(group);
    }

    void styling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.styling
        OptionGroup group = new OptionGroup("Horizontal Group");
        group.addItem("One");
        group.addItem("Two");
        group.addItem("Three");
        
        // Lay the items out horizontally
        group.addStyleName("horizontal");

        // You can also say this if you like:
        group.setSizeUndefined();
        // END-EXAMPLE: component.select.optiongroup.styling
        
        layout.setSizeUndefined();
        layout.addComponent(group);
    }
    
    Container createSelectData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("caption", String.class, null);
        return container;
    }
}
