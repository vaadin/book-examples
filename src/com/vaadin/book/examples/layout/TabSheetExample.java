package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class TabSheetExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 3321998143334619838L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("tabchange".equals(context))
            tabchange(layout);
        else if ("preventtabchange".equals(context))
            preventtabchange(layout);
        else if ("tabclose".equals(context))
            tabclose(layout);
        else if ("styling".equals(context))
            styling(layout);
        else
            setCompositionRoot(new Label("Error"));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.basic
        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        // Create the first tab, specify caption when adding
        Layout tab1 = new VerticalLayout(); // Wrap in a layout
        tab1.addComponent(new Image(null,
                new ThemeResource("img/planets/Mercury.jpg")));
        tabsheet.addTab(tab1, "Mercury",
                new ThemeResource("img/planets/Mercury_symbol.png"));

        // This tab gets its caption from the component caption
        Component tab2 = new Image("Venus",
                new ThemeResource("img/planets/Venus.jpg"));
        tabsheet.addTab(tab2).setIcon(
                new ThemeResource("img/planets/Venus_symbol.png"));

        // And so forth the other tabs...
        String[] tabs = {"Earth", "Mars", "Jupiter", "Saturn"};
        for (String caption: tabs) {
            VerticalLayout tab = new VerticalLayout();
            tab.addComponent(new Embedded(null,
                    new ThemeResource("img/planets/"+caption+".jpg")));
            tabsheet.addTab(tab, caption,
                    new ThemeResource("img/planets/"+caption+"_symbol.png"));
        }
        // END-EXAMPLE: layout.tabsheet.basic
        
        
    }
    
    void tabchange(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.tabchange
        TabSheet tabsheet = new TabSheet();

        // Create tab content dynamically when tab is selected
        tabsheet.addSelectedTabChangeListener(
                new TabSheet.SelectedTabChangeListener() {
            private static final long serialVersionUID = -2358653511430014752L;

            public void selectedTabChange(SelectedTabChangeEvent event){
                // Find the tabsheet
                TabSheet tabsheet = event.getTabSheet();
                
                // Find the tab (here we know it's a layout)
                Layout tab = (Layout) tabsheet.getSelectedTab();

                // Get the tab caption from the tab object
                String caption = tabsheet.getTab(tab).getCaption();
                
                // Fill the tab content
                tab.removeAllComponents();
                tab.addComponent(new Image(null,
                    new ThemeResource("img/planets/"+caption+".jpg")));
            }
        });
        
        // Have some tabs
        String[] tabs = {"Mercury", "Venus", "Earth", "Mars"};
        for (String caption: tabs)
            tabsheet.addTab(new VerticalLayout(), caption,
                    new ThemeResource("img/planets/"+caption+"_symbol.png"));
        // END-EXAMPLE: layout.tabsheet.tabchange
        layout.addComponent(tabsheet);
    }

    void preventtabchange(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.preventtabchange
        final TabSheet tabsheet = new TabSheet();

        // Have some tabs
        String[] tabs = {"Mercury", "Venus", "Earth", "Mars"};
        for (String caption: tabs) {
            VerticalLayout tab = new VerticalLayout();
            tab.addComponent(new Embedded(null,
                    new ThemeResource("img/planets/"+caption+".jpg")));
            tabsheet.addTab(tab, caption,
                    new ThemeResource("img/planets/"+caption+"_symbol.png"));
            
            // Here's a condition for the tab that the user must fulfill
            tab.addComponent(new CheckBox("You must select this"));
        }
        
        // Handling tab changes
        tabsheet.addSelectedTabChangeListener(
                new TabSheet.SelectedTabChangeListener() {
            private static final long serialVersionUID = -2358653511430014752L;
            
            Component selected     = tabsheet.getSelectedTab();
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
                    tabsheet.setSelectedTab(selected);
                    // Notification.show("Must check!");
                } else {
                    selected = tabsheet.getSelectedTab();
                    // Notification.show("Changed!");
                }
            }
        });
        // END-EXAMPLE: layout.tabsheet.preventtabchange
        layout.addComponent(tabsheet);
    }

    void tabclose(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.tabclose
        final TabSheet tabsheet = new TabSheet();

        // Have some tabs
        String[] tabs = {"Mercury", "Venus", "Earth", "Mars"};
        for (String caption: tabs) {
            Component tabContent = new Image(null,
                    new ThemeResource("img/planets/"+caption+".jpg"));
            tabsheet.addTab(tabContent, caption,
                    new ThemeResource("img/planets/"+caption+"_symbol.png"));

            // Enable closing the tab
            tabsheet.getTab(tabContent).setClosable(true);
        }
        
        // Handle closing a tab
        tabsheet.setCloseHandler(new CloseHandler() {
            private static final long serialVersionUID = 3370098616683565513L;

            @Override
            public void onTabClose(TabSheet tabsheet, Component tabContent) {
                Tab tab = tabsheet.getTab(tabContent);
                Notification.show("Closing " + tab.getCaption());
                
                // We need to close it explicitly in the handler
                tabsheet.removeTab(tab);
            }
        });
        
        // END-EXAMPLE: layout.tabsheet.tabclose
        layout.addComponent(tabsheet);
    }

    void styling(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.styling
        TabSheet borderlesssheet = new TabSheet();
        borderlesssheet.setCaption("Borderless TabSheet");
        borderlesssheet.addStyleName(Reindeer.TABSHEET_BORDERLESS);
        borderlesssheet.addTab(new Label("Some content"), "Tab 1");
        borderlesssheet.addTab(new Label("Some content"), "Tab 2");
        borderlesssheet.addTab(new Label("Some content"), "Tab 3");
        layout.addComponent(borderlesssheet);

        TabSheet smallsheet = new TabSheet();
        smallsheet.setCaption("Small TabSheet");
        smallsheet.addStyleName(Reindeer.TABSHEET_SMALL);
        smallsheet.addTab(new Label("Some content"), "Tab 1");
        smallsheet.addTab(new Label("Some content"), "Tab 2");
        smallsheet.addTab(new Label("Some content"), "Tab 3");
        layout.addComponent(smallsheet);

        TabSheet minsheet = new TabSheet();
        minsheet.setCaption("Minimal TabSheet");
        minsheet.addStyleName(Reindeer.TABSHEET_MINIMAL);
        minsheet.addTab(new Label("Some content"), "Tab 1");
        minsheet.addTab(new Label("Some content"), "Tab 2");
        minsheet.addTab(new Label("Some content"), "Tab 3");
        layout.addComponent(minsheet);

        TabSheet hoversheet = new TabSheet();
        hoversheet.setCaption("Hover-Closable TabSheet");
        hoversheet.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE);
        hoversheet.addTab(new Label("Some content"), "Tab 1");
        hoversheet.addTab(new Label("Some content"), "Tab 2");
        hoversheet.addTab(new Label("Some content"), "Tab 3");
        for (Component c: hoversheet)
            hoversheet.getTab(c).setClosable(true);
        layout.addComponent(hoversheet);

        TabSheet closheet = new TabSheet();
        closheet.setCaption("Selected-Closable TabSheet");
        closheet.addStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE);
        closheet.addTab(new Label("Some content"), "Tab 1");
        closheet.addTab(new Label("Some content"), "Tab 2");
        closheet.addTab(new Label("Some content"), "Tab 3");
        for (Component c: closheet)
            closheet.getTab(c).setClosable(true);
        layout.addComponent(closheet);
        // END-EXAMPLE: layout.tabsheet.styling
        
        layout.setSpacing(true);
    }
}
