package com.vaadin.book.examples.component.grid;

import java.util.Arrays;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.sort.Sort;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterCell;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;


public class GridExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.basic
        // BOOK: components.grid
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("born", Integer.class);

        // Add some data rows
        grid.addRow("Nicolaus Copernicus", 1543);
        grid.addRow("Galileo Galilei", 1564);
        grid.addRow("Johannes Kepler", 1571);

        // Fit the number of rows (no scrolling)
        // TODO Doesn't work properly
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(7);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.basic
    }


    public void features(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.features
        // Define some columns
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("firstname", String.class, null);
        container.addContainerProperty("lastname", String.class, null);
        container.addContainerProperty("born", Integer.class, null);
        container.addContainerProperty("died", Integer.class, null);
        Object[] pids = container.getContainerPropertyIds().toArray();
        
        // Add some data rows
        Object[][] data = new Object[][] {
                {"Nicolaus", "Copernicus", 1473, 1543},
                {"Galileo", "Galilei", 1564, 1642},
                {"Johannes", "Kepler", 1571, 1630},
                {"Tycho", "Brahe", 1546, 1601},
                {"Giordano", "Bruno", 1548, 1600},
                {"Christiaan", "Huygens", 1629, 1695}};
        for (Object[] row: data) {
            Object itemId = container.addItem();
            for (int i=0; i<pids.length; i++)
                container.getContainerProperty(itemId, pids[i]).setValue(row[i]);
        }
        
        // Add some generated properties
        GeneratedPropertyContainer gpcontainer =
            new GeneratedPropertyContainer(container);
        gpcontainer.addGeneratedProperty("lived",
            new PropertyValueGenerator<Integer>() {
            private static final long serialVersionUID = -1636752705984592807L;

            @Override
            public Integer getValue(Item item, Object itemId,
                                    Object propertyId) {
                int born = (Integer)
                           item.getItemProperty("born").getValue();
                int died = (Integer)
                           item.getItemProperty("died").getValue();
                return Integer.valueOf(died - born);
            }

            @Override
            public Class<Integer> getType() {
                return Integer.class;
            }
        });
        
        Grid grid = new Grid(gpcontainer);
        grid.setCaption("My Featureful Grid");
        grid.setWidth("600px");
        grid.setHeightByRows(5);
        grid.setHeightMode(HeightMode.ROW);

        // Single-selection mode (default)
        grid.setSelectionMode(SelectionMode.MULTI);

        // Format years differently
        grid.getColumn("born").setRenderer(
            new NumberRenderer("%d AD"));
        grid.getColumn("died").setRenderer(
            new NumberRenderer("%d AD"));
        grid.getColumn("lived").setRenderer(
            new NumberRenderer("%d years"));

        HeaderRow mainHeader = grid.getDefaultHeaderRow();
        mainHeader.getCell("firstname").setText("First Name");
        mainHeader.getCell("lastname").setText("Last Name");
        mainHeader.getCell("born").setText("Born In");
        mainHeader.getCell("died").setText("Died In");
        mainHeader.getCell("lived").setText("Lived For");

        // Group headers by joining the cells
        HeaderRow groupingHeader = grid.prependHeaderRow();
        HeaderCell headerCell1 = groupingHeader.join(
            groupingHeader.getCell("firstname"),
            groupingHeader.getCell("lastname"));
        headerCell1.setHtml("Names");
        HeaderCell headerCell2 = groupingHeader.join(
            groupingHeader.getCell("born"),
            groupingHeader.getCell("died"),
            groupingHeader.getCell("lived"));
        headerCell2.setHtml("Years");
        
        // Set styles for the headers
        mainHeader.setStyleName("boldheader");
        groupingHeader.setStyleName("boldheader");

        // Create a header row to hold column filters
        HeaderRow filterRow = grid.appendHeaderRow();
        
        // Set up a filter for all columns
        for (Object pid: grid.getContainerDataSource()
                             .getContainerPropertyIds()) {
            HeaderCell cell = filterRow.getCell(pid);
            
            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);
            filterField.setInputPrompt("Filter");
            
            // Set filter field width based on data type
            if (grid.getContainerDataSource()
                    .getType(pid).equals(Integer.class)) {
                filterField.setColumns(5);
                cell.setStyleName("rightalign");
            } else
                filterField.setColumns(8);
                
            
            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                container.removeContainerFilters(pid);
                
                // (Re)create the filter if necessary
                if (! change.getText().isEmpty())
                    container.addContainerFilter(
                        new SimpleStringFilter(pid,
                            change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }

        // Freeze two first columns
        grid.setFrozenColumnCount(2);

        // Add a footer row
        FooterRow footer = grid.appendFooterRow();
        
        grid.setCellStyleGenerator(generator -> // Java 8
            Arrays.asList("born", "died", "lived")
                  .contains(generator.getPropertyId())?
                "rightalign" : null);

        // Calculate averages of the numeric columns
        for (String numericColumn: new String[] {"born", "died", "lived"}) {
            double avg = 0.0;
            for (Object itemId: gpcontainer.getItemIds())
                avg += ((Number) gpcontainer.getContainerProperty(
                    itemId, numericColumn).getValue()).doubleValue();
            avg /= container.size();
            
            // Set the value in the footer
            FooterCell footerCellBorn = footer.getCell(numericColumn);
            footerCellBorn.setText(String.format("%1$.2f", avg));
            footerCellBorn.setStyleName("rightalign");
        }

        // Enable editing
        grid.setEditorEnabled(true);
        grid.setEditorFieldGroup(new FieldGroup());
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.features
    }
    
    public void multi(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.selection.multi
        // Create a grid bound to a container
        Grid grid = new Grid(exampleDataSource());
        grid.setWidth("500px");
        grid.setHeight("300px");

        // Enable multi-selection mode
        grid.setSelectionMode(SelectionMode.MULTI);
        
        // Allow deleting the selected items
        Button deleteSelected = new Button("Delete Selected", e -> {
            // Delete all selected data items
            for (Object itemId: grid.getSelectedRows())
                grid.getContainerDataSource().removeItem(itemId);
            
            // Disable after deleting
            e.getButton().setEnabled(false);
        });
        deleteSelected.setEnabled(false); // Enable later

        // Handle selection changes
        grid.addSelectionListener(selection -> { // Java 8
            Notification.show(selection.getAdded().size() +
                              " items added, " +
                              selection.getRemoved().size() +
                              " removed.");

            // Allow deleting selected only if there's any selected
            deleteSelected.setEnabled(grid.getSelectedRows().size() > 0);
        });

        layout.addComponents(grid, deleteSelected);
        // END-EXAMPLE: component.grid.selection.multi
    }
    
    public void filtering(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.filtering
        // Have a filterable container
        IndexedContainer container = exampleDataSource();

        // Create a grid bound to it
        Grid grid = new Grid(container);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("500px");
        grid.setHeight("300px");

        // Create a header row to hold column filters
        HeaderRow filterRow = grid.appendHeaderRow();

        // Set up a filter for all columns
        for (Object pid: grid.getContainerDataSource()
                             .getContainerPropertyIds()) {
            HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setColumns(8);
            filterField.setInputPrompt("Filter");
            filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);

            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                container.removeContainerFilters(pid);

                // (Re)create the filter if necessary
                if (! change.getText().isEmpty())
                    container.addContainerFilter(
                        new SimpleStringFilter(pid,
                            change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.filtering
    }

    public void sort(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.sorting.sort
        // Have a sortable container
        IndexedContainer container = exampleDataSource();

        // Create a grid bound to it
        Grid grid = new Grid(container);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("350px");
        grid.setHeight("300px");

        // Sort first by city and then by name 
        grid.sort(Sort.by("city").then("name"));

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.sorting.sort
    }

    public void sortdirection(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.sorting.sortdirection
        // Have a sortable container
        IndexedContainer container = exampleDataSource();

        // Create a grid bound to it
        Grid grid = new Grid(container);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("500px");
        grid.setHeight("300px");

        // Sort first by city and then by name 
        grid.sort(Sort.by("city", SortDirection.ASCENDING)
                      .then("name", SortDirection.DESCENDING));

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.sorting.sortdirection
    }

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
        // Have a sortable container
        IndexedContainer container = exampleDataSource();

        // Create a grid bound to it
        Grid grid = new Grid(container);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setWidth("500px");
        grid.setHeight("300px");

        grid.setCellStyleGenerator(cellRef -> // Java 8
            "year".equals(cellRef.getPropertyId())?
                "rightalign" : null);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.stylegeneration.cellstyle
    }
    
    public void editing(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing
        Grid grid = new Grid(exampleDataSource());
        grid.setWidth("600px");
        grid.setHeight("400px");

        // Single-selection mode (default)
        grid.setSelectionMode(SelectionMode.NONE);

        // Enable editing
        grid.setEditorEnabled(true);
        grid.setEditorFieldGroup(new FieldGroup());
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing
    }

    public static IndexedContainer exampleDataSource() {
        return TableExample.generateContent();
    }
}
