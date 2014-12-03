package com.vaadin.book.examples.addons.jpacontainer;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class JPAContainerExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;
    
    // BEGIN-EXAMPLE: jpacontainer.nonpersistent
    /* Comets are not persistent */
    public class Comet {
        int id;
        String name;
        
        public Comet(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    
    public void nonpersistent(VerticalLayout layout) {
        BeanItemContainer<Comet> comets = new BeanItemContainer<Comet>(Comet.class);
        
        comets.addBean(new Comet(1, "Halley"));
        comets.addBean(new Comet(2, "Encke"));
        comets.addBean(new Comet(3, "Biela"));
        comets.addBean(new Comet(4, "Faye"));
        
        Table cometTable = new Table("The Comets", comets);
        // END-EXAMPLE: jpacontainer.nonpersistent
        cometTable.setPageLength(cometTable.size());
        layout.addComponent(cometTable);
    }

    public static final String basicDescription =
        "<h1>Basic Use of JPAContainer</h1>"+
        "<p>The easiest way to create a <b>JPAContainer</b> is to use the <b>JPAContainerFactory</b>.</p>";

    public void basic(VerticalLayout layout) {
        // EXAMPLE-REF: jpacontainer.basic com.vaadin.book.examples.addons.jpacontainer.Person jpacontainer.basic
        // BEGIN-EXAMPLE: jpacontainer.basic
        // Let's have some data created with pure JPA
        EntityManager em = JPAContainerFactory.
            createEntityManagerForPersistenceUnit("book-examples");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.persist(new Person("Jeanne Calment", 122));
        em.persist(new Person("Sarah Knauss", 119));
        em.persist(new Person("Lucy Hannah", 117));
        em.getTransaction().commit();
        
        // Create a persistent person container
        JPAContainer<Person> persons =
            JPAContainerFactory.make(Person.class, "book-examples");

        // You can add entities to the container as well
        persons.addEntity(new Person("Marie-Louise Meilleur", 117));
        
        // Bind it to a component
        Table personTable = new Table("The Persistent People", persons);
        personTable.setVisibleColumns(new Object[]{"id","name","age"});
        layout.addComponent(personTable);
        // END-EXAMPLE: jpacontainer.basic

        // Set up sorting
        persons.sort(new String[]{"age", "name"},
                     new boolean[]{false, false});

        personTable.setPageLength(4);
        layout.addComponent(personTable);
    }

    public static final String thehardwayDescription =
        "<h1>The Hard Way to Create a JPAContainer</h1>"+
        "<p>You need the following steps:</p>" +
        "<ol>" +
        "  <li>Create an <b>EntityManagerFactory</b></li>" +
        "  <li>Create an <b>EntityManager</b></li>" +
        "  <li>Create an entity provider</li>" +
        "  <li>Create the <b>JPAContainer</b></li>" +
        "  <li>Use the entity provider in the <b>JPAContainer</b></li>" +
        "</ol>";

    public void thehardway(VerticalLayout layout) {
        // BEGIN-EXAMPLE: jpacontainer.thehardway
        // We need a factory to create entity manager
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("book-examples");
        
        // We need an entity manager to create entity provider
        EntityManager em = emf.createEntityManager();
        
        // We need an entity provider to create a container        
        CachingMutableLocalEntityProvider<Person> entityProvider =
            new CachingMutableLocalEntityProvider<Person>(Person.class,
                                                          em);
        
        // And there we have it
        JPAContainer<Person> persons =
                new JPAContainer<Person> (Person.class);
        persons.setEntityProvider(entityProvider);
        
        // We have to empty it for the purpose of the example -
        // the items are really persistent.
        for (Object itemId: persons.getItemIds())
            persons.removeItem(itemId);
        
        // Add some items to it
        persons.addEntity(new Person("Jeanne Calment", 122));
        persons.addEntity(new Person("Sarah Knauss", 119));
        persons.addEntity(new Person("Lucy Hannah", 117));
        persons.addEntity(new Person("Marie-Louise Meilleur", 117));
        
        // Bind it to a component
        Table personTable = new Table("The Persistent People", persons);
        personTable.setVisibleColumns(new Object[]{"id","name","age"});
        // END-EXAMPLE: jpacontainer.thehardway

        personTable.setPageLength(4);
        layout.addComponent(personTable);
    }

    public static final String bufferingDescription =
            "<h1>Container Buffering</h1>"+
            "<p>You can enable container-level buffering with <tt>setAutoCommit(false)</tt>. " +
            "To commit changes, call <tt>commit()</tt> for the container.</p>";

    public void buffering(VerticalLayout layout) {
        // BEGIN-EXAMPLE: jpacontainer.buffering
        // Let's have some data created with pure JPA
        EntityManager em = JPAContainerFactory.
            createEntityManagerForPersistenceUnit("book-examples");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createQuery("DELETE FROM Country c").executeUpdate();
        Country france = new Country("France");
        Country us     = new Country("United States");
        em.persist(france);
        em.persist(us);
        em.persist(new Person("Jeanne Calment", 122, france));
        em.persist(new Person("Sarah Knauss", 119, us));
        em.persist(new Person("Lucy Hannah", 117, us));
        em.getTransaction().commit();
        
        // Create a persistent person container
        final JPAContainer<Person> persons =
            JPAContainerFactory.makeBatchable(Person.class, "book-examples");
        
        // Enable container-level buffering
        persons.setBuffered(true);
        persons.setAutoCommit(false);

        // Bind it to a component
        final Table personTable = new Table(null, persons);
        personTable.setVisibleColumns(new Object[]{"id","name","age","country"});
        
        class EditorForm extends Panel {
            private static final long serialVersionUID = -6231154324593170205L;

            FieldGroup binder;
            VerticalLayout content = new VerticalLayout();
            FormLayout form = new FormLayout();
            HorizontalLayout footer = new HorizontalLayout();

            public EditorForm () {
                super("Editor");
                setContent(content);
                content.addComponent(form);
                content.addComponent(footer);
            }

            public void setItemDataSource(Item item) {
                if (binder == null) {
                    binder = new FieldGroup(item);
                    FieldFactory factory = new FieldFactory();
                    binder.setBuffered(true);
                    for (Object pid: item.getItemPropertyIds()) {
                        Field<?> field = factory.createField(item, pid, this);
                        if (field != null) {
                            form.addComponent(field);
                            binder.bind(field, pid);
                        }
                    }
                } else
                    binder.setItemDataSource(item);
            }
        };
        final EditorForm editor = new EditorForm();
        editor.setVisible(false);

        final Button add = new Button("Add New");
        add.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -2648881989359843992L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Create an empty item and add it to the container -
                // it's not yet written to the database as buffering
                // is enabled
                Object itemId = persons.addEntity(new Person(null, 0));

                editor.setItemDataSource(persons.getItem(itemId));
                editor.setVisible(true);
            }
        });

        Button save = new Button("Save");
        save.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -4113540091230273025L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    // Item-level commit the form
                    editor.binder.commit();
                    
                    // Commit adding the item to the container
                    persons.commit();
                    
                    editor.setVisible(false);
                } catch (CommitException e) {
                    Notification.show("Save failed");
                }
            }
        });
        editor.footer.addComponent(save);

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 8607289364855257525L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Discard the edit as well as adding the entity
                editor.binder.discard();
                persons.discard();

                editor.setVisible(false);
            }
        });
        editor.footer.addComponent(cancel);

        HorizontalLayout horizontal = new HorizontalLayout();
        Panel masterPanel = new Panel("People");
        VerticalLayout masterPanelContent = new VerticalLayout();
        masterPanel.setContent(masterPanelContent);
        masterPanelContent.addComponent(personTable);
        masterPanelContent.addComponent(add);
        horizontal.addComponent(masterPanel);
        horizontal.addComponent(editor);
        // END-EXAMPLE: jpacontainer.bufferinc

        // Set up sorting
        persons.sort(new String[]{"age", "name"},
                     new boolean[]{false, false});

        personTable.setPageLength(4);
        layout.addComponent(horizontal);
    }

    /*************************************************************************
     * Nested Properties
     *************************************************************************/
    public static final String nestedDescription =
        "<h1>Nested Properties</h1>"+
        "<p></p>";

    public void nested(VerticalLayout layout) {
        // Populate with example data
        JPAContainerExample.insertExampleData();
        
        // BEGIN-EXAMPLE: jpacontainer.nested
        // Have a persistent container
        JPAContainer<Person> persons =
            JPAContainerFactory.make(Person.class, "book-examples");

        // Add a nested property to a many-to-one property
        persons.addNestedContainerProperty("country.name");
        
        // Show the persons in a table, except the "country" column,
        // which is an object - show the nested property instead
        Table personTable = new Table("The Persistent People", persons);
        personTable.setVisibleColumns(new String[]{"name","age",
                                                   "country.name"});

        // Have a nicer caption for the country.name column
        personTable.setColumnHeader("country.name", "Nationality");
        // END-EXAMPLE: jpacontainer.nested

        // Set up sorting
        persons.sort(new String[]{"age", "name"},
                     new boolean[]{false, false});
        
        personTable.setPageLength(5);
        layout.addComponent(personTable);
    }

    static HashMap<String,Country> insertExampleDataWithJPAContainer(JPAContainer<Person> persons, JPAContainer<Country> countries) {
        // We have to empty it for the purpose of the example -
        // the items are really persistent.
        for (Object itemId: persons.getItemIds())
            persons.removeItem(itemId);
        for (Object itemId: countries.getItemIds())
            countries.removeItem(itemId);
        
        // Add some items to the containers
        
        // Create a new entity and add it to a container        
        Country france = new Country("France");
        Object itemId = countries.addEntity(france);
        
        // Get the managed entity
        france = countries.getItem(itemId).getEntity();

        // Use the managed entity in entity references
        persons.addEntity(new Person("Jeanne Calment", 122, france));
        
        // Add a bunch of other items
        HashMap<String,Country> countryMap = new HashMap<String, Country>();
        for (String s: new String[]{"United States", "Canada", "Ecuador", "Japan"}) {
            Country c = new Country(s);
            Object id = countries.addEntity(c);
            countryMap.put(s, countries.getItem(id).getEntity());
        }
        
        persons.addEntity(new Person("Sarah Knauss", 119, countryMap.get("United States")));
        persons.addEntity(new Person("Lucy Hannah", 117, countryMap.get("United States")));
        persons.addEntity(new Person("Marie-Louise Meilleur", 117, countryMap.get("Canada")));
        persons.addEntity(new Person("María Capovilla", 116, countryMap.get("Ecuador")));
        persons.addEntity(new Person("Tane Ikai", 116, countryMap.get("Japan")));
        persons.addEntity(new Person("Elizabeth Bolden", 116, countryMap.get("United States")));
        persons.addEntity(new Person("Carrie White", 116, countryMap.get("United States")));
        persons.addEntity(new Person("Kamato Hongo", 116, countryMap.get("Japan")));
        persons.addEntity(new Person("Maggie Barnes", 115, countryMap.get("United States")));
        
        return countryMap;
    }

    static void insertExampleData() {
        /*
        // Check if there already is data
        EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit("book-examples");
        Query q = em.createQuery("SELECT COUNT(p.id) FROM Person p");
        int count = (Integer) q.getSingleResult();
        BookExamplesUI.getLogger().info("First result= " + count);
        if (count > 0)
            return;
            */

        // Let's have some data created with using pure JPA
        EntityManager em = JPAContainerFactory.
            createEntityManagerForPersistenceUnit("book-examples");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createQuery("DELETE FROM Country c").executeUpdate();
        
        // Add the countries
        HashMap<String,Country> countryMap = new HashMap<String, Country>();
        for (String s: new String[]{"France", "United States", "Canada", "Ecuador", "Japan"}) {
            Country c = new Country(s);
            em.persist(c);
            countryMap.put(s, c);
        }
        
        em.persist(new Person("Jeanne Calment", 122, countryMap.get("France")));
        em.persist(new Person("Sarah Knauss", 119, countryMap.get("United States")));
        em.persist(new Person("Lucy Hannah", 117, countryMap.get("United States")));
        em.persist(new Person("Marie-Louise Meilleur", 117, countryMap.get("Canada")));
        em.persist(new Person("María Capovilla", 116, countryMap.get("Ecuador")));
        em.persist(new Person("Tane Ikai", 116, countryMap.get("Japan")));
        em.persist(new Person("Elizabeth Bolden", 116, countryMap.get("United States")));
        em.persist(new Person("Carrie White", 116, countryMap.get("United States")));
        em.persist(new Person("Kamato Hongo", 116, countryMap.get("Japan")));
        em.persist(new Person("Maggie Barnes", 115, countryMap.get("United States")));
        em.getTransaction().commit();
    }
}
