package com.vaadin.book.examples.component.grid;

import java.io.Serializable;
import java.util.Collection;

import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterCell;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;


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

    public void array(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.array
        // BOOK: components.grid
        // Create the Grid
        Grid grid = new Grid();
        grid.setSelectionMode(SelectionMode.NONE);

        // Define the columns
        grid.addColumn("name", String.class);
        grid.addColumn("born", Integer.class);
        
        // Have some data
        Object[][] people = {{"Nicolaus Copernicus", 1543},
                             {"Galileo Galilei", 1564},
                             {"Johannes Kepler", 1571}};
        for (Object[] person: people)
            grid.addRow(person);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.array
    }

    // BEGIN-EXAMPLE: component.grid.collection
    // BOOK: components.grid
    // A data model
    public class Person implements Serializable {
        private static final long serialVersionUID = 5643002875138191294L;

        private String name;
        private int    born;
        
        public Person(String name, int born) {
            this.name = name;
            this.born = born;
        }
        
        public String getName() {
            return name;
        }
        
        public int getBorn() {
            return born;
        }
    }

    public void collection(VerticalLayout layout) {
        // Have some data
        Collection<Person> people = Lists.newArrayList(
            new Person("Nicolaus Copernicus", 1543),
            new Person("Galileo Galilei", 1564),
            new Person("Johannes Kepler", 1571));

        // Have a container of some type to contain the data
        BeanItemContainer<Person> container =
            new BeanItemContainer<Person>(Person.class, people);

        // Create a grid bound to the container
        Grid grid = new Grid(container);
        grid.setColumnOrder("name", "born");

        // Handle selection in single-selection mode
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addSelectionListener(e -> { // Java 8
            // Get the item of the selected row
            BeanItem<Person> item =
                container.getItem(grid.getSelectedRow());

            // Use the item somehow
            Notification.show("Selected " +
                              item.getBean().getName());
        });

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.collection
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
        grid.setHeightByRows(7);
        grid.setHeightMode(HeightMode.ROW);

        // Single-selection mode (default)
        grid.setSelectionMode(SelectionMode.MULTI);

        HeaderRow mainHeader = grid.getDefaultHeaderRow();
        mainHeader.getCell("firstname").setText("First Name");
        mainHeader.getCell("lastname").setText("Last Name");
        mainHeader.getCell("born").setText("Born In");
        mainHeader.getCell("died").setText("Died In");
        mainHeader.getCell("lived").setText("Lived (Years)");

        // Group headers by joining the cells
        HeaderRow joinheader = grid.prependHeaderRow();
        HeaderCell headerCell1 = joinheader.join(
            joinheader.getCell("firstname"),
            joinheader.getCell("lastname"));
        headerCell1.setText("Names");
        HeaderCell headerCell2 = joinheader.join(
            joinheader.getCell("born"),
            joinheader.getCell("died"),
            joinheader.getCell("lived"));
        headerCell2.setText("Years");
        
        // Add a footer row
        FooterRow footer = grid.appendFooterRow();
        
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
        }
        
        // Enable editing
        grid.setEditorEnabled(true);
        grid.setEditorFieldGroup(new FieldGroup());
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.features
    }
}
