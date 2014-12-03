package com.vaadin.book.examples.addons.jpacontainer;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class JPAHierarhicalExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        if ("childrenallowed".equals(context))
            childrenallowed(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // EXAMPLE-REF: jpacontainer.hierarchical.basic com.vaadin.book.examples.addons.jpacontainer.CelestialBody jpacontainer.hierarchical
        // BEGIN-EXAMPLE: jpacontainer.hierarchical.basic
        // Create some beans
        CelestialBody sun     = new CelestialBody("The Sun", null);
        CelestialBody mercury = new CelestialBody("Mercury", sun);
        CelestialBody venus   = new CelestialBody("Venus", sun); 
        CelestialBody earth   = new CelestialBody("Earth", sun);
        CelestialBody moon    = new CelestialBody("The Moon", earth);
        CelestialBody mars    = new CelestialBody("Mars", sun); 
        CelestialBody phobos  = new CelestialBody("Phobos", mars); 
        CelestialBody daimos  = new CelestialBody("Daimos", mars); 
        CelestialBody jupiter = new CelestialBody("Jove", sun); 
        CelestialBody io      = new CelestialBody("Io", jupiter); 
        CelestialBody europa  = new CelestialBody("Europa", jupiter); 
        CelestialBody ganymede= new CelestialBody("Ganymede", jupiter); 
        CelestialBody callisto= new CelestialBody("Callisto", jupiter); 
        CelestialBody saturn  = new CelestialBody("Saturn", sun); 
        CelestialBody titan   = new CelestialBody("Titan", saturn); 
        CelestialBody rhea    = new CelestialBody("Rhea", saturn); 
        CelestialBody iapetus = new CelestialBody("Iapetus", saturn); 
        CelestialBody dione   = new CelestialBody("Dione", saturn); 
        CelestialBody tethys  = new CelestialBody("Tethys", saturn); 
        CelestialBody uranus  = new CelestialBody("Uranus", sun); 
        CelestialBody titania = new CelestialBody("Titania", uranus); 
        CelestialBody oberon  = new CelestialBody("Oberon", uranus); 
        CelestialBody umbriel = new CelestialBody("Umbriel", uranus); 
        CelestialBody ariel   = new CelestialBody("Ariel", uranus); 
        CelestialBody neptune = new CelestialBody("Neptune", sun);
        CelestialBody triton  = new CelestialBody("Triton", neptune);
        CelestialBody proteus = new CelestialBody("Proteus", neptune);
        CelestialBody nereid  = new CelestialBody("Nereid", neptune);
        CelestialBody larissa = new CelestialBody("Larissa", neptune);
        
        // Add the beans to the database using JPA directly
        CelestialBody bodyBeans[] = new CelestialBody[] {
            sun, mercury, venus, earth, moon, mars, phobos,
            daimos, jupiter, io, europa, ganymede, callisto,
            saturn, titan, rhea, iapetus, dione, tethys, uranus,
            titania, oberon, umbriel, ariel, neptune, triton,
            proteus, nereid, larissa
        };
        EntityManager em = JPAContainerFactory.
                createEntityManagerForPersistenceUnit("book-examples");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM CelestialBody p").executeUpdate();
        em.getTransaction().commit();
        for (CelestialBody body: bodyBeans) {
            // Use short transactions to preserve insertion order
            em.getTransaction().begin();
            em.persist(body);
            em.getTransaction().commit();
        }
        
        // Create the container
        JPAContainer<CelestialBody> bodies =
                JPAContainerFactory.makeReadOnly(CelestialBody.class,
                                                 "book-examples");
        
        // Set it up for hierarchical representation
        bodies.setParentProperty("parent");
        
        // Bind it to a hierarchical component 
        Tree tree = new Tree("Celestial Bodies", bodies);
        tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        tree.setItemCaptionPropertyId("name");
        
        // Expand the tree completely
        for (Object rootId: bodies.rootItemIds())
            tree.expandItemsRecursively(rootId);
        // END-EXAMPLE: jpacontainer.hierarchical.basic
        layout.addComponent(tree);
    }
    
    public final static String childrenallowedDescription =
            "<h1>Hiding Node Expand/Collapse Controls</h1>" +
            "<p>By default all nodes can be expanded/collapsed. " +
            "To disable the controls for items, you normally use <tt>setChildrenAllowed()</tt>. " +
            "As it would require storing the state information in the container, it is " +
            "not supported in <b>JPAContainer</b>. You can, however, define your own logic " +
            "for displaying the controls by overriding <tt>areChildrenAllowed()</tt>.</p>";

    void childrenallowed(VerticalLayout layout) {
        // BEGIN-EXAMPLE: jpacontainer.hierarchical.childrenallowed
        // Create some beans
        CelestialBody sun     = new CelestialBody("The Sun", null);
        CelestialBody mercury = new CelestialBody("Mercury", sun);
        CelestialBody venus   = new CelestialBody("Venus", sun); 
        CelestialBody earth   = new CelestialBody("Earth", sun);
        CelestialBody moon    = new CelestialBody("The Moon", earth);
        CelestialBody mars    = new CelestialBody("Mars", sun); 
        CelestialBody phobos  = new CelestialBody("Phobos", mars); 
        CelestialBody daimos  = new CelestialBody("Daimos", mars); 
        CelestialBody jupiter = new CelestialBody("Jove", sun); 
        CelestialBody io      = new CelestialBody("Io", jupiter); 
        CelestialBody europa  = new CelestialBody("Europa", jupiter); 
        CelestialBody ganymede= new CelestialBody("Ganymede", jupiter); 
        CelestialBody callisto= new CelestialBody("Callisto", jupiter); 
        CelestialBody saturn  = new CelestialBody("Saturn", sun); 
        CelestialBody titan   = new CelestialBody("Titan", saturn); 
        CelestialBody rhea    = new CelestialBody("Rhea", saturn); 
        CelestialBody iapetus = new CelestialBody("Iapetus", saturn); 
        CelestialBody dione   = new CelestialBody("Dione", saturn); 
        CelestialBody tethys  = new CelestialBody("Tethys", saturn); 
        CelestialBody uranus  = new CelestialBody("Uranus", sun); 
        CelestialBody titania = new CelestialBody("Titania", uranus); 
        CelestialBody oberon  = new CelestialBody("Oberon", uranus); 
        CelestialBody umbriel = new CelestialBody("Umbriel", uranus); 
        CelestialBody ariel   = new CelestialBody("Ariel", uranus); 
        CelestialBody neptune = new CelestialBody("Neptune", sun);
        CelestialBody triton  = new CelestialBody("Triton", neptune);
        CelestialBody proteus = new CelestialBody("Proteus", neptune);
        CelestialBody nereid  = new CelestialBody("Nereid", neptune);
        CelestialBody larissa = new CelestialBody("Larissa", neptune);
        
        // Add the beans to the database using JPA
        CelestialBody bodyBeans[] = new CelestialBody[] {
            sun, mercury, venus, earth, moon, mars, phobos,
            daimos, jupiter, io, europa, ganymede, callisto,
            saturn, titan, rhea, iapetus, dione, tethys, uranus,
            titania, oberon, umbriel, ariel, neptune, triton,
            proteus, nereid, larissa
        };
        EntityManager em = JPAContainerFactory.
                createEntityManagerForPersistenceUnit("book-examples");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM CelestialBody p").executeUpdate();
        em.getTransaction().commit();
        for (CelestialBody body: bodyBeans) {
            em.getTransaction().begin();
            em.persist(body);
            em.getTransaction().commit(); // Commit to preserve order
        }
        
        /*********************************************************************/
        /* The relevant part:                                                */
        
        // Customize JPAContainer to define the logic for
        // displaying the node expansion indicator.
        JPAContainer<CelestialBody> bodies =
                new JPAContainer<CelestialBody>(CelestialBody.class) {
            private static final long serialVersionUID = 1827106776364469552L;

            @Override
            public boolean areChildrenAllowed(Object itemId) {
                return getChildren(itemId).size() > 0;
            }
        };
        bodies.setEntityProvider(
            new CachingLocalEntityProvider<CelestialBody>(
                CelestialBody.class, em));
        /*********************************************************************/
        
        // Set it up for hierarchical representation
        bodies.setParentProperty("parent");
        
        // Bind it to a hierarhical component
        Tree tree = new Tree("Celestial Bodies", bodies);
        tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        tree.setItemCaptionPropertyId("name");
        
        // Expand the tree
        for (Object rootId: bodies.rootItemIds())
            tree.expandItemsRecursively(rootId);
        // END-EXAMPLE: jpacontainer.hierarchical.childrenallowed
        layout.addComponent(tree);
    }
}
