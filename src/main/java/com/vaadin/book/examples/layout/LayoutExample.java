package com.vaadin.book.examples.layout;

import java.util.Stack;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class LayoutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8458669675797366833L;

    public void init(String context) {
        if ("catfinder".equals(context))
            catfinder();
        else if ("relative".equals(context))
            relative();
        else if ("traversal".equals(context))
            traversal();
        else
            setCompositionRoot(new Label("Error"));
    }
    
    void catfinder() {
        setWidth("700px");
        setHeight("400px");
        
        // BEGIN-EXAMPLE: layout.overview.catfinder
        // Create the root content layout
        VerticalLayout root = new VerticalLayout();
        root.addStyleName("catfinder");
        root.setMargin(true);
        root.setSizeFull();
        
        // Add the components
        
        // Title bar
        HorizontalLayout titleBar = new HorizontalLayout();
        titleBar.setWidth("100%");
        root.addComponent(titleBar);

        Label title = new Label("The Ultimate Cat Finder");
        title.addStyleName("title");
        titleBar.addComponent(title);
        Label titleComment = new Label("for Vaadin");
        titleComment.addStyleName("titlecomment");
        titleComment.setSizeUndefined();
        titleBar.addComponent(titleComment);
        titleBar.setExpandRatio(title, 1.0f); // Minimize the comment

        // Horizontal layout with selection tree on the left and 
        // a details panel on the right.
        HorizontalLayout horlayout = new HorizontalLayout();
        horlayout.setSizeFull();
        horlayout.setSpacing(true);
        root.addComponent(horlayout);
        root.setExpandRatio(horlayout, 1);

        // Layout for the menu area. Wrap the menu in a Panel to allow
        // scrollbar.
        Panel menuContainer = new Panel("The Possible Places");
        menuContainer.addStyleName("menucontainer");
        menuContainer.addStyleName("light"); // No border
        menuContainer.setWidth(null); // Undefined width
        menuContainer.setHeight("100%");
        horlayout.addComponent(menuContainer);
        
        // A menu tree, fill it later.
        Tree menu = new Tree();
        menu.setSizeUndefined();
        menuContainer.setContent(menu);

        // A panel for the main view area on the right side
        Panel detailspanel = new Panel("Details");
        detailspanel.addStyleName("detailspanel");
        detailspanel.addStyleName("light"); // No borders
        detailspanel.setSizeFull();
        horlayout.addComponent(detailspanel);

        // Have a vertical layout in the Details panel.
        VerticalLayout detailsLayout = new VerticalLayout();
        detailsLayout.setSizeFull();
        detailspanel.setContent(detailsLayout);
        
        // Put some stuff in the Details view.
        VerticalLayout detailsbox = new VerticalLayout();
        detailsbox.setSizeUndefined();
        final Label question = new Label("Where is the cat?");
        question.setSizeUndefined(); 
        detailsbox.addComponent(question);
        final Label location = new Label("I don't know! Tell me!");
        location.setSizeUndefined(); 
        detailsbox.addComponent(location);
        detailsLayout.addComponent(detailsbox);
        detailsLayout.setComponentAlignment(detailsbox, Alignment.MIDDLE_CENTER);

        // Let the details panel take as much space as possible and
        // have the selection tree to be as small as possible
        horlayout.setExpandRatio(detailspanel, 1);
        horlayout.setExpandRatio(menuContainer, 0);
        
        // A footer
        Label footer = new Label("You can not hear the paws");
        footer.addStyleName("footer");
        root.addComponent(footer);

        //////////////////////////////////////////////////////
        // Put in the application data and handle the UI logic
        
        final Object[][] planets = new Object[][] {
            new Object[] {"Mercury"},
            new Object[] {"Venus"},
            new Object[] {"Earth",   "The Moon"},
            new Object[] {"Mars",    "Phobos", "Deimos"},
            new Object[] {"Jupiter", "Io", "Europa", "Ganymedes", "Callisto"},
            new Object[] {"Saturn",  "Titan", "Tethys", "Dione", "Rhea", "Iapetus"},
            new Object[] {"Uranus",  "Miranda", "Ariel", "Umbriel", "Titania", "Oberon"},
            new Object[] {"Neptune", "Triton", "Proteus", "Nereid", "Larissa"}};

        // Add planets as root items in the tree.
        for (int i = 0; i < planets.length; i++) {
            final String planet = (String) (planets[i][0]);
            menu.addItem(planet);

            if (planets[i].length == 1) {
                // The planet has no moons so make it a leaf.
                menu.setChildrenAllowed(planet, false);
            } else {
                // Add children (moons) under the planets.
                for (int j = 1; j < planets[i].length; j++) {
                    final String moon = (String) planets[i][j];

                    // Add the item as a regular item.
                    menu.addItem(moon);

                    // Set it to be a child.
                    menu.setParent(moon, planet);

                    // Make the moons look like leaves.
                    menu.setChildrenAllowed(moon, false);
                }

                // Expand the subtree.
                menu.expandItemsRecursively(planet);
            }
        }
        
        menu.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty() != null &&
                    event.getProperty().getValue() != null) {
                    location.setValue("The cat is in " + event.getProperty().getValue());
                }
            }
        });
        menu.setImmediate(true);

        // END-EXAMPLE: layout.overview.catfinder

        setCompositionRoot(root);
    }
    
    void relative () {
        // BEGIN-EXAMPLE: layout.overview.relative
        VerticalLayout layout = new VerticalLayout();
        
        Panel panel = new Panel("My Panel");
        panel.setWidth(Sizeable.SIZE_UNDEFINED, Unit.POINTS);
        
        layout.addComponent(panel);
        // END-EXAMPLE: layout.overview.relative
        
        setCompositionRoot(layout);
    }
    
    void traversal() {
        // BEGIN-EXAMPLE: layout.overview.traversal
        // Build a user interface
        catfinder();
        
        // Traverse through all components (including the current one,
        // which is a CustomComponent)
        Stack<Component> stack = new Stack<Component>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Component c = stack.pop();
            if (c instanceof ComponentContainer)
                for (Component i: (ComponentContainer) c)
                    stack.add(i);

            // Do something to each component
            if (c.getCaption() != null)
                c.setCaption("["+c.getCaption()+"]");
            if (c instanceof Label)
                ((Label)c).setValue("\""+((String)((Label)c).getValue())+"\"");
        }
        // END-EXAMPLE: layout.overview.traversal
    }
}
