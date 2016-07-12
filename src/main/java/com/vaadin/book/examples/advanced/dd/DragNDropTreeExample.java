package com.vaadin.book.examples.advanced.dd;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.ServerSideCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.Tree.TreeDropCriterion;
import com.vaadin.ui.Tree.TreeTargetDetails;

public class DragNDropTreeExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;

    public void init(String context) {
        if ("tree".equals(context))
            treeNodes();
        else if ("serverside".equals(context))
            serverSideAcceptCriteria();
        else if ("treedropcriterion".equals(context))
            treeDropCriteria();
        else
            setCompositionRoot(new Label("Invalid Context"));
    }
    
    void treeNodes () {
        VerticalLayout layout = new VerticalLayout();

        // Allow all items to have children
        //for (Object itemId : tree.getItemIds())
        //    tree.setChildrenAllowed(itemId, true);
        
        // BEGIN-EXAMPLE: advanced.dragndrop.tree
        final Tree tree = new Tree("Inventory");
        tree.setContainerDataSource(TreeExample.createTreeContent());
        layout.addComponent(tree);
        
        // Expand all items
        for (Iterator<?> it = tree.rootItemIds().iterator(); it.hasNext();)
            tree.expandItemsRecursively(it.next());
        
        // Set the tree in drag source mode
        tree.setDragMode(TreeDragMode.NODE);
        
        // Allow the tree to receive drag drops and handle them
        tree.setDropHandler(new DropHandler() {
            private static final long serialVersionUID = 7492520500678755519L;

            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }

            public void drop(DragAndDropEvent event) {
                // Wrapper for the object that is dragged
                Transferable t = event.getTransferable();
                
                // Make sure the drag source is the same tree
                if (t.getSourceComponent() != tree)
                    return;
                
                TreeTargetDetails target = (TreeTargetDetails)
                    event.getTargetDetails();

                // Get ids of the dragged item and the target item
                Object sourceItemId = t.getData("itemId");
                Object targetItemId = target.getItemIdOver();

                // On which side of the target the item was dropped 
                VerticalDropLocation location = target.getDropLocation();
                
                HierarchicalContainer container = (HierarchicalContainer)
                tree.getContainerDataSource();

                // Drop right on an item -> make it a child
                if (location == VerticalDropLocation.MIDDLE)
                    tree.setParent(sourceItemId, targetItemId);

                // Drop at the top of a subtree -> make it previous
                else if (location == VerticalDropLocation.TOP) {
                    Object parentId = container.getParent(targetItemId);
                    container.setParent(sourceItemId, parentId);
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                }
                
                // Drop below another item -> make it next 
                else if (location == VerticalDropLocation.BOTTOM) {
                    Object parentId = container.getParent(targetItemId);
                    container.setParent(sourceItemId, parentId);
                    container.moveAfterSibling(sourceItemId, targetItemId);
                }
            }
        });
        // END-EXAMPLE: advanced.dragndrop.tree
        
        setCompositionRoot(layout);
    }


    void serverSideAcceptCriteria () {
        VerticalLayout layout = new VerticalLayout();

        final Tree tree = new Tree("Inventory");
        tree.setContainerDataSource(TreeExample.createTreeContent());
        layout.addComponent(tree);
        
        // Expand all items
        for (Iterator<?> it = tree.rootItemIds().iterator(); it.hasNext();)
            tree.expandItemsRecursively(it.next());
        
        // Set the tree in drag source mode
        tree.setDragMode(TreeDragMode.NODE);
        
        // Allow the tree to receive drag drops and handle them
        tree.setDropHandler(new DropHandler() {
            private static final long serialVersionUID = -7145575482446153518L;

            // BEGIN-EXAMPLE: advanced.dragndrop.accept.serverside
            public AcceptCriterion getAcceptCriterion() {
                // Server-side accept criterion that allows drops on any other
                // location except on nodes that may not have children
                ServerSideCriterion criterion = new ServerSideCriterion() {
                    private static final long serialVersionUID = -7676320244243315312L;

                    public boolean accept(DragAndDropEvent dragEvent) {
                        TreeTargetDetails target = (TreeTargetDetails)
                            dragEvent.getTargetDetails();

                        // The tree item on which the load hovers
                        Object targetItemId = target.getItemIdOver();

                        // On which side of the target the item is hovered
                        VerticalDropLocation location = target.getDropLocation();
                        if (location == VerticalDropLocation.MIDDLE)
                            if (! tree.areChildrenAllowed(targetItemId))
                                return false; // Not accepted

                        return true; // Accept everything else
                    }
                };
                return criterion;
            }
            // END-EXAMPLE: advanced.dragndrop.accept.serverside

            public void drop(DragAndDropEvent event) {
                // Wrapper for the object that is dragged
                Transferable t = event.getTransferable();
                
                // Make sure the drag source is the same tree
                if (t.getSourceComponent() != tree)
                    return;
                
                TreeTargetDetails target = (TreeTargetDetails)
                    event.getTargetDetails();

                // Get ids of the dragged item and the target item
                Object sourceItemId = t.getData("itemId");
                Object targetItemId = target.getItemIdOver();

                // On which side of the target the item was dropped 
                VerticalDropLocation location = target.getDropLocation();
                
                HierarchicalContainer container = (HierarchicalContainer)
                tree.getContainerDataSource();

                // Drop right on an item -> make it a child
                if (location == VerticalDropLocation.MIDDLE)
                    tree.setParent(sourceItemId, targetItemId);

                // Drop at the top of a subtree -> make it previous
                else if (location == VerticalDropLocation.TOP) {
                    Object parentId = container.getParent(targetItemId);
                    container.setParent(sourceItemId, parentId);
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                }
                
                // Drop below another item -> make it next 
                else if (location == VerticalDropLocation.BOTTOM) {
                    Object parentId = container.getParent(targetItemId);
                    container.setParent(sourceItemId, parentId);
                    container.moveAfterSibling(sourceItemId, targetItemId);
                }
            }
        });
        
        setCompositionRoot(layout);
    }
    
    // TODO This has a lot of redundancy
    void treeDropCriteria () {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: advanced.dragndrop.tree.treedropcriterion
        final Tree tree = new Tree("Inventory");
        tree.setContainerDataSource(TreeExample.createTreeContent());
        layout.addComponent(tree);
        
        // Expand all items
        for (Iterator<?> it = tree.rootItemIds().iterator(); it.hasNext();)
            tree.expandItemsRecursively(it.next());
        
        // Set the tree in drag source mode
        tree.setDragMode(TreeDragMode.NODE);
        
        // Allow the tree to receive drag drops and handle them
        tree.setDropHandler(new DropHandler() {
            private static final long serialVersionUID = -7145575482446153518L;

            // BEGIN-EXAMPLE: advanced.dragndrop.accept.treedropcriterion
            public AcceptCriterion getAcceptCriterion() {
                // Server-side accept criterion that allows drops on any
                // other tree node except on node that may not have children
                TreeDropCriterion criterion = new TreeDropCriterion() {
                    private static final long serialVersionUID = -7676320244243315312L;

                    @Override
                    protected Set<Object> getAllowedItemIds(
                            DragAndDropEvent dragEvent, Tree tree) {
                        HashSet<Object> allowed = new HashSet<Object>();
                        for (Iterator<?> i = tree.getItemIds().iterator(); i.hasNext();) {
                            Object itemId = i.next();
                            if (tree.hasChildren(itemId))
                                allowed.add(itemId);
                        }
                        return allowed;
                    }
                };
                return criterion;
            }
            // END-EXAMPLE: advanced.dragndrop.accept.treedropcriterion

            public void drop(DragAndDropEvent event) {
                // Wrapper for the object that is dragged
                Transferable t = event.getTransferable();
                
                // Make sure the drag source is the same tree
                if (t.getSourceComponent() != tree)
                    return;
                
                TreeTargetDetails target = (TreeTargetDetails)
                    event.getTargetDetails();

                // Get ids of the dragged item and the target item
                Object sourceItemId = t.getData("itemId");
                Object targetItemId = target.getItemIdOver();

                // On which side of the target the item was dropped 
                VerticalDropLocation location = target.getDropLocation();
                
                HierarchicalContainer container = (HierarchicalContainer)
                tree.getContainerDataSource();

                // Drop right on an item -> make it a child
                if (location == VerticalDropLocation.MIDDLE)
                    tree.setParent(sourceItemId, targetItemId);

                // Drop at the top of a subtree -> make it previous
                else if (location == VerticalDropLocation.TOP) {
                    Object parentId = container.getParent(targetItemId);
                    container.setParent(sourceItemId, parentId);
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                }
                
                // Drop below another item -> make it next 
                else if (location == VerticalDropLocation.BOTTOM) {
                    Object parentId = container.getParent(targetItemId);
                    container.setParent(sourceItemId, parentId);
                    container.moveAfterSibling(sourceItemId, targetItemId);
                }
            }
        });
        // END-EXAMPLE: advanced.dragndrop.tree.treedropcriterion
        
        setCompositionRoot(layout);
    }
}
