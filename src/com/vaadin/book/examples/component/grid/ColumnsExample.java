package com.vaadin.book.examples.component.grid;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderer.ProgressBarRenderer;


public class ColumnsExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 19636533335703614L;

    public void summary(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.columns.summary
        // BOOK: components.grid
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("400px");
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("firstname", String.class);
        grid.addColumn("lastname", String.class);
        grid.addColumn("born", Integer.class);
        grid.addColumn("died", Integer.class);
        grid.addColumn("birthplace", String.class);
        grid.addColumn("rating", Double.class);
        
        // Freeze the first two columns (firstname, lastname)
        grid.setFrozenColumnCount(2);

        // Set caption explicitly
        Grid.Column bornColumn = grid.getColumn("born");
        bornColumn.setHeaderCaption("Born");

        Grid.Column diedColumn = grid.getColumn("died");
        diedColumn.setWidth(100); // Pixels

        // Use expand ratio for a column
        Grid.Column ratingColumn = grid.getColumn("rating");
        ratingColumn.setExpandRatio(1);
        
        grid.setColumnOrder("firstname", "lastname", "born",
                            "birthplace", "died");

        // Add some data rows
        grid.addRow("Nicolaus", "Copernicus", 1473, "Torun", 1543, 0.2);
        grid.addRow("Galileo", "Galilei", 1564, "Pisa", 1642, 0.42);
        grid.addRow("Johannes", "Kepler", 1571, "Weil der Stadt", 1630, 1.0);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.columns.summary
    }

    public void expandratio(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.columns.expandratio
        // BOOK: components.grid
        // Create a grid
        Grid grid = new Grid();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("600px");
        grid.setStyleName("expandratiogrid");

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("score", Double.class)
            .setRenderer(new ProgressBarRenderer());
        grid.addColumn("rating", Double.class)
            .setRenderer(new ProgressBarRenderer());
        
        // This column expands just a bit
        Grid.Column bornColumn = grid.getColumn("score");
        bornColumn.setExpandRatio(1);

        // This column expands 5 times more
        Grid.Column ratingColumn = grid.getColumn("rating");
        ratingColumn.setMinimumWidth(100);
        ratingColumn.setMaximumWidth(400);
        ratingColumn.setExpandRatio(5);
        
        grid.setCellStyleGenerator(cellReference -> // Java 8
            "description".equals(cellReference.getPropertyId())?
                "wrappingcolumn" : null);

        // Add some data rows
        grid.addRow("Nicolaus Copernicus", 0.2, 0.1);
        grid.addRow("Galileo Galilei",     0.9, 0.42);
        grid.addRow("Johannes Kepler",     0.6, 1.0);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.columns.expandratio
    }

    // grid.addRow("Nicolaus Copernicus", "Copernicus was an astronomer who formulated Sun-centric model of the universe", 0.1);
    // grid.addRow("Galileo Galilei", "Galileo built the first astronomical telescope and proved Copernican model of the solar system", 0.42);
    // grid.addRow("Johannes Kepler", "Kepler formulated laws for the motions of the planetary bodies", 1.0);
}
