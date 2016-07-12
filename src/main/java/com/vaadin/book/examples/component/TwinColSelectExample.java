package com.vaadin.book.examples.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

public class TwinColSelectExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("captions".equals(context))
            captions(layout);
        else if ("icons".equals(context))
            icons(layout);
        else if ("css".equals(context))
            css(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.twincolselect.basic
        // BOOK: components.twincolselect
        TwinColSelect select = new TwinColSelect("Select Targets");
        
        // Put some items in the select
        select.addItems("Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune");

        // Few items, so we can set rows to match item count
        select.setRows(select.size());
        
        // Preselect a few items by creating a set
        select.setValue(new HashSet<String>(
            Arrays.asList("Venus", "Earth", "Mars")));

        // Handle value changes
        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected: " +
                    event.getProperty().getValue())));

        layout.addComponent(select);
        // END-EXAMPLE: component.select.twincolselect.basic
    }

    void captions(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.twincolselect.captions
        // BOOK: components.twincolselect
        final TwinColSelect select =
            new TwinColSelect("Select Targets to Destroy");

        // Set the column captions (optional)
        select.setLeftColumnCaption("These are left");
        select.setRightColumnCaption("These are done for");
        
        // Put some data in the select
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (int pl=0; pl<planets.length; pl++)
            select.addItem(planets[pl]);

        // Set the number of visible items
        select.setRows(planets.length);

        // Set the number of visible items
        select.setColumns(10);
        
        // Preselect a few items
        HashSet<String> preselected = new HashSet<String>();
        Collections.addAll(preselected, "Venus", "Earth", "Mars");
        select.setValue(preselected);
        
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 6081009810084203857L;
            
            public void valueChange(ValueChangeEvent event) {
                BookExamplesUI.getLogger().info(event.getProperty().getType().getName());
                if (event.getProperty().getValue() != null)
                    BookExamplesUI.getLogger().info(event.getProperty().getValue().getClass().getName());
            }
        });
        select.setImmediate(true);
        
        layout.addComponent(select);
        // END-EXAMPLE: component.select.twincolselect.captions
    }

    /**
     * Does not work because TwinColSelect items can't have icons.
     */
    void icons(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.twincolselect.icons
        // BOOK: components.selecting#twincolselect
        final TwinColSelect select =
            new TwinColSelect("Select Targets to Destroy");
        
        // Put some data in the select
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (int pl=0; pl<planets.length; pl++) {
            select.addItem(planets[pl]);
            select.setItemIcon(planets[pl],
                    new ThemeResource("img/planets/Earth_small.png"));
        }

        // Preselect a few items
        HashSet<String> preselected = new HashSet<String>();
        Collections.addAll(preselected, "Venus", "Earth", "Mars");
        select.setValue(preselected);
        
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 6081009810084203857L;
            
            public void valueChange(ValueChangeEvent event) {
                BookExamplesUI.getLogger().info(event.getProperty().getType().getName());
                if (event.getProperty().getValue() != null)
                    BookExamplesUI.getLogger().info(event.getProperty().getValue().getClass().getName());
            }
        });
        select.setImmediate(true);
        
        layout.addComponent(select);
        // END-EXAMPLE: component.select.twincolselect.icons
    }

    void css(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.twincolselect.css
        // BOOK: components.selecting#twincolselect
        final TwinColSelect select =
            new TwinColSelect("Select Targets to Destroy");

        select.addStyleName("twincolselectexample");
        
        // Set the column captions (optional)
        select.setLeftColumnCaption("These are left");
        select.setRightColumnCaption("These are done for");
        
        // Put some data in the select
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (int pl=0; pl<planets.length; pl++)
            select.addItem(planets[pl]);

        // Set the number of visible items
        select.setHeight("200px");
        select.setRows(6);

        // Set width by the number of character columns
        select.setColumns(10);
        
        // Preselect a few items
        HashSet<String> preselected = new HashSet<String>();
        Collections.addAll(preselected, "Venus", "Earth", "Mars");
        select.setValue(preselected);
        
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 6081009810084203857L;
            
            public void valueChange(ValueChangeEvent event) {
                BookExamplesUI.getLogger().info(event.getProperty().getType().getName());
                if (event.getProperty().getValue() != null)
                    BookExamplesUI.getLogger().info(event.getProperty().getValue().getClass().getName());
            }
        });
        select.setImmediate(true);
        
        layout.addComponent(select);
        // END-EXAMPLE: component.select.twincolselect.css
    }
}
