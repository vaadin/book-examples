package com.vaadin.book.examples.component.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;


public class GridDataBindingExample extends CustomComponent implements AnyBookExampleBundle {
    
    public void array(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.databinding.array
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
        // END-EXAMPLE: component.grid.databinding.array
    }

    // BEGIN-EXAMPLE: component.grid.databinding.collection
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
        Collection<Person> people = new ArrayList<>();
        people.add(new Person("Nicolaus Copernicus", 1543));
        people.add(new Person("Galileo Galilei", 1564));
        people.add(new Person("Johannes Kepler", 1571));

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
        // END-EXAMPLE: component.grid.databinding.collection
    }

    public static final String hierarchicalDescription =
            "<h1>Hierarchical Data in Grid</h1>"
            + "<p>Grid does not currently support hierarchical data, so there's nothing to see here.</p>";

    public void hierarchical(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.databinding.hierarchical
        // BOOK: components.grid
        HierarchicalContainer container = TreeExample.createTreeContent();
        
        // Create the Grid
        Grid grid = new Grid(container);
        grid.setSelectionMode(SelectionMode.NONE);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.databinding.hierarchical
    }
}
