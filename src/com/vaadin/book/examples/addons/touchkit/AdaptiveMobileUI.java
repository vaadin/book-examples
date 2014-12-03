package com.vaadin.book.examples.addons.touchkit;

// BEGIN-EXAMPLE: mobile.overview.phone
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mobiletheme")
public class AdaptiveMobileUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;

    public boolean isSmallScreenDevice() {
        return Page.getCurrent().getWebBrowser().getScreenWidth() < 600;
    }

    @Override
    protected void init(VaadinRequest request) {
        // Set the window or tab title
        getPage().setTitle("Hello Phone!");
        
        // Create the content root layout for the UI
        TabBarView mainView = new TabBarView();
        setContent(mainView);
        
        // Create a view - usually a regular class
        class MyView extends VerticalLayout {
            private static final long serialVersionUID = 3750679255269899661L;

            Table table = new Table("Planets", planetData());

            public MyView() {
                addComponent(new Label("This is a view"));
                table.setWidth("100%");
                table.setPageLength(table.size());
                addComponent(table);
                addComponent(new Button("Go"));
                setSpacing(true);
            }
        }
        mainView.addTab(new MyView(), "Planets");

        // Add some more sub-views
        mainView.addTab(new Label("Dummy"), "Map");
        mainView.addTab(new Label("Dummy"), "Settings");
    }
    
    @SuppressWarnings("unchecked")
    Container planetData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        
        String names[] = {"Mercury", "Venus", "Earth", "Mars",
                          "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String name: names)
            container.getContainerProperty(container.addItem(), "name").setValue(name);
        return container;
    }
}
// END-EXAMPLE: mobile.overview.phone
