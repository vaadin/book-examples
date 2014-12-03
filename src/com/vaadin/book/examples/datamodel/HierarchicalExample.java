package com.vaadin.book.examples.datamodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class HierarchicalExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("implementing".equals(context))
            implementing(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public static final String implementingDescription =
        "<h1>Implementing the Hierarchical Interface</h1>" +
        "<p>This example shows how to implement <b>Hierarchical</b> interface for <b>BeanItemContainer</b> (BIC).</p>" +
        "<p>The implementation depends on the representation of hierarchy in the contained data type. " +
        "In this implementation, we assume that the hierarchy is represented with a <i>parent</i> property that " +
        "contains the item ID of the parent item. With BIC this is easy because bean references are used " +
        "for the bean IDs, so we can use a getParent() method in the bean. The parent-pointing property " +
        "is parameterized in this implementation.</p>" +
        "<p>Notice that the getChildren() is awfully inefficient in this implementation. It has O(n) " +
        "complexity, which results in at least O(n^2) complexity when painting the tree. If the bean " +
        "type would have references for the children as well, the task would become much lighter.</p>";
    
    // BEGIN-EXAMPLE: datamodel.container.hierarchical.implementing
    /** A bean class with a hierarchy representation */
    public class Body implements Serializable {
        private static final long serialVersionUID = -312738461368736290L;

        String name;
        Body parent; // Represent hierarchy with a simple parent ref

        /** Constructor for root nodes. */
        public Body(String name) {
            this.name       = name;
            this.parent     = null;
        }
        
        /** Constructor for branch or leaf nodes. */
        public Body(String name, Body parent) {
            this.name       = name;
            this.parent     = parent;
        }
        
        public String getName() {
            return name;
        }

        /** The property to get the hierarchy information */
        public Body getParent() {
            return parent;
        }
    }
    
    /** Extension of BeanItemContainer that implements Hierarchical */
    public class HierarchicalBeanItemContainer<T>
           extends BeanItemContainer<T> implements Hierarchical {
        private static final long serialVersionUID = 5475310742299028402L;

        // The contained bean type uses this property to store
        // the parent relationship.
        Object parentPID;
        
        public HierarchicalBeanItemContainer(Class<T> type,
                Object parentPropertyId) {
            super(type);
            
            this.parentPID = parentPropertyId;
        }

        @Override
        public Collection<?> getChildren(Object itemId) {
            LinkedList<Object> children = new LinkedList<Object>();

            // This implementation has O(n^2) complexity when
            // painting the tree, so it's really inefficient.
            for (Object candidateId: getItemIds()) {
                Object parentRef = getItem(candidateId).
                        getItemProperty(parentPID).getValue();
                if (parentRef == itemId)
                    children.add(candidateId);
            }
            
            if (children.size() > 0)
                return children;
            else
                return null;
        }

        @Override
        public Object getParent(Object itemId) {
            return getItem(itemId).
                    getItemProperty(parentPID).getValue();
        }

        @Override
        public Collection<?> rootItemIds() {
            LinkedList<Object> result = new LinkedList<Object>();
            for (Object candidateId: getItemIds()) {
                Object parentRef = getItem(candidateId).
                        getItemProperty(parentPID).getValue();
                if (parentRef == null)
                    result.add(candidateId);
            }
            
            if (result.size() > 0)
                return result;
            else
                return null;
        }

        @Override
        public boolean setParent(Object itemId, Object newParentId)
            throws UnsupportedOperationException {
            throw new UnsupportedOperationException(
                "Not implemented here");
        }

        @Override
        public boolean areChildrenAllowed(Object itemId) {
            return hasChildren(itemId);
        }

        @Override
        public boolean setChildrenAllowed(Object itemId,
                                          boolean childrenAllowed)
                throws UnsupportedOperationException {
            throw new UnsupportedOperationException(
                    "Not implemented here");
        }

        @Override
        public boolean isRoot(Object itemId) {
            return getItem(itemId).getItemProperty(parentPID).
                    getValue() == null;
        }

        @Override
        public boolean hasChildren(Object itemId) {
            for (Object candidateId: getItemIds()) {
                Object parentRef = getItem(candidateId).
                        getItemProperty(parentPID).getValue();
                if (parentRef == itemId)
                    return true;
            }
            return false;
        }
    }
    
    void implementing(VerticalLayout layout) {
        // Create some beans
        Body sun     = new Body("The Sun");
        Body mercury = new Body("Mercury", sun);
        Body venus   = new Body("Venus", sun); 
        Body earth   = new Body("Earth", sun);
        Body moon    = new Body("The Moon", earth);
        Body mars    = new Body("Mars", sun); 
        Body phobos  = new Body("Phobos", mars); 
        Body daimos  = new Body("Daimos", mars); 
        Body jupiter = new Body("Jove", sun); 
        Body io      = new Body("Io", jupiter); 
        Body europa  = new Body("Europa", jupiter); 
        Body ganymede= new Body("Ganymede", jupiter); 
        Body callisto= new Body("Callisto", jupiter); 
        Body saturn  = new Body("Saturn", sun); 
        Body titan   = new Body("Titan", saturn); 
        Body rhea    = new Body("Rhea", saturn); 
        Body iapetus = new Body("Iapetus", saturn); 
        Body dione   = new Body("Dione", saturn); 
        Body tethys  = new Body("Tethys", saturn); 
        Body uranus  = new Body("Uranus", sun); 
        Body titania = new Body("Titania", uranus); 
        Body oberon  = new Body("Oberon", uranus); 
        Body umbriel = new Body("Umbriel", uranus); 
        Body ariel   = new Body("Ariel", uranus); 
        Body neptune = new Body("Neptune", sun);
        Body triton  = new Body("Triton", neptune);
        Body proteus = new Body("Proteus", neptune);
        Body nereid  = new Body("Nereid", neptune);
        Body larissa = new Body("Larissa", neptune);

        // Create a container for the beans
        final HierarchicalBeanItemContainer<Body> bodyContainer =
            new HierarchicalBeanItemContainer<Body>(
                    Body.class, "parent");

        // Add the beans to the container
        Body bodyBeans[] = new Body[] {
            sun, mercury, venus, earth, moon, mars, phobos,
            daimos, jupiter, io, europa, ganymede, callisto,
            saturn, titan, rhea, iapetus, dione, tethys, uranus,
            titania, oberon, umbriel, ariel, neptune, triton,
            proteus, nereid, larissa
        };
        for (Body body: bodyBeans)
            bodyContainer.addBean(body);
                
        // Put them in a tree
        Tree tree = new Tree("Planetary Bodies", bodyContainer);
        tree.setItemCaptionMode(Tree.ITEM_CAPTION_MODE_PROPERTY);
        tree.setItemCaptionPropertyId("name");
        
        // Expand the tree
        for (Object rootItemId: bodyContainer.rootItemIds())
            tree.expandItemsRecursively(rootItemId);
        
        layout.addComponent(tree);
    }        
    // END-EXAMPLE: datamodel.container.hierarchical.implementing
}
