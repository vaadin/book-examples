package com.vaadin.book.examples.component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Stack;

import com.vaadin.book.examples.advanced.dd.TreeAndTableExample;
import com.vaadin.book.examples.advanced.dd.TreeAndTableExample.InventoryObject;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.book.examples.lib.Description;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.AbstractSelect.VerticalLocationIs;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TreeTableExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.basic
        TreeTable ttable = new TreeTable("My TreeTable");
        ttable.addContainerProperty("Name", String.class, null);
        ttable.addContainerProperty("Number", Integer.class, null);
        ttable.setWidth("20em");
        
        // Create the tree nodes and set the hierarchy
        ttable.addItem(new Object[]{"Menu", null}, 0);
        ttable.addItem(new Object[]{"Beverages", null}, 1);
        ttable.setParent(1, 0);
        ttable.addItem(new Object[]{"Foods", null}, 2);
        ttable.setParent(2, 0);
        ttable.addItem(new Object[]{"Coffee", 23}, 3);
        ttable.addItem(new Object[]{"Tea", 42}, 4);
        ttable.setParent(3, 1);
        ttable.setParent(4, 1);
        ttable.addItem(new Object[]{"Bread", 13}, 5);
        ttable.addItem(new Object[]{"Cake", 11}, 6);
        ttable.setParent(5, 2);
        ttable.setParent(6, 2);
        
        // Expand the tree
        for (Object itemId: ttable.getContainerDataSource()
                                  .getItemIds()) {
            ttable.setCollapsed(itemId, false);
            
            // Also disallow expanding leaves
            if (! ttable.hasChildren(itemId))
                ttable.setChildrenAllowed(itemId, false);
        }
        
        // Limit the size
        ttable.setPageLength(ttable.getContainerDataSource().size());
        
        Button add = new Button("Add Item", event ->  {
            Object newItemId = ttable.addItemAfter(1);
            ttable.getContainerProperty(newItemId, "Name").setValue("New One");
            ttable.setParent(newItemId, 2);
        });
        // END-EXAMPLE: component.treetable.basic
        
        layout.addComponents(ttable, add);
        layout.setSpacing(true);
    }

    public void components(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.components
        final TreeTable ttable = new TreeTable("My TreeTable");
        ttable.addContainerProperty("Name", CheckBox.class, "");
        ttable.addContainerProperty("City", String.class, "");
        ttable.setWidth("20em");
        
        // Create the tree nodes
        ttable.addItem(new Object[]{new CheckBox("Root"), "Helsinki"}, 0);
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void editable(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.editable
        TreeTable ttable = new TreeTable("My TreeTable");
        ttable.addContainerProperty("name", String.class, "");
        ttable.addContainerProperty("city", String.class, "");
        ttable.setWidth("400px");
        ttable.setPageLength(5);
        
        // Create the tree nodes
        ttable.addItem(new Object[]{"Root", "Helsinki"}, 0);
        ttable.addItem(new Object[]{"Branch 1", "Tampere"}, 1);
        ttable.addItem(new Object[]{"Branch 2", "Turku"}, 2);
        ttable.addItem(new Object[]{"Leaf 1", "Piikkiö"}, 3);
        ttable.addItem(new Object[]{"Leaf 2", "Parainen"}, 4);
        ttable.addItem(new Object[]{"Leaf 3", "Raisio"}, 5);
        ttable.addItem(new Object[]{"Leaf 4", "Naantali"}, 6);
        
        // Set the hierarchy
        ttable.setParent(1, 0);
        ttable.setParent(2, 0);
        ttable.setParent(3, 1);
        ttable.setParent(4, 1);
        ttable.setParent(5, 2);
        ttable.setParent(6, 2);
        
        // Counter for counting field factory calls
        Label counter = new Label("No calls yet");

        // Allow editing
        ttable.setEditable(true);
        ttable.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = 7171599235716520257L;
            
            int callCount = 0;

            @Override
            public Field createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
                counter.setValue("Field factory called " + callCount++ + " times");
                
                // Create a short TextField for all columns
                TextField tf = new TextField();
                tf.addStyleName(ValoTheme.TEXTFIELD_TINY);
                tf.setColumns(10);
                return tf;
            }
        });
        // END-EXAMPLE: component.treetable.editable
        
        layout.addComponents(ttable, counter);
        layout.setSpacing(true);
    }

    @SuppressWarnings("unchecked")
    public void additemafter(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.additemafter
        TreeTable ttable = new TreeTable("My Tree Table");
        ttable.addContainerProperty("Name", String.class, "");
        ttable.addContainerProperty("Type", String.class, "");
        ttable.setPageLength(8);
        layout.addComponent(ttable);
        
        // Reserve excess space for the tree column
        ttable.setWidth("400px");
        ttable.setColumnExpandRatio("Name", 1);

        // Populate table
        Object rootId = ttable.addItem();
        ttable.getContainerProperty(rootId, "Name").setValue("Root");
        ttable.getContainerProperty(rootId, "Type").setValue("Root");
        ttable.setCollapsed(rootId, false);
        Object branch1Id = ttable.addItem();
        ttable.getContainerProperty(branch1Id, "Name").setValue("Branch 1");
        ttable.getContainerProperty(branch1Id, "Type").setValue("Branch");
        ttable.setParent(branch1Id, rootId);
        ttable.setCollapsed(branch1Id, false);
        Object branch2Id = ttable.addItem();
        ttable.getContainerProperty(branch2Id, "Name").setValue("Branch 2");
        ttable.getContainerProperty(branch2Id, "Type").setValue("Branch");
        ttable.setParent(branch2Id, rootId);
        ttable.setCollapsed(branch2Id, false);
        Object leafId = ttable.addItem();
        ttable.getContainerProperty(leafId, "Name").setValue("Leaf");
        ttable.getContainerProperty(leafId, "Type").setValue("Leaf");
        ttable.setParent(leafId, branch1Id);
        ttable.setChildrenAllowed(leafId, false);

        layout.addComponent(new Button("Add Root Item", click -> {
            Object newItem = ttable.addItem();
            ttable.getContainerProperty(newItem, "Name")
                  .setValue("New Root Item");
            ttable.getContainerProperty(newItem, "Type")
                  .setValue("Root");
        }));

        layout.addComponent(new Button("Add Item Under Root", click -> {
            Object newItemId = ttable.addItemAfter(branch1Id);
            ttable.setParent(newItemId, rootId);
            ttable.getContainerProperty(newItemId, "Name")
                  .setValue("New Branch");
            ttable.getContainerProperty(newItemId, "Type")
                  .setValue("Branch");
        }));
        // END-EXAMPLE: component.treetable.additemafter
        
        layout.setSpacing(true);
    }

    public void draganddrop (VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.draganddrop
        final TreeTable ttable = new TreeTable("Inventory");

        // Bind to some demo data
        ttable.setContainerDataSource(new TreeAndTableExample().createTreeContent());
        ttable.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
        
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
                    container.getItem(beanItem).getItemProperty(propertyId)
                             .setValue(beanItem.getItemProperty(propertyId).getValue());

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

    // TODO something with this
    /** A hierarchical bean */
    class Thing implements Serializable {
        private static final long serialVersionUID = 2587697847732360532L;

        String name;
        int number;
        Thing parent;
        
        public Thing(String name, int number) {
            this.name = name;
            this.number = number;
        }
        
        // Setters and getters
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setNumber(int number) {
            this.number = number;
        }
        public int getNumber() {
            return number;
        }
        public void setParent(Thing parent) {
            this.parent = parent;
        }
        public Thing getParent() {
            return parent;
        }
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

    @Description(title="Cell Style for Tree Column in TreeTable", value=
            "<p>This example shows how to style the tree column. " +
            "It's the same example as for Tree, but it's not as easy to style because " +
            "the HTML structure is rather different.</p>"
            + "<p><b>Note:</b> The style setting does not seem to have immediate effect. Bug?</p>")
    public void itemstylegenerator(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.treetable.itemstylegenerator
        TreeTable ttable = new TreeTable("Clickable Inventory");
        ttable.addStyleName("checkboxed");
        ttable.setContainerDataSource(TreeExample.createTreeContent());
        
        // Only allow clicks
        ttable.setSelectable(false);
        
        // Stores which nodes are checked
        final HashSet<String> checked = new HashSet<String>();
        
        // Allow the user to "check" and "uncheck" tree nodes
        // by clicking them
        ttable.addItemClickListener(event -> {
            if (checked.contains(event.getItemId()))
                checked.remove(event.getItemId());
            else
                checked.add((String) event.getItemId());
            
            // TODO: Is this necessary?
            ttable.markAsDirty();
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
    }
}
