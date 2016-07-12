package com.vaadin.book.examples.datamodel;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class IndexedContainerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.container.indexedcontainer.basic
        // Create the container
        IndexedContainer container = new IndexedContainer();
        
        // Define the properties (columns)
        container.addContainerProperty("name", String.class, "noname");
        container.addContainerProperty("volume", Double.class, -1.0d);
        container.addContainerProperty("id", Object.class, -1);
        
        // Create an item
        Object itemId = container.addItem();
        
        // Get the item
        Item item = container.getItem(itemId);

        // Set a property value in the item
        Property<String> nameProperty =
                item.getItemProperty("name");
        nameProperty.setValue("box");
        
        // Directly accessing the property object
        container.getContainerProperty(itemId, "volume").setValue(5.0);

        // Create an item with a given item ID
        Item newItem = container.addItem("agivenid");
        newItem.getItemProperty("name").setValue("barrel");
        newItem.getItemProperty("volume").setValue(119.2);
        
        // Create another item but leave it empty (except for the id)
        container.addItem();
        
        // Add a few more items
        Object content[][] = {{"jar", 2.0}, {"bottle", 0.75},
                              {"can", 1.5}};
        for (Object[] row: content) {
            Item rowItem = container.getItem(container.addItem());
            rowItem.getItemProperty("name").setValue(row[0]);
            rowItem.getItemProperty("volume").setValue(row[1]);
        }

        // Copy the item IDs to a third property
        for (Object id: container.getItemIds())
            container.getContainerProperty(id, "id").setValue(id);
        
        // Bind a table to it
        Table table = new Table("Containers of All Sorts", container);
        table.setPageLength(container.size());
        layout.addComponent(table);
        
        // Preselect the third item
        Object thirdItemId = container.getIdByIndex(2);
        table.select(thirdItemId);
        table.setSelectable(true);
        // END-EXAMPLE: datamodel.container.indexedcontainer.basic
    }
}
