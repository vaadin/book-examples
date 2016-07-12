package com.vaadin.book.examples.component.grid;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("My UI")
@Theme("valo")
public class GridSortBug extends UI {
    
    @WebServlet(value = "/gridsortbug/*",
                asyncSupported = true)
    @VaadinServletConfiguration(
                productionMode = false,
                ui = GridSortBug.class)
    static public class MyProjectServlet extends VaadinServlet {
    }
    
    @Override
    protected void init(VaadinRequest request) {
        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Have a sortable container
        IndexedContainer container = exampleDataSource();

        // Create a grid bound to it
        Grid grid = new Grid(container);
        // grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("500px");
        grid.setHeight("300px");

        // Sort first by city and then by name 
        grid.sort("city");

        content.addComponent(grid);
    }

    public static IndexedContainer exampleDataSource() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("city", String.class, null);
        container.addContainerProperty("year", Integer.class, null);
        
        addContent(container);
        return container;
    }
    
    public static void addContent(IndexedContainer container) {
        String[] firstnames = new String[]{"Isaac", "Ada", "Charles", "Douglas"};
        String[] lastnames  = new String[]{"Newton", "Lovelace", "Darwin", "Adams"};
        String[] cities     = new String[]{"London", "Oxford", "Innsbruck", "Turku"};
        for (int i=0; i<100; i++) {
            Object itemId = container.addItem();
            String name = firstnames[(int) (Math.random()*4)] + " " + lastnames[(int) (Math.random()*4)];
            container.getItem(itemId).getItemProperty("name").setValue(name);
            String city = cities[(int) (Math.random()*4)];
            container.getItem(itemId).getItemProperty("city").setValue(city);
            Integer year = 1800 + (int) (Math.random()*200);
            container.getItem(itemId).getItemProperty("year").setValue(year);
        }
    }
}
