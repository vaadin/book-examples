package com.vaadin.book.examples.component;

import java.util.HashSet;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.CollapseEvent;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.VerticalLayout;

public class TreeExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("expanding".equals(context))
            expanding(layout);
        else if ("expandlistener".equals(context))
            expandCollapse();
        else if ("collapselistener".equals(context))
            expandCollapse();
        else if ("itemclicklistener".equals(context))
            itemClickListener();
        else if ("disable".equals(context))
            disableItem();
        else if ("itemstylegenerator".equals(context))
            itemstylegenerator();
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.tree.basic
        Tree tree = new Tree("My Tree");
        
        // Create the tree nodes
        tree.addItem("UI");
        tree.addItem("Branch 1");
        tree.addItem("Branch 2");
        tree.addItem("Leaf 1");
        tree.addItem("Leaf 2");
        tree.addItem("Leaf 3");
        tree.addItem("Leaf 4");
        
        // Set the hierarchy
        tree.setParent("Branch 1", "UI");
        tree.setParent("Branch 2", "UI");
        tree.setParent("Leaf 1", "Branch 1");
        tree.setParent("Leaf 2", "Branch 1");
        tree.setParent("Leaf 3", "Branch 2");
        tree.setParent("Leaf 4", "Branch 2");

        // Disallow children for leaves
        tree.setChildrenAllowed("Leaf 1", false);
        tree.setChildrenAllowed("Leaf 2", false);
        tree.setChildrenAllowed("Leaf 3", false);
        tree.setChildrenAllowed("Leaf 4", false);
        // END-EXAMPLE: component.tree.basic
        
        layout.addComponent(tree);
    }

    void expanding(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.tree.expanding
        Tree tree = new Tree("My Tree");
        
        // Create the tree nodes
        tree.addItem("UI");
        tree.addItem("Branch 1");
        tree.addItem("Branch 2");
        tree.addItem("Leaf 1");
        tree.addItem("Leaf 2");
        tree.addItem("Leaf 3");
        tree.addItem("Leaf 4");
        
        // Set the hierarchy
        tree.setParent("Branch 1", "UI");
        tree.setParent("Branch 2", "UI");
        tree.setParent("Leaf 1", "Branch 1");
        tree.setParent("Leaf 2", "Branch 1");
        tree.setParent("Leaf 3", "Branch 2");
        tree.setParent("Leaf 4", "Branch 2");

        // Disallow children for leaves
        tree.setChildrenAllowed("Leaf 1", false);
        tree.setChildrenAllowed("Leaf 2", false);
        tree.setChildrenAllowed("Leaf 3", false);
        tree.setChildrenAllowed("Leaf 4", false);
        
        // Expand all items that can be
        for (Object itemId: tree.getItemIds())
            tree.expandItem(itemId);
        // END-EXAMPLE: component.tree.expanding
        
        layout.addComponent(tree);
    }
    
    // TODO Include example in book
    void expandCollapse() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.tree.expandlistener
        // Have a tree container with some unexpanded root items
        final HierarchicalContainer container =
            new HierarchicalContainer();
        container.addItem("One Node");
        container.addItem("Another Node");
        container.addItem("Third Node");
        
        // Bind the container to a tree
        final Tree tree = new Tree("My Tree", container);

        // When an item is expanded, add some children to it
        tree.addExpandListener(new Tree.ExpandListener() {
            private static final long serialVersionUID = -7752244479991850484L;
            
            int childCounter = 0;

            public void nodeExpand(ExpandEvent event) {
                // No children for the first node
                if ("One Node".equals(event.getItemId())) {
                    // A late modification
                    tree.setChildrenAllowed(event.getItemId(), false);
                    
                    Notification.show("No children");
                } else {
                    // Add a few new child nodes to the expanded node
                    for (int i=0; i < 3; i++) {
                        String childId = "Child " + (++childCounter);
                        tree.addItem(childId);
                        tree.setParent(childId, event.getItemId());
                    }
    
                    Notification.show("Added nodes");
                }
            }
        });
        // END-EXAMPLE: component.tree.expandlistener

        // BEGIN-EXAMPLE: component.tree.collapselistener
        // When an item is collapsed, remove all its children
        tree.addCollapseListener(new Tree.CollapseListener() {
            private static final long serialVersionUID = -5440538174976602881L;

            public void nodeCollapse(CollapseEvent event) {
                // Remove all children of the collapsing node
                Object children[] = container.getChildren(event.getItemId()).toArray();
                for (int i=0; i<children.length; i++)
                    container.removeItemRecursively(children[i]);

                Notification.show("Removed nodes");
            }
        });
        // END-EXAMPLE: component.tree.collapselistener
        
        layout.addComponent(tree);
        setCompositionRoot(layout);
    }
    
    // TODO Include example in book
    void disableItem() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.tree.disable
        final Tree tree = new Tree("My Tree");
        tree.setContainerDataSource(createTreeContent());
        
        // Show all leaf nodes as disabled
        tree.setItemStyleGenerator(new Tree.ItemStyleGenerator() {
            private static final long serialVersionUID = -3801493951826750909L;

            @Override
            public String getStyle(Tree source, Object itemId) {
                if (! tree.hasChildren(itemId))
                    return "disabled";
                return null;
            }
        });
        
        // Disallow selecting "disabled" leaf nodes
        tree.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 7237016481874141615L;
            
            Object previous = null;

            public void valueChange(ValueChangeEvent event) {
                if (! tree.hasChildren(tree.getValue()))
                    tree.setValue(previous);
                else
                    previous = tree.getValue();
            }
        });
        tree.setImmediate(true);
        
        layout.addComponent(tree);
        // END-EXAMPLE: component.tree.disable
        
        setCompositionRoot(layout);
    }
    
    // TODO Include example in book
    void itemClickListener() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.tree.itemclicklistener
        Tree tree = new Tree("Clickable Inventory");
        tree.setContainerDataSource(createTreeContent());
        
        tree.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            private static final long serialVersionUID = -6287768389172009900L;

            public void itemClick(ItemClickEvent event) {
                // Pick only left mouse clicks
                if (event.getButton() == ItemClickEvent.BUTTON_LEFT)
                    Notification.show("Left click",
                                Notification.Type.HUMANIZED_MESSAGE);
            }
        });
        // END-EXAMPLE: component.tree.itemclicklistener
        layout.addComponent(tree);
        
        setCompositionRoot(layout);
    }

    void itemstylegenerator() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.tree.itemstylegenerator
        final Tree tree = new Tree("Clickable Inventory");
        tree.addStyleName("checkboxed");
        tree.setContainerDataSource(createTreeContent());

        // Only allow clicks
        tree.setSelectable(false);

        // Remember which nodes are checked
        final HashSet<String> checked = new HashSet<String>();

        // Allow the user to "check" and "uncheck" tree nodes
        // by clicking them
        tree.addItemClickListener(event -> { // Java 8
            if (checked.contains(event.getItemId()))
                checked.remove(event.getItemId());
            else
                checked.add((String) event.getItemId());
            
            // Trigger running the item style generator
            tree.markAsDirty();
        });

        Tree.ItemStyleGenerator gen = new Tree.ItemStyleGenerator() {
            private static final long serialVersionUID = -7016120138582433243L;

            @Override
            public String getStyle(Tree source, Object itemId) {
                if (checked.contains(itemId))
                    return "checked";
                else
                    return "unchecked";
            }
        }; 
        tree.setItemStyleGenerator(gen);
        // END-EXAMPLE: component.tree.itemstylegenerator
        layout.addComponent(tree);
        
        setCompositionRoot(layout);
    }
    
    public static HierarchicalContainer createTreeContent() {
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
        container.addContainerProperty("caption", String.class, "");
        
        new Object() {
            public void put(Object[] data, Object parent, HierarchicalContainer container) {
                for (int i=1; i<data.length; i++) {
                    if (data[i].getClass() == String.class) {
                        // Support both ITEM_CAPTION_MODE_ID and ITEM_CAPTION_MODE_PROPERTY
                        container.addItem(data[i]);
                        container.getItem(data[i]).getItemProperty("caption").setValue(data[i]);
                        container.setParent(data[i], parent);
                        container.setChildrenAllowed(data[i], false);
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
