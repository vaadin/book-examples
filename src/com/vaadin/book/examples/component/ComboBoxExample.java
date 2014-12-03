package com.vaadin.book.examples.component;

import java.io.Serializable;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class ComboBoxExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("filtering".equals(context))
            filtering(layout);
        else if ("enumtype".equals(context))
            enumtype(layout);
        else if ("preselecting".equals(context))
            preselecting(layout);
        else if ("newitemsallowed".equals(context))
            newItemsAllowed(layout);
        else if ("newitemhandler".equals(context))
            newItemHandler(layout);
        else if ("nullselection".equals(context))
            nullSelection(layout);
        else if ("resetselection".equals(context))
            resetSelection(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout rlayout) {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.select.combobox.basic
        ComboBox combobox = new ComboBox("Select One");
        combobox.setInvalidAllowed(false);
        combobox.setNullSelectionAllowed(false);
        
        // Add some items and specify their item ID.
        // The item ID is by default used as item caption.
        combobox.addItems("Io", "Europa", "Ganymedes", "Callisto");
        
        // Handle selection change
        combobox.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                combobox.getValue())));
        // END-EXAMPLE: component.select.combobox.basic

        layout.addComponent(combobox);
        rlayout.addComponent(layout);
        rlayout.setHeight("300px"); // Space for drop-down list
    }

    void filtering(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.filtering
        ComboBox combobox = new ComboBox("Enter containing substring");

        // Set the filtering mode
        combobox.setFilteringMode(FilteringMode.CONTAINS);

        // Set number of items in the suggestion pop-up
        combobox.setPageLength(5);

        // Fill the component with some items
        String[] planets = new String[] {
                "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune" };
        for (int i = 0; i < planets.length; i++)
            for (int j = 0; j < planets.length; j++)
                combobox.addItem(planets[j] + " to " + planets[i]);
        // END-EXAMPLE: component.select.combobox.filtering
        
        layout.addComponent(combobox);
        layout.setHeight("200px");
    }
    
    void preselecting(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.preselecting
        ComboBox combobox = new ComboBox("Some caption");
        
        // Add an item with a given ID
        combobox.addItem("GBP");
        
        // Select it using the item ID
        combobox.setValue("GBP");
        
        // Add some other items
        combobox.addItem("EUR");
        combobox.addItem("USD");
        // END-EXAMPLE: component.select.combobox.preselecting
         
        layout.addComponent(combobox);
    }

    // BEGIN-EXAMPLE: component.select.combobox.enumtype
    enum MyEnum {
        MERCURY("Mercury"),
        VENUS("Venus"),
        EARTH("Earth");
        
        private String name;
        private MyEnum (String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }

    void enumtype(VerticalLayout layout) {
        ComboBox combobox = new ComboBox("Some caption");
        combobox.setNullSelectionAllowed(false);
        
        combobox.addItem(MyEnum.MERCURY);
        combobox.addItem(MyEnum.VENUS);
        combobox.addItem(MyEnum.EARTH);
        
        // Preselect an item
        combobox.setValue(MyEnum.VENUS);

        layout.addComponent(combobox);
    }
    // END-EXAMPLE: component.select.combobox.enumtype

    void newItemsAllowed(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.newitemsallowed
        ComboBox combobox = new ComboBox("Some caption");
        combobox.setInvalidAllowed(false);
        combobox.setNullSelectionAllowed(false);
        combobox.addItem("GBP");
        combobox.addItem("EUR");
        combobox.addItem("USD");
        
        // Allow adding new items
        combobox.setNewItemsAllowed(true);
    
        layout.addComponent(combobox);
        // END-EXAMPLE: component.select.combobox.newitemsallowed
    }

    /** A bean with a "name" property. */
    public class Planet implements Serializable {
        private static final long serialVersionUID = 7725549394908524264L;

        int    id;
        String name;
        
        public Planet(int id, String name) {
            this.id   = id;
            this.name = name;
        }
        
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    void newItemHandler(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.newitemhandler
        // Have a bean container to put the beans in
        final BeanItemContainer<Planet> container =
            new BeanItemContainer<Planet>(Planet.class);

        // Put some example data in it
        container.addItem(new Planet(1, "Mercury"));
        container.addItem(new Planet(2, "Venus"));
        container.addItem(new Planet(3, "Earth"));
        container.addItem(new Planet(4, "Mars"));
        container.addItem(new Planet(5, "Jove"));

        final ComboBox select =
            new ComboBox("Select or Add a  Planet", container);
        select.setNullSelectionAllowed(false);
        
        // Use the name property for item captions
        select.setItemCaptionPropertyId("name");
        
        // Allow adding new items
        select.setNewItemsAllowed(true);
        select.setImmediate(true);

        // Custom handling for new items
        select.setNewItemHandler(new NewItemHandler() {
            private static final long serialVersionUID = 8368219559549759808L;

            @Override
            public void addNewItem(String newItemCaption) {
                // Create a new bean - can't set all properties
                Planet newPlanet = new Planet(0, newItemCaption);
                container.addBean(newPlanet);
                
                // Remember to set the selection to the new item
                select.select(newPlanet);
                
                Notification.show("Added new planet called " +
                                  newItemCaption);
            }
        });
    
        layout.addComponent(select);
        // END-EXAMPLE: component.select.combobox.newitemhandler
    }

    void nullSelection(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.nullselection
        ComboBox combobox = new ComboBox("My ComboBox");
        
        // Enable null selection
        combobox.setNullSelectionAllowed(true);
        
        // Add the item that marks 'null' value
        String nullitem = "-- none --";
        combobox.addItem(nullitem);
        
        // Designate it as the 'null' value marker
        combobox.setNullSelectionItemId(nullitem);
        
        // Add some other items
        for(int i=0; i<10; i++)
            combobox.addItem("Item " + i);
        // END-EXAMPLE: component.select.combobox.nullselection
        
        layout.addComponent(combobox);
        layout.setHeight("200px");
    }

    void resetSelection(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.resetselection
        // FORUM: http://vaadin.com/forum/-/message_boards/message/206665
        final ComboBox combobox = new ComboBox("My ComboBox");
        combobox.setInputPrompt("Select a value");
        layout.addComponent(combobox);
        
        // Enable null selection
        combobox.setNullSelectionAllowed(false);
        
        // Add some items
        for(int i=0; i<10; i++)
            combobox.addItem("Item " + i);

        // Previously selected value
        final Label value = new Label("Please select a value from the box");
        layout.addComponent(value);
        
        combobox.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = -5188369735622627751L;

            public void valueChange(ValueChangeEvent event) {
                if (combobox.getValue() != null) {
                    value.setValue("Selected: " +
                                   (String) combobox.getValue());
                
                    // Reset the ComboBox
                    combobox.setValue(null);
                    combobox.setInputPrompt("Select another value");
                }
            }
        });
        combobox.setImmediate(true);
        // END-EXAMPLE: component.select.combobox.resetselection
    }
}
