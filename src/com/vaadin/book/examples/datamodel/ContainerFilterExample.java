package com.vaadin.book.examples.datamodel;

import java.util.regex.PatternSyntaxException;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class ContainerFilterExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("like".equals(context))
            like(layout);
        else if ("tree".equals(context))
            tree(layout);
        else if ("custom".equals(context))
            custom(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // Example copied from TextChangeEventsExample
        // BEGIN-EXAMPLE: datamodel.container.filter.basic
        // Text field for inputting a filter
        final TextField tf = new TextField("Name Filter");
        tf.focus();
        layout.addComponent(tf);

        // Create a table with some example content
        //final Table table = new Table("Filtered Table");
        final Table table = new Table("Filtered List");
        table.setMultiSelect(true);
        table.setContainerDataSource(TableExample.generateContent());
        layout.addComponent(table);

        // Filter table according to typed input
        tf.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1048639156493298177L;
            
            SimpleStringFilter filter = null;

            public void textChange(TextChangeEvent event) {
                Filterable f = (Filterable)
                    table.getContainerDataSource();
                
                // Remove old filter
                if (filter != null)
                    f.removeContainerFilter(filter);
                
                // Set new filter for the "Name" column
                filter = new SimpleStringFilter("name", event.getText(),
                                                true, false);
                f.addContainerFilter(filter);
            }
        });
        // END-EXAMPLE: datamodel.container.filter.basic
    }

    void like(final VerticalLayout layout) {
        // Example copied from TextChangeEventsExample
        // BEGIN-EXAMPLE: datamodel.container.filter.like
        // Text field for inputting a filter
        final TextField tf = new TextField("Suffix Filter");
        tf.focus();
        layout.addComponent(tf);

        // Create a table with some example content
        //final Table table = new Table("Filtered Table");
        final Table table = new Table("Filtered List");
        table.setMultiSelect(true);
        table.setContainerDataSource(TableExample.generateContent());
        layout.addComponent(table);

        // Filter table according to typed input
        tf.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1048639156493298177L;
            
            Filter filter = null;

            public void textChange(TextChangeEvent event) {
                Filterable f = (Filterable)
                    table.getContainerDataSource();
                
                // Remove old filter
                if (filter != null)
                    f.removeContainerFilter(filter);
                
                // Set new filter for the "Name" column
                filter = new Like("name", "%"+event.getText());
                f.addContainerFilter(filter);
            }
        });
        // END-EXAMPLE: datamodel.container.filter.like

        // layout.addComponent(form);
    }

    void tree(final VerticalLayout layout) {
        // Example copied from TextChangeEventsExample
        // BEGIN-EXAMPLE: datamodel.container.filter.tree
        // Text field for inputting a filter
        final TextField tf = new TextField("Name Filter");
        tf.focus();
        layout.addComponent(tf);

        // Create a tree with some example content. The tree node
        // caption comes from the "caption" property.
        final Tree tree = new Tree("Filtered Tree");
        tree.setContainerDataSource(TreeExample.createTreeContent());
        tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        tree.setItemCaptionPropertyId("caption");
        for (Object itemId: tree.rootItemIds())
            tree.expandItemsRecursively(itemId);
        layout.addComponent(tree);

        // Filter table according to typed input
        tf.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1048639156493298177L;
            
            SimpleStringFilter filter = null;

            public void textChange(TextChangeEvent event) {
                Filterable f = (Filterable)
                    tree.getContainerDataSource();
                
                // Remove old filter
                if (filter != null)
                    f.removeContainerFilter(filter);
                
                // Set new filter for the "caption" property
                filter = new SimpleStringFilter("caption", event.getText(),
                                                true, false);
                f.addContainerFilter(filter);
            }
        });
        // END-EXAMPLE: datamodel.container.filter.tree

        // layout.addComponent(form);
    }

    void custom(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.container.filter.custom
        class MyCustomFilter implements Container.Filter {
            private static final long serialVersionUID = 6606181786787976311L;
            
            protected String propertyId;
            protected String regex;
            protected Label  status;
            
            public MyCustomFilter(String propertyId, String regex, Label status) {
                this.propertyId = propertyId;
                this.regex      = regex;
                this.status     = status;
            }

            /** Apply the filter on an item to check if it passes. */
            @Override
            public boolean passesFilter(Object itemId, Item item)
                    throws UnsupportedOperationException {
                // Acquire the relevant property from the item object
                Property<?> p = item.getItemProperty(propertyId);
                
                // Should always check validity
                if (p == null || !p.getType().equals(String.class))
                    return false;
                String value = (String) p.getValue();
                
                // Pass all if regex not given
                if (regex.isEmpty()) {
                    status.setValue("Empty filter");
                    return true;
                }
                
                // The actual filter logic + error handling
                try {
                    boolean result = value.matches(regex);
                    status.setValue(""); // OK
                    return result;
                } catch (PatternSyntaxException e) {
                    status.setValue("Invalid pattern");
                    return false;
                }
            }

            /** Tells if this filter works on the given property. */
            @Override
            public boolean appliesToProperty(Object propertyId) {
                return propertyId != null &&
                       propertyId.equals(this.propertyId);
            }
        }
        
        // Text field for inputting a filter
        final TextField tf = new TextField("My Own Filter");
        tf.setValue(".*(Ada|Love).*");
        tf.focus();

        // Create a filterable container with some example content
        final IndexedContainer c = TableExample.generateContent();

        HorizontalLayout filterRow = new HorizontalLayout();
        filterRow.setSpacing(true);
        layout.addComponent(filterRow);

        Button apply = new Button("Apply Filter");
        filterRow.addComponent(tf);
        filterRow.addComponent(apply);
        filterRow.setComponentAlignment(apply, Alignment.BOTTOM_LEFT);
        
        final Label status = new Label("");
        filterRow.addComponent(status);
        filterRow.setComponentAlignment(status, Alignment.BOTTOM_LEFT);
        
        // Filter table according to typed input
        apply.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1048639156493298177L;
            
            MyCustomFilter filter = null;

            @Override
            public void buttonClick(ClickEvent event) {
                // Remove old filter
                if (filter != null)
                    c.removeContainerFilter(filter);
                
                // Set new filter for the "Name" column
                filter = new MyCustomFilter("name",
                        (String) tf.getValue(), status);
                c.addContainerFilter(filter);
            }
        });

        // Create a table that uses the filtered container
        Table table = new Table("Filtered Table");
        table.setContainerDataSource(c);
        layout.addComponent(table);
        // END-EXAMPLE: datamodel.container.filter.custom
        
        layout.setSpacing(true);
    }
}
