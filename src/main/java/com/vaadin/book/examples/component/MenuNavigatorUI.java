package com.vaadin.book.examples.component;

import java.util.HashMap;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: component.menubar.navigator
@Theme("book-examples")
public class MenuNavigatorUI extends UI {
    private static final long serialVersionUID = -9158747860805610183L;

    /** A menu bar that both controls and observes navigation */
    protected class NavigableMenuBar extends MenuBar implements ViewChangeListener {
        private static final long serialVersionUID = 7178106622402490205L;

        private MenuItem previous = null; // Previously selected item
        private MenuItem current  = null; // Currently selected item

        // Map view IDs to corresponding menu items
        HashMap<String,MenuItem> menuItems = new HashMap<String,MenuItem>();
        
        private Navigator navigator = null;
        
        public NavigableMenuBar(Navigator navigator) {
            this.navigator = navigator;
        }
        
        /** Navigate to a view by menu selection */ 
        MenuBar.Command mycommand = new MenuBar.Command() {
            private static final long serialVersionUID = 7920906555442357534L;

            public void menuSelected(MenuItem selectedItem) {
                String viewName = selectItem(selectedItem);
                navigator.navigateTo(viewName);
            }
        };
        
        public void addView(String viewName, String caption, Resource icon) {
            menuItems.put(viewName, addItem(caption, icon, mycommand));
        }

        /** Select a menu item by its view ID **/
        protected boolean selectView(String viewName) {
            // Check that the menu item exists
            if (!menuItems.containsKey(viewName))
                return false;

            if (previous != null)
                previous.setStyleName(null);
            if (current == null)
                current = menuItems.get(viewName);
            current.setStyleName("highlight");
            previous = current;
            
            return true;
        }

        /** Selects a new menu item */
        public String selectItem(MenuItem selectedItem) {
            current = selectedItem;
        
            // Do reverse lookup for the view ID
            for (String key: menuItems.keySet())
                if (menuItems.get(key) == selectedItem)
                    return key;
            
            return null;
        }

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return selectView(event.getViewName());
        }
            
        @Override
        public void afterViewChange(ViewChangeEvent event) {}
     };

    // We have just a single view class in this example
    public class MyView extends VerticalLayout implements View {
        private static final long serialVersionUID = -4486592525912050737L;

        public MyView(String something) {
            addComponent(new Label("This is " + something));
            setSizeFull();
        }
        
        @Override
        public void enter(ViewChangeEvent event) {
            // The view could do something here as well
        }
    }
    
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);
        
        // Have a view display controlled by a navigator
        Panel viewDisplay = new Panel();
        viewDisplay.setSizeFull();

        // Create a navigator with some pre-created views
        Navigator navigator = new Navigator(this, viewDisplay);
        navigator.addView("beverages", new MyView("Beverages View"));
        navigator.addView("snacks",    new MyView("Snacks View"));
        navigator.addView("services",  new MyView("Services View"));
        navigator.navigateTo("beverages");

        // Control and observe navigation by a main menu
        NavigableMenuBar menu = new NavigableMenuBar(navigator);
        menu.addStyleName("mybarmenu");
        layout.addComponent(menu);
        
        // The view display goes below the menu bar
        layout.addComponent(viewDisplay);
        layout.setExpandRatio(viewDisplay, 1.0f);

        // Update the menu selection when the view changes
        navigator.addViewChangeListener(menu);
        
        // Add items in the menu and associate them with a view ID
        menu.addView("beverages", "Beverages", null);
        menu.addView("snacks",    "Snacks", null);
        menu.addView("services",  "Services", null);
    }
}
// END-EXAMPLE: component.menubar.navigator