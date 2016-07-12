package com.vaadin.book.examples.component.grid;

import com.vaadin.book.examples.component.table.TableExample;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

public class GridStyleExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    @SuppressWarnings("unchecked")
    public void rowstyle(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.stylegeneration.rowstyle
        // Have a container
        IndexedContainer container = exampleDataSource();
        container.addContainerProperty("alive", Boolean.class, null);
        for (Object itemId: container.getItemIds())
            container.getContainerProperty(itemId, "alive")
                     .setValue(Math.random() > 0.5);

        // Create a grid bound to it
        Grid grid = new Grid(container);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("500px");
        grid.setHeight("300px");

        grid.setRowStyleGenerator(rowRef -> {// Java 8
            if (! ((Boolean) rowRef.getItem()
                                   .getItemProperty("alive")
                                   .getValue()).booleanValue())
                return "grayed";
            else
                return null;
        });

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.stylegeneration.rowstyle
    }

    public void cellstyle(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.stylegeneration.cellstyle
        // Create a grid bound to it
        Grid grid = new Grid(exampleDataSource());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("500px");
        grid.setHeight("300px");

        grid.setCellStyleGenerator(cellRef -> {// Java 8
            if ("year".equals(cellRef.getPropertyId())) {
                if (((Integer) cellRef.getValue()) > 1900)
                    return "rightalign supercell";
                else
                    return "rightalign";
            } else
                return null;
        });

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.stylegeneration.cellstyle
    }

    public static IndexedContainer exampleDataSource() {
        return TableExample.generateContent();
    }
}
