package com.vaadin.book.examples.component;

import java.io.Serializable;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class SelectExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout rlayout) {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.select.common.basic
        // Create the selection component
        ComboBox select = new ComboBox("My Select");
        
        // Add some items (the given ID is used as item caption)
        select.addItem("Io");
        select.addItem("Europa");
        select.addItem("Ganymedes");
        select.addItem("Callisto");
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);

        // Handle selection change
        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                event.getProperty().getValue())));
        // END-EXAMPLE: component.select.common.basic
         
        layout.addComponent(select);
        rlayout.addComponent(layout);
        rlayout.setHeight("300px"); // Space for drop-down list
    }
    
    public void givencaption(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.adding.givencaption
        // Create a selection component
        ComboBox select = new ComboBox("My Select");

        // Add the items by using the caption as item ID
        String captions[] = {"Io", "Europa", "Ganymedes", "Callisto"};
        for (String caption: captions)
            select.addItem(caption);
        
        // Preselect by item ID (which is the caption)
        select.select("Io");

        // Handle selection change
        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                event.getProperty().getValue())));
        // END-EXAMPLE: component.select.common.adding.givencaption

        select.setNullSelectionAllowed(false);
        layout.addComponent(select);
    }

    public void givenitemid(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.adding.givenitemid
        // Create a selection component
        ComboBox select = new ComboBox("My Select");

        String captions[] = {"Io", "Europa", "Ganymedes", "Callisto"};
        for (int i=0; i<captions.length; i++) {
            // Use the integer as item ID
            select.addItem(i);

            // Set the caption for the added item
            select.setItemCaption(i, captions[i]);
        }
        
        // Preselect by item ID
        select.setValue(0); // Select first item

        // Also getValue() returns the integer item IDs
        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                event.getProperty().getValue())));
        // END-EXAMPLE: component.select.common.adding.givenitemid

        select.setNullSelectionAllowed(false);
        layout.addComponent(select);
    }

    public void generateditemid(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.adding.generateditemid
        // Create a selection component
        ComboBox sel = new ComboBox("My Select");

        String captions[] = {"Io", "Europa", "Ganymedes", "Callisto"};
        for (String caption: captions) {
            // Add an item with a generated ID
            Object itemId = sel.addItem();

            // Set the item caption
            sel.setItemCaption(itemId, caption);
        }
        
        // Select the first item by iterating to first item ID (Java 8)
        sel.select(sel.getItemIds().stream().findFirst().orElse(null));
        
        sel.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                event.getProperty().getValue())));
        // END-EXAMPLE: component.select.common.adding.generateditemid

        sel.setNullSelectionAllowed(false);
        layout.addComponent(sel);
    }

    public void container(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.container
        // Have a container data source of some kind
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);

        // Add some items
        String names[] = {"Sirius", "Canopus", "Arcturus"};
        for (String name: names) {
            Object itemId = container.addItem();
            container.getContainerProperty(itemId, "name")
                .setValue(name);
        }

        // Create a selection component bound to the container
        OptionGroup group = new OptionGroup("My Select", container);
        
        // Use the container property value for caption
        group.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        group.setItemCaptionPropertyId("name");

        // Handle selection change
        group.addValueChangeListener(event -> // Java 8
                Notification.show("Selected: " +
                        event.getProperty().getValue()));
        // END-EXAMPLE: component.select.common.container
         
        layout.addComponent(group);
    }

    public void explicitdefaultsid(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.captions.explicitdefaultsid
        // Create a selection component
        ComboBox select = new ComboBox("Moons of Mars");
        select.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
        
        // If caption is not given explicitly, default to the ID
        select.addItem(new Integer(1));
        
        // Set item caption for this item explicitly
        select.addItem(2); // same as "new Integer(2)"
        select.setItemCaption(2, "Deimos");

        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                event.getProperty().getValue())));
        // END-EXAMPLE: component.select.common.captions.explicitdefaultsid
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    public void captionmodeid(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.captions.captionmodeid
        ComboBox select = new ComboBox("Inner Planets");
        select.setItemCaptionMode(ItemCaptionMode.ID);
        
        // A class that implements toString()
        class PlanetId extends Object implements Serializable {
            private static final long serialVersionUID = -7452707902301121901L;

            String planetName;
            PlanetId (String name) {
                planetName = name;
            }
            public String toString () {
                return "The Planet " + planetName;
            }
        }

        // Use such objects as item identifiers
        String planets[] = {"Mercury", "Venus", "Earth", "Mars"};
        for (int i=0; i<planets.length; i++)
            select.addItem(new PlanetId(planets[i]));

        select.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected " +
                event.getProperty().getValue())));
        // END-EXAMPLE: component.select.common.captions.captionmodeid
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    // BEGIN-EXAMPLE: component.select.common.captions.captionproperty
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

    public void captionproperty(VerticalLayout layout) {
        // Have a bean container to put the beans in
        BeanItemContainer<Planet> container =
            new BeanItemContainer<Planet>(Planet.class);

        // Put some example data in it
        container.addItem(new Planet(1, "Mercury"));
        container.addItem(new Planet(2, "Venus"));
        container.addItem(new Planet(3, "Earth"));
        container.addItem(new Planet(4, "Mars"));

        // Create a selection component bound to the container
        final ComboBox select = new ComboBox("Planets", container);

        // Set the caption mode to read the caption directly
        // from the 'name' property of the bean
        select.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        select.setItemCaptionPropertyId("name");

        // Handle selects
        select.addValueChangeListener(event -> { // Java 8
            // Get the selected item
            Object itemId = event.getProperty().getValue();
            BeanItem<?> item = (BeanItem<?>) select.getItem(itemId);
            
            // Get the actual bean and use the data
            Planet planet = (Planet) item.getBean();
            
            layout.addComponent(new Label("Clicked planet #" +
                                          planet.getId()));
        });
        select.setImmediate(true);
        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.common.captions.captionproperty
        
        layout.addComponent(select);
    }
    
    public void preselecting(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.preselecting
        // Create a selection component
        ComboBox select = new ComboBox("My ComboBox");
        
        // Add items with given item IDs
        select.addItem("Mercury");
        select.addItem("Venus");
        select.addItem("Earth");
        
        // Select an item using the item ID
        select.setValue("Earth");
        // END-EXAMPLE: component.select.common.preselecting
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    public void icons(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.icons
        // Create the selection component
        ComboBox select = new ComboBox("Target to Destroy");
        
        // Add some items
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Neptune", "Uranus"};
        for (String planet: planets) {
            select.addItem(planet);
            select.setItemIcon(planet,
                new ThemeResource("img/planets/"+planet+"_small.png"));
        }
        
        select.select("Earth");
        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.common.icons
         
        layout.addComponent(select);
    }

    public void multiselect(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.common.multiselect
        // Create the selection component with some items
        OptionGroup group = new OptionGroup("My Select");
        group.addItems("Io", "Europa", "Ganymedes", "Kallisto");
        
        // Enable the multiple selection mode
        group.setMultiSelect(true);
        
        // Handle selection change
        group.addValueChangeListener(event -> // Java 8
                Notification.show("Selected: " +
                        event.getProperty().getValue()));
        // END-EXAMPLE: component.select.common.multiselect
         
        layout.addComponent(group);
    }

    public void hierarchical(VerticalLayout layout) {
        NativeSelect select = new NativeSelect("Hierarchical Select");
        select.setContainerDataSource(TreeExample.createTreeContent());
        
        layout.addComponent(select);
    }
}
