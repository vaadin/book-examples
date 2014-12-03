package com.vaadin.book.examples.component;

import java.util.HashSet;
import java.util.Stack;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.advanced.dd.TreeAndTableExample;
import com.vaadin.book.examples.advanced.dd.TreeAndTableExample.InventoryObject;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.VerticalLocationIs;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

public class TreeTableExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.basic
        final TreeTable ttable = new TreeTable("My TreeTable");
        ttable.addContainerProperty("Name", String.class, "");
        ttable.setWidth("20em");
        
        // Create the tree nodes
        ttable.addItem(new Object[]{"UI"}, 0);
        ttable.addItem(new Object[]{"Branch 1"}, 1);
        ttable.addItem(new Object[]{"Branch 2"}, 2);
        ttable.addItem(new Object[]{"Leaf 1"}, 3);
        ttable.addItem(new Object[]{"Leaf 2"}, 4);
        ttable.addItem(new Object[]{"Leaf 3"}, 5);
        ttable.addItem(new Object[]{"Leaf 4"}, 6);
        
        // Set the hierarchy
        ttable.setParent(1, 0);
        ttable.setParent(2, 0);
        ttable.setParent(3, 1);
        ttable.setParent(4, 1);
        ttable.setParent(5, 2);
        ttable.setParent(6, 2);
        
        // Expand the tree
        ttable.setCollapsed(2, false);
        //for (Object itemId: tree.getItemIds())
        //    tree.setCollapsed(itemId, false);
        
        Button foo = new Button("Foo", new Button.ClickListener() {
            private static final long serialVersionUID = 8903978809209811750L;

            @Override
            public void buttonClick(ClickEvent event) {
                Object newItemId = ttable.addItemAfter(1);
                ttable.getContainerProperty(newItemId, "Name").setValue("New One");
                ttable.setParent(newItemId, 2);
            }
        });
        // END-EXAMPLE: component.treetable.basic
        
        layout.addComponent(ttable);
        layout.addComponent(foo);
    }

    public void components(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.components
        final TreeTable ttable = new TreeTable("My TreeTable");
        ttable.addContainerProperty("Name", CheckBox.class, "");
        ttable.addContainerProperty("City", String.class, "");
        ttable.setWidth("20em");
        
        // Create the tree nodes
        ttable.addItem(new Object[]{new CheckBox("UI"), "Helsinki"}, 0);
        ttable.addItem(new Object[]{new CheckBox("Branch 1"), "Tampere"}, 1);
        ttable.addItem(new Object[]{new CheckBox("Branch 2"), "Turku"}, 2);
        ttable.addItem(new Object[]{new CheckBox("Leaf 1"), "Piikkiö"}, 3);
        ttable.addItem(new Object[]{new CheckBox("Leaf 2"), "Parainen"}, 4);
        ttable.addItem(new Object[]{new CheckBox("Leaf 3"), "Raisio"}, 5);
        ttable.addItem(new Object[]{new CheckBox("Leaf 4"), "Naantali"}, 6);
        
        // Set the hierarchy
        ttable.setParent(1, 0);
        ttable.setParent(2, 0);
        ttable.setParent(3, 1);
        ttable.setParent(4, 1);
        ttable.setParent(5, 2);
        ttable.setParent(6, 2);
        
        // Expand the tree
        ttable.setCollapsed(2, false);
        for (Object itemId: ttable.getItemIds())
            ttable.setCollapsed(itemId, false);
        
        ttable.setPageLength(ttable.size());
        // END-EXAMPLE: component.treetable.components
        
        layout.addComponent(ttable);
    }

    public void additemafter(VerticalLayout layout) {
        final TreeTable ttable = new TreeTable("Simple test with builtin container");
        ttable.addContainerProperty("Name", String.class, "");
        ttable.addContainerProperty("Type", String.class, "");
        layout.addComponent(ttable);
        
        // populate table
        final Object rootId = ttable.addItem();
        ttable.getContainerProperty(rootId, "Name").setValue("UI");
        final Object branch1Id = ttable.addItem();
        ttable.getContainerProperty(branch1Id, "Name").setValue("Branch 1");
        final Object branch2Id = ttable.addItem();
        ttable.getContainerProperty(branch2Id, "Name").setValue("Branch 2");
        Object leafId = ttable.addItem();
        ttable.getContainerProperty(leafId, "Name").setValue("Leaf");

        // build hierarchy
        ttable.setParent(leafId, branch1Id);
        ttable.setParent(branch1Id, rootId);

        // flag to last item to be leaf
        ttable.setChildrenAllowed(leafId, false);

        // reserve excess space for the "treecolumn"
        ttable.setWidth("100%");
        ttable.setColumnExpandRatio("Name", 1);
       
        Button lAddItemButton = new Button("Add Item");
        layout.addComponent(lAddItemButton);
       
        lAddItemButton.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 6810618553723194020L;

            public void buttonClick(ClickEvent event) {
                Object newItem = ttable.addItem();
                ttable.getContainerProperty(newItem, "Name").setValue("New Add Item");
            }
        });
       
        Button lAddItemAfterButton = new Button("Add Item After");
        layout.addComponent(lAddItemAfterButton);
       
        lAddItemAfterButton.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1304303387443588874L;

            public void buttonClick(ClickEvent event) {
                Object newItemId = ttable.addItemAfter(branch1Id);
                ttable.setParent(newItemId, rootId);
                ttable.getContainerProperty(newItemId, "Name").setValue("New Add Item After");
            }
        });
    }        

    public void draganddrop (VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.draganddrop
        final TreeTable ttable = new TreeTable("Inventory");

        // Bind to some demo data
        ttable.setContainerDataSource(new TreeAndTableExample().createTreeContent());
        ttable.setItemCaptionMode(Table.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
        
        // Expand all nodes
        for (Object item: ttable.getItemIds().toArray())
            ttable.setCollapsed(item, false);
        
        // Enable dragging single rows from the treetable
        ttable.setDragMode(TableDragMode.ROW);
        
        // Allow the treetable to receive drag drops and handle them
        ttable.setDropHandler(new DropHandler() {
            private static final long serialVersionUID = 7492520500678755519L;

            public AcceptCriterion getAcceptCriterion() {
                // Accept drops in the middle of items that can have
                // children, and below and above all items.
                return new Or (Tree.TargetItemAllowsChildren.get(),
                               new Not(VerticalLocationIs.MIDDLE));
            }

            public void drop(DragAndDropEvent event) {
                // Wrapper for the object that is dragged
                DataBoundTransferable t = (DataBoundTransferable)
                    event.getTransferable();
                
                AbstractSelectTargetDetails target =
                    (AbstractSelectTargetDetails) event.getTargetDetails();

                // Get ids of the dragged item and the target item
                Object sourceItemId = t.getData("itemId");
                Object targetItemId = target.getItemIdOver();
                
                // Check that the target is not in the subtree of
                // the dragged item itself
                for (Object itemId = targetItemId; itemId != null; itemId = ttable.getParent(itemId))
                    if (itemId == sourceItemId)
                        return;

                // On which side of the target was the item dropped 
                VerticalDropLocation location = target.getDropLocation();
                
                HierarchicalContainer container =
                    (HierarchicalContainer) ttable.getContainerDataSource();

                // Do some hassle with the example data representation
                BeanItem<?> beanItem = null;
                if (sourceItemId instanceof BeanItem<?>)
                    beanItem = (BeanItem<?>) sourceItemId;
                else if (sourceItemId instanceof InventoryObject)
                    beanItem = new BeanItem<InventoryObject>((InventoryObject) sourceItemId);
                
                // Remove the item from the source container and
                // add it to the tree's container
                Container sourceContainer = t.getSourceContainer();
                sourceContainer.removeItem(sourceItemId);
                ttable.addItem(beanItem); // Hassle

                // More hassle: copy the property values from the BeanItem to the container item
                for (Object propertyId: container.getContainerPropertyIds())
                    container.getItem(beanItem).getItemProperty(propertyId).setValue(beanItem.getItemProperty(propertyId));

                InventoryObject bean = (InventoryObject) beanItem.getBean();
                ttable.setChildrenAllowed(beanItem, bean.isContainer());

                // Drop right on an item -> make it a child
                if (location == VerticalDropLocation.MIDDLE)
                    ttable.setParent(beanItem, targetItemId);
                
                // Drop at the top of a subtree -> make it previous
                else if (location == VerticalDropLocation.TOP) {
                    Object parentId = container.getParent(targetItemId);
                    ttable.setParent(beanItem, parentId);
                    container.moveAfterSibling(beanItem, targetItemId);
                    container.moveAfterSibling(targetItemId, beanItem);
                }
                
                // Drop below another item -> make it next 
                else if (location == VerticalDropLocation.BOTTOM) {
                    Object parentId = container.getParent(targetItemId);
                    ttable.setParent(beanItem, parentId);
                    container.moveAfterSibling(beanItem, targetItemId);
                }
                
                ttable.setItemCaption(beanItem, bean.getName());
            }
        });
        // END-EXAMPLE: component.treetable.draganddrop
        
        layout.addComponent(ttable);
    }
    
    public void big(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.big
        TreeTable ttable = new TreeTable("Big TreeTable");
        ttable.addContainerProperty("name", String.class, null);
        ttable.addContainerProperty("number", Integer.class, null);
        ttable.setWidth("400px");

        Stack<Integer> parents = new Stack<Integer>();
        for (int i=0; i<3000; i++) {
            Item item = ttable.addItem(i); // Use index as item ID
            item.getItemProperty("name").setValue("Item " + i);
            item.getItemProperty("number").setValue(i);

            if (parents.size() > 0)
                ttable.setParent(i, parents.lastElement());
            ttable.setCollapsed(i, false);

            if (parents.size() < 4 && Math.random() > 0.65)
                parents.push(i);
            else {
                ttable.setChildrenAllowed(i, false);
                if (parents.size() > 0 && Math.random() > 0.65)
                    parents.pop();
            }
        }
        // END-EXAMPLE: component.treetable.big
        
        layout.addComponent(ttable);
    }

    public final static String itemstylegeneratorDescription =
            "<h1>Cell Style for Tree Column in TreeTable</h1>" +
            "<p>This example shows how to style the tree column. It's the same example as for Tree, but it's not as easy to style because " +
            "the HTML structure is rather different.</p>";
    
    public void itemstylegenerator() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.treetable.itemstylegenerator
        final TreeTable ttable = new TreeTable("Clickable Inventory");
        ttable.addStyleName("checkboxed");
        ttable.setContainerDataSource(TreeExample.createTreeContent());
        
        // Only allow clicks
        ttable.setSelectable(false);
        
        // Stores which nodes are checked
        final HashSet<String> checked = new HashSet<String>();
        
        // Allow the user to "check" and "uncheck" tree nodes
        // by clicking them
        ttable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            private static final long serialVersionUID = 5548609446898735032L;

            public void itemClick(ItemClickEvent event) {
                if (checked.contains(event.getItemId()))
                    checked.remove(event.getItemId());
                else
                    checked.add((String) event.getItemId());
                
                // TODO: Is this necessary?
                ttable.markAsDirty();
            }
        });
        
        Table.CellStyleGenerator cellStyleGenerator = new Table.CellStyleGenerator() {
            private static final long serialVersionUID = -401201653443233243L;

            @Override
            public String getStyle(Table source, Object itemId,
                                   Object propertyId) {
                if ("caption".equals(propertyId))
                    if (checked.contains(itemId))
                        return "checked";
                    else
                        return "unchecked";
                else
                    return null;
            }
        }; 
        ttable.setCellStyleGenerator(cellStyleGenerator);
        // END-EXAMPLE: component.treetable.itemstylegenerator
        layout.addComponent(ttable);
        
        setCompositionRoot(layout);
    }
}
