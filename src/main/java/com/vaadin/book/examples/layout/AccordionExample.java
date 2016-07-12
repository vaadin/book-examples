package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class AccordionExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 3321998143334619838L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("tabchange".equals(context))
            tabchange(layout);
        else if ("preventtabchange".equals(context))
            preventtabchange(layout);
        else if ("styling".equals(context))
            styling(layout);
        else
            setCompositionRoot(new Label("Error"));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.accordion.basic
        // Create the accordion
        Accordion accordion = new Accordion();

        // Create the first tab, specify caption when adding
        Layout tab1 = new VerticalLayout(); // Wrap in a layout
        tab1.addComponent(new Image(null, // No component caption
            new ThemeResource("img/planets/Mercury.jpg")));
        accordion.addTab(tab1, "Mercury",
            new ThemeResource("img/planets/Mercury_symbol.png"));

        // This tab gets its caption from the component caption
        Component tab2 = new Image("Venus",
            new ThemeResource("img/planets/Venus.jpg"));
        accordion.addTab(tab2).setIcon(
            new ThemeResource("img/planets/Venus_symbol.png"));

        // And so forth the other tabs...
        String[] tabs = {"Earth", "Mars", "Jupiter", "Saturn"};
        for (String caption: tabs) {
            String basename = "img/planets/" + caption;
            VerticalLayout tab = new VerticalLayout();
            tab.addComponent(new Embedded(null,
                    new ThemeResource(basename + ".jpg")));
            accordion.addTab(tab, caption,
                    new ThemeResource(basename + "_symbol.png"));
        }
        // END-EXAMPLE: layout.accordion.basic
        layout.addComponent(accordion);
    }
    
    void tabchange(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.accordion.tabchange
        Accordion accordion = new Accordion();

        // Create tab content dynamically when tab is selected
        accordion.addSelectedTabChangeListener(
                new Accordion.SelectedTabChangeListener() {
            private static final long serialVersionUID = -2358653511430014752L;

            public void selectedTabChange(SelectedTabChangeEvent event){
                // Find the accordion (as a TabSheet)
                TabSheet accordion = event.getTabSheet();
                
                // Find the tab (here we know it's a layout)
                Layout tab = (Layout) accordion.getSelectedTab();

                // Get the tab caption from the tab object
                String caption = accordion.getTab(tab).getCaption();
                
                // Fill the tab content
                tab.removeAllComponents();
                tab.addComponent(new Image(null,
                    new ThemeResource("img/planets/"+caption+".jpg")));
            }
        });
        
        // Have some tabs
        String[] tabs = {"Mercury", "Venus", "Earth", "Mars"};
        for (String caption: tabs)
            accordion.addTab(new VerticalLayout(), caption,
                    new ThemeResource("img/planets/"+caption+"_symbol.png"));
        // END-EXAMPLE: layout.accordion.tabchange
        layout.addComponent(accordion);
    }

    void preventtabchange(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.accordion.preventtabchange
        final Accordion accordion = new Accordion();

        // Have some tabs
        String[] tabs = {"Mercury", "Venus", "Earth", "Mars"};
        for (String caption: tabs) {
            VerticalLayout tab = new VerticalLayout();
            tab.addComponent(new Embedded(null,
                    new ThemeResource("img/planets/"+caption+".jpg")));
            accordion.addTab(tab, caption,
                    new ThemeResource("img/planets/"+caption+"_symbol.png"));
            
            // Here's a condition for the tab that the user must fulfill
            tab.addComponent(new CheckBox("You must select this"));
        }
        
        // Handling tab changes
        accordion.addSelectedTabChangeListener(
                new Accordion.SelectedTabChangeListener() {
            private static final long serialVersionUID = -2358653511430014752L;
            
            Component selected     = accordion.getSelectedTab();
            boolean   preventEvent = false;

            public void selectedTabChange(SelectedTabChangeEvent event) {
                if (preventEvent) {
                    preventEvent = false;
                    return;
                }
                
                // Check that the tab from which we are changing allows it
                VerticalLayout tab = (VerticalLayout) selected;
                CheckBox mayChange = (CheckBox) tab.getComponent(1);
                if (!mayChange.getValue().booleanValue()) {
                    // Revert the tab change
                    preventEvent = true; // Prevent secondary change event
                    accordion.setSelectedTab(selected);
                    // Notification.show("Must check!");
                } else {
                    selected = accordion.getSelectedTab();
                    // Notification.show("Changed!");
                }
            }
        });
        // END-EXAMPLE: layout.accordion.preventtabchange
        layout.addComponent(accordion);
    }

    void styling(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.accordion.styling
        Accordion accordion = new Accordion();
        accordion.addStyleName(Reindeer.TABSHEET_BORDERLESS);
        layout.addComponent(accordion);
        
        accordion.addComponent(new Label("Here is some content"));
        // END-EXAMPLE: layout.accordion.styling
    }
}
