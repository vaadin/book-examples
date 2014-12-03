package com.vaadin.book.examples.addons.jpacontainer;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class JPAFilteringExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("entity".equals(context))
            entity(layout);
        else if ("querymodification".equals(context))
            querymodification(layout);
        
        setCompositionRoot(layout);
    }
    
    public static final String basicDescription =
        "<h1>Filtering</h1>"+
        "<p></p>";

    void basic(Layout layout) {
        JPAContainerExample.insertExampleData();

        // BEGIN-EXAMPLE: jpacontainer.filtering.basic
        // Create a persistent container
        final JPAContainer<Person> persons =
            JPAContainerFactory.make(Person.class, "book-examples");

        // Show only all people older than 116
        Filter filter = new Compare.Greater("age", 116);
        persons.addContainerFilter(filter);
        
        Table personTable = new Table("The Persistent People", persons);
        persons.addNestedContainerProperty("country.name");
        personTable.setVisibleColumns(new String[]{"id","name","age",
                                                   "country.name"});
        // END-EXAMPLE: jpacontainer.filtering.basic

        personTable.setPageLength(5);
        layout.addComponent(personTable);
    }

    public static final String entityDescription =
            "<h1>Filtering by Entity</h1>"+
            "<p>You can filter a container using another entity with <b>Equals</b> operator, so that the other entity must " +
            "match a property value.</p>";

    void entity(Layout layout) {
        JPAContainerExample.insertExampleData();

        // BEGIN-EXAMPLE: jpacontainer.filtering.entity
        // Create persistent containers
        final JPAContainer<Person> persons =
            JPAContainerFactory.make(Person.class, "book-examples");
        final JPAContainer<Country> countries =
            JPAContainerFactory.make(Country.class, "book-examples");

        // A list for selecting the filter object 
        ListSelect countrySelect = new ListSelect("Pick a Filter", countries);
        countrySelect.setItemCaptionMode(ListSelect.ITEM_CAPTION_MODE_PROPERTY);
        countrySelect.setItemCaptionPropertyId("name");
        countrySelect.setNullSelectionAllowed(false);
        countrySelect.addListener(new ValueChangeListener() {
            private static final long serialVersionUID = -545624716030176003L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                // Get the entity object of the selected row
                Object itemId = event.getProperty().getValue();
                EntityItem<Country> item = countries.getItem(itemId);
                Country country = item.getEntity();
                
                // Remove old filter
                persons.removeAllContainerFilters();

                // Add new filter
                Filter filter = new Compare.Equal("country", country);
                persons.addContainerFilter(filter);
            }
        });
        countrySelect.setImmediate(true);

        // Bind the container to UI
        Table personTable = new Table("The Persistent People", persons);
        persons.addNestedContainerProperty("country.name");
        personTable.setVisibleColumns(new String[]{"id","name","age",
                                                   "country.name"});
        // END-EXAMPLE: jpacontainer.filtering.entity

        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        countrySelect.setRows(5);
        hlayout.addComponent(countrySelect);
        personTable.setPageLength(5);
        hlayout.addComponent(personTable);
        layout.addComponent(hlayout);
    }

    public static final String querymodificationDescription =
            "<h1>Querying with the Criteria API</h1>"+
            "<p>You can use the JPA Criteria API directly to make queries. To do so, you must implement a <b>QueryModifierDelegate</b>.</p>";

    void querymodification(Layout layout) {
        JPAContainerExample.insertExampleData();

        // BEGIN-EXAMPLE: jpacontainer.criteria.querymodification
        // Create persistent container
        final JPAContainer<Person> persons =
            JPAContainerFactory.make(Person.class, "book-examples");

        // Modify the query to include only people over 116
        persons.getEntityProvider().setQueryModifierDelegate(
                new DefaultQueryModifierDelegate () {
            @Override
            public void filtersWillBeAdded(
                    CriteriaBuilder criteriaBuilder,
                    CriteriaQuery<?> query,
                    List<Predicate> predicates) {
                Root<?> fromPerson = query.getRoots().iterator().next();

                // Add a "WHERE age > 116" expression
                Path<Integer> age = fromPerson.<Integer>get("age");
                predicates.add(criteriaBuilder.gt(age, 116));
            }
        });

        Table personTable = new Table("Really Persistent People", persons);
        persons.addNestedContainerProperty("country.name");
        personTable.setVisibleColumns(new String[]{"id","name","age",
                                                   "country.name"});
        // END-EXAMPLE: jpacontainer.criteria.querymodification

        personTable.setPageLength(persons.size());
        layout.addComponent(personTable);
    }

    public static final String nativefilterDescription =
        "<h1>Filtering with a Native SQL Filter</h1>"+
        "<p>Filtering with native SQL is necessary for filtering where some native WHERE operations are not supported by JPAQL.</p>";

    void nativefilter(Layout layout) {
        JPAContainerExample.insertExampleData();
    
        // BEGIN-EXAMPLE: jpacontainer.criteria.querymodification
        // Create persistent container
        final JPAContainer<Person> persons =
            JPAContainerFactory.make(Person.class, "book-examples");
    
        // Modify the query to include only people over 116
        persons.getEntityProvider().setQueryModifierDelegate(
                new DefaultQueryModifierDelegate () {
            @Override
            public void filtersWillBeAdded(
                    CriteriaBuilder criteriaBuilder,
                    CriteriaQuery<?> query,
                    List<Predicate> predicates) {
                Root<?> fromPerson = query.getRoots().iterator().next();
    
                // Add a "WHERE age > 116" expression
                Path<Integer> age = fromPerson.<Integer>get("age");
                predicates.add(criteriaBuilder.gt(age, 116));
            }
        });
    
        Table personTable = new Table("Really Persistent People", persons);
        persons.addNestedContainerProperty("country.name");
        personTable.setVisibleColumns(new String[]{"id","name","age",
                                                   "country.name"});
        // END-EXAMPLE: jpacontainer.criteria.querymodification
    
        personTable.setPageLength(persons.size());
        layout.addComponent(personTable);
    }
}
