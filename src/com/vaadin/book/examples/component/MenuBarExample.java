package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class MenuBarExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("keep".equals(context))
            keep(layout);
        else if ("navigator".equals(context))
            navigator(layout);
        else if ("bigger".equals(context))
            bigger(layout);
        else
            layout.addComponent(new Label("Context not found"));
        setCompositionRoot(layout);
    }

    public static String basicDescription =
        "<h1>Basic use of the <b>MenuBar</b> component</h1> "+
        "<p>Selection is handled by implementing the <b>Command</b> interface.</p>";
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.menubar.basic
        MenuBar barmenu = new MenuBar();
        layout.addComponent(barmenu);
        
        // A feedback component
        final Label selection = new Label("");
        layout.addComponent(selection);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = new MenuBar.Command() {
            private static final long serialVersionUID = 4483012525105015694L;

            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Ordered a " +
                        selectedItem.getText() +
                        " from menu.");
            }  
        };
        
        // A top-level menu item that opens a submenu
        MenuItem drinks = barmenu.addItem("Beverages", null, null);

        // Submenu item with a sub-submenu
        MenuItem hots = drinks.addItem("Hot", null, null);
        hots.addItem("Tea",
            new ThemeResource("icons/tea-16px.png"),    mycommand);
        hots.addItem("Coffee",
            new ThemeResource("icons/coffee-16px.png"), mycommand);

        // Another submenu item with a sub-submenu
        MenuItem colds = drinks.addItem("Cold", null, null);
        colds.addItem("Milk",      null, mycommand);
        colds.addItem("Weissbier", null, mycommand);
        colds.setDescription("Ich mag es");

        // A sub-menu item after a separator
        drinks.addSeparator();
        drinks.addItem("Quit Drinking", null, null);
        
        // Another top-level item
        MenuItem snacks = barmenu.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst",  null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);
        
        // Yet another top-level item
        MenuItem servs = barmenu.addItem("Services", null, null);
        servs.addItem("Car Service", null, mycommand);    
        // END-EXAMPLE: component.menubar.basic
        
        // A bit additional space
        Label spacer = new Label("&nbsp;", ContentMode.HTML);
        spacer.setHeight("100px");
        layout.addComponent(spacer);
    }

    void keep(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.menubar.keep
        MenuBar barmenu = new MenuBar();
        barmenu.addStyleName("mybarmenu");
        layout.addComponent(barmenu);
        
        // A feedback component
        final Label selection = new Label("");
        layout.addComponent(selection);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = new MenuBar.Command() {
            private static final long serialVersionUID = 7920906555442357534L;

            MenuItem previous = null;

            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Ordered a " +
                        selectedItem.getText() +
                        " from menu.");

                if (previous != null)
                    previous.setStyleName(null);
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
            }  
        };
        
        // Put some items in the menu
        barmenu.addItem("Beverages", null, mycommand);
        barmenu.addItem("Snacks", null, mycommand);
        barmenu.addItem("Services", null, mycommand);
        // END-EXAMPLE: component.menubar.keep
    }
    
    // EXAMPLE-REF: component.menubar.navigator com.vaadin.book.examples.component.MenuNavigatorUI component.menubar.navigator
    void navigator(final VerticalLayout layout) {
        Button button = new Button("Click to Open");
        layout.addComponent(button);
        
        BrowserWindowOpener opener = new BrowserWindowOpener(MenuNavigatorUI.class);
        opener.setFeatures("width=640,height=480,resizable");
        opener.extend(button);
    }

    void create3LevelMenu(MenuBar barmenu, MenuBar.Command mycommand) {
        // Put some items in the menu hierarchically
        MenuBar.MenuItem beverages =
            barmenu.addItem("Beverages", null, null);
        MenuBar.MenuItem hot_beverages =
            beverages.addItem("Hot", null, null);
        hot_beverages.addItem("Tea", null, mycommand);
        hot_beverages.addItem("Coffee", null, mycommand);
        MenuBar.MenuItem cold_beverages =
            beverages.addItem("Cold", null, null);
        cold_beverages.addItem("Milk", null, mycommand);
        cold_beverages.addItem("Weissbier", null, mycommand);
        
        // Another top-level item
        MenuBar.MenuItem snacks =
            barmenu.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst", null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);
        
        // Yet another top-level item
        MenuBar.MenuItem services =
            barmenu.addItem("Services", null, null);
        services.addItem("Car Service", null, mycommand);        
        // END-EXAMPLE: component.menubar.keep
    }

    void bigger(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.menubar.bigger
        // The menu won't fit in this space
        Panel menuPanel = new Panel("Tight Panel with a Menu");
        menuPanel.setWidth("400px");
        VerticalLayout contentLayout = new VerticalLayout();
        menuPanel.setContent(contentLayout);
        
        MenuBar barmenu = new MenuBar();
        barmenu.setWidth("100%");
        contentLayout.addComponent(barmenu);

        // Add some menus with sub-menus
        String planets[][]= {{"Mercury"},
                {"Venus"},
                {"Earth",   "The Moon"},
                {"Mars",    "Phobos", "Deimos"},
                {"Jupiter", "Io", "Europa", "Ganymedes", "Callisto"},
                {"Saturn",  "Titan", "Tethys", "Dione", "Rhea", "Iapetus"},
                {"Uranus",  "Miranda", "Ariel", "Umbriel", "Titania", "Oberon"},
                {"Neptune", "Triton", "Proteus", "Nereid", "Larissa"}};
        for (String[] planetmoons: planets) {
            MenuItem planet = barmenu.addItem(planetmoons[0], null);
            for (int j=1; j<planetmoons.length; j++)
                planet.addItem(planetmoons[j], null);
        }
        // END-EXAMPLE: component.menubar.bigger
        layout.addComponent(menuPanel);
    }
}
