package com.vaadin.book.examples.component.grid;

import java.util.HashMap;
import java.util.HashSet;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;


public class HierarchicalGridExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 19636533335703614L;

    public void hierarchical(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.tricks.hierarchical
        // BOOK: components.grid
        /**
         * Hierarchical Grid
         * 
         * <p>The container data source must be Indexed, Hierarchical,
         * and Filterable.</p>
         *
         * <p>TODO - Adding new items in the container or modifying their
         * parenthoods is currently not supported. The container also must
         * be writable, and preferably an in-memory container, because
         * of how refreshing is triggered.</p>
         * 
         * @author magi
         */
        class HierarchicalGrid extends Grid {
            private static final long serialVersionUID = -4744320951722696900L;

            // We need to store unfiltered parenthoods outside the container,
            // as we can't access these states out otherwise
            protected HashMap<Object,Object> unfilteredParents =
                  new HashMap<Object,Object>();

            // Which items are expanded
            protected HashSet<Object> expanded = new HashSet<Object>();
            
            protected Object hierarchicalColumnId;
            
            public HierarchicalGrid() {
                addStyleName("hierarchicalgrid");
                
                // Tree representation is visualized with cell styles
                setCellStyleGenerator(this::generateCellStyle);

                // Handle expand/collapse by clicking on the items
                addItemClickListener(this::handleItemClicks);
            }
            
            public void expandAll() {
                Container.Hierarchical container =
                    (Container.Hierarchical) getContainerDataSource();
                
                for (Object itemId: container.getItemIds())
                    if (container.areChildrenAllowed(itemId))
                        expand(itemId);
            }
            
            public void expand(Object itemId) {
                expanded.add(itemId);
            }
            
            @Override
            public void setContainerDataSource(Indexed container) {
                if (! (container instanceof Container.Hierarchical))
                    throw new IllegalArgumentException(
                        "Container bound to HierarchicalGrid must "
                        + "implement Container.Hierarhical");
                if (! (container instanceof Container.Filterable))
                    throw new IllegalArgumentException(
                        "Container bound to HierarchicalGrid must "
                        + "implement Container.Filterable");
                
                super.setContainerDataSource(container);
                
                // The first column will be hierarchical
                hierarchicalColumnId =
                    container.getContainerPropertyIds().iterator().next();

                // Set up custom filter
                HierarchicalFilter filter = new HierarchicalFilter();
                ((Container.Filterable) getContainerDataSource())
                    .addContainerFilter(filter);

                copyParenthoods((HierarchicalContainer) container);
            }
            
            protected void copyParenthoods(HierarchicalContainer container) {
                for (Object itemId: container.getItemIds())
                    unfilteredParents.put(itemId, container.getParent(itemId));
            }
            
            @SuppressWarnings("unchecked")
            protected void handleItemClicks(ItemClickEvent event) {
                Container.Hierarchical container =
                        (Container.Hierarchical) getContainerDataSource();

                if (event.getPropertyId().equals(hierarchicalColumnId) &&
                    container.areChildrenAllowed(event.getItemId())) {
                    // Toggle expanded state
                    if (expanded.contains(event.getItemId()))
                        expanded.remove(event.getItemId());
                    else
                        expanded.add(event.getItemId());
                    
                    // Nothing happens unless we change the value
                    // WARNING The container must be writable, and hopefully
                    // it is an in-memory container...
                    @SuppressWarnings("rawtypes")
                    Property property = container.getContainerProperty(
                              event.getItemId(), event.getPropertyId());
                    property.setValue((Object) property.getValue());
                }
            }

            // Visualize the hierarchy using a CellStyleGenerator
            protected String generateCellStyle(CellReference cellReference) {
                Container.Hierarchical container =
                    (Container.Hierarchical) getContainerDataSource();

                if (cellReference.getPropertyId().equals(hierarchicalColumnId)) {
                    String styles = "grid-tree-node";

                    // Count the number of parents to determine indentation
                    Object parentId = cellReference.getItemId();
                    int parentCount = 0;
                    while ((parentId = unfilteredParents.get(parentId)) != null)
                        parentCount++;
                    if (parentCount > 0)
                        styles += " grid-node-parents-" + parentCount;
    
                    // Is it a leaf node?
                    if (! container.areChildrenAllowed(cellReference.getItemId()))
                        styles += " grid-node-leaf";
                    else
                        // Determine if is currently expanded
                        if (expanded.contains(cellReference.getItemId()))
                            styles += " grid-node-expanded";
                    return styles;
                } else
                    return null;
            }

            class HierarchicalFilter implements Container.Filter {
                private static final long serialVersionUID = -7338253279090855861L;

                @Override
                public boolean passesFilter(Object itemId, Item item)
                    throws UnsupportedOperationException {
                    // Visible if all parents are expanded
                    for (Object parentId = unfilteredParents.get(itemId);
                         parentId != null; parentId = unfilteredParents.get(parentId))
                        if (! expanded.contains(parentId))
                            return false;
                    return true;
                }

                @Override
                public boolean appliesToProperty(Object propertyId) {
                    return propertyId.equals(hierarchicalColumnId);
                }
            }
        }
        
        // Create a grid
        HierarchicalGrid grid = new HierarchicalGrid();
        grid.setCaption("My Grid");
        grid.setWidth("400px");
        grid.setSelectionMode(SelectionMode.NONE);
        
        // Use a hierarchical container with sample data
        HierarchicalContainer container = createHierarchicalContent();
        grid.setContainerDataSource(container);
        
        // Expand all nodes
        grid.expandAll();
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.tricks.hierarchical
    }

    public static HierarchicalContainer createHierarchicalContent() {
        final Object[] inventory = new Object[] {
                "root",
                "+5 Quarterstaff (blessed)",
                "+3 Elven Dagger (blessed)",
                "+5 Helmet (greased)",
                new Object[] {"Sack",
                        "Pick-Axe",
                        "Lock Pick",
                        "Tinning Kit",
                        "Potion of Healing (blessed)",
                },
                new Object[] {"Bag of Holding",
                        "Potion of Invisibility",
                        "Magic Marker",
                        "Can of Grease (blessed)",
                },
                new Object[] {"Chest",
                        "Scroll of Identify",
                        "Scroll of Genocide",
                        "Towel",
                        new Object[] {"Large Box",
                                "Bugle",
                                "Oil Lamp",
                                "Figurine of Vaadin",
                                "Expensive Camera",
                        },
                        "Tin Opener",
                },
        };

        HierarchicalContainer container = new HierarchicalContainer();
        
        // A property that holds the caption is needed for ITEM_CAPTION_MODE_PROPERTY
        container.addContainerProperty("caption", String.class, null);
        container.addContainerProperty("count", Integer.class, null);
        
        new Object() {
            @SuppressWarnings("unchecked")
            public void put(Object[] data, Object parent, HierarchicalContainer container) {
                for (int i=1; i<data.length; i++) {
                    if (data[i].getClass() == String.class) {
                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(data[i]);
                        container.getItem(data[i]).getItemProperty("caption").setValue(data[i]);
                        container.setParent(data[i], parent);
                        container.setChildrenAllowed(data[i], false);
                        
                        int count = Double.valueOf(Math.random()*5 + 1).intValue();
                        container.getItem(data[i]).getItemProperty("count").setValue(count);
                    } else {// It's an Object[]
                        Object[] sub = (Object[]) data[i];
                        String name = (String) sub[0];

                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(name);
                        container.getItem(name).getItemProperty("caption").setValue(name);
                        put(sub, name, container);
                        container.setParent(name, parent);
                    }
                }
            }
        }.put(inventory, null, container);
        
        return container;
    }
}
