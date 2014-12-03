package com.vaadin.book.examples.datamodel;

import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BeanItemContainerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("select".equals(context))
            select(layout);
        else if ("addall".equals(context))
            addall(layout);
        else if ("dualbinding".equals(context))
            dualbinding(layout);
        else if ("beanupdate".equals(context))
            beanupdate(layout);
        else if ("nestedbean".equals(context))
            nestedbean(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: datamodel.container.beanitemcontainer.basic
    // Here is a bean
    public class Bean implements Serializable {
        private static final long serialVersionUID = -1520923107014804137L;

        String name;
        double energy; // Energy content in kJ/100g
        
        public Bean(String name, double energy) {
            this.name   = name;
            this.energy = energy;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public double getEnergy() {
            return energy;
        }
        
        public void setEnergy(double energy) {
            this.energy = energy;
        }
    }
    
    void basic(VerticalLayout layout) {
        // Create a container for such beans
        BeanItemContainer<Bean> beans =
            new BeanItemContainer<Bean>(Bean.class);
        
        // Add some beans to it
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));

        // Bind a table to it
        Table table = new Table("Beans of All Sorts", beans);
        layout.addComponent(table);
        // END-EXAMPLE: datamodel.container.beanitemcontainer.basic
        
        table.setPageLength(beans.size());
        table.setVisibleColumns(new Object[]{"name", "energy"});
    }
    
    void select(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.container.beanitemcontainer.select
        // Create a container for such beans
        BeanItemContainer<Bean> beans =
            new BeanItemContainer<Bean>(Bean.class);
        
        // Add some beans to it
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));

        // Bind a combo box to it
        ComboBox select = new ComboBox("Select one", beans);
        select.setNullSelectionAllowed(false);
        layout.addComponent(select);
        
        // Use the name property for the item caption
        select.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        select.setItemCaptionPropertyId("name");
        
        // Preselect some value
        select.setValue(beans.getIdByIndex(2));

        // Report selection
        final Label feedback = new Label("");
        layout.addComponent(feedback);
        select.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1162945655606583495L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                    Bean selected = (Bean) event.getProperty().getValue();
                    feedback.setValue("Selected " + selected.getName());
                }
            }
        });
        select.setImmediate(true);
        // END-EXAMPLE: datamodel.container.beanitemcontainer.select
    }

    void addall(VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.container.beanitemcontainer.addall
        // Have a collection with some beans
        ArrayList<Bean> beanlist = new ArrayList<Bean>();
        beanlist.add(new Bean("Mung bean",   1452.0));
        beanlist.add(new Bean("Chickpea",    686.0));
        beanlist.add(new Bean("Lentil",      1477.0));
        beanlist.add(new Bean("Common bean", 129.0));
        beanlist.add(new Bean("Soybean",     1866.0));
        
        // Create a container for the beans
        final BeanItemContainer<Bean> beans =
            new BeanItemContainer<Bean>(Bean.class);
        
        // Add them all
        beans.addAll(beanlist);

        // Bind a table to the container
        Table table = new Table("Beans of All Sorts", beans);
        layout.addComponent(table);
        
        // Have a button to empty and create some new items
        Button recreate = new Button("Recreate");
        recreate.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 6823630748713272361L;

            @Override
            public void buttonClick(ClickEvent event) {
                beans.removeAllItems();

                ArrayList<Bean> beanlist = new ArrayList<Bean>();
                beanlist.add(new Bean("Space bean",   42.0));
                beanlist.add(new Bean("Moon bean",    388.0));
                
                beans.addAll(beanlist);
            }
        });
        layout.addComponent(recreate);
        // END-EXAMPLE: datamodel.container.beanitemcontainer.addall
        
        table.setPageLength(beans.size());
        table.setVisibleColumns(new Object[]{"name", "energy"});
    }
    
    public static final String dualbindingDescription =
        "<h1>Binding a bean both to a Table and a Form</h1>"+
        "<p>Editing the <b>BeanItem</b> in the form fires value change events, "+
        "which are communicated to the other components bound to the same data source, in this case the <b>Table</b>.</p>";

    @SuppressWarnings("unchecked")
    void dualbinding(VerticalLayout vlayout) {
        // BEGIN-EXAMPLE: datamodel.container.beanitemcontainer.dualbinding
        // Create a container for beans
        final BeanItemContainer<Bean> beans =
            new BeanItemContainer<Bean>(Bean.class);
        
        // Add some beans to it
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));

        // A layout for the table and form
        HorizontalLayout layout = new HorizontalLayout();

        // Bind a table to it
        final Table table = new Table("Beans of All Sorts", beans);
        table.setVisibleColumns(new Object[]{"name", "energy"});
        table.setPageLength(7);
        layout.addComponent(table);
        
        // Create a form for editing a selected or new item.
        // It is invisible until actually used.
        final Form form = new Form();
        form.setCaption("Edit Item");
        form.setVisible(false);
        layout.addComponent(form);
        
        // When the user selects an item, show it in the form
        table.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1162945655606583495L;

            public void valueChange(ValueChangeEvent event) {
                // Close the form if the item is deselected
                if (event.getProperty().getValue() == null) {
                    form.setVisible(false);
                    return;
                }

                // Bind the form to the selected item
                form.setItemDataSource(beans.getItem(table.getValue()));
                form.setVisible(true);
            }
        });
        table.setSelectable(true);
        table.setImmediate(true);

        // Creates a new bean for editing in the form before adding
        // it to the table. Adding is handled after committing
        // the form.
        final Button newBean = new Button("New Bean");
        newBean.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -7340189561756261036L;

            public void buttonClick(ClickEvent event) {
                // Create a new bean and bind it to the form
                Bean bean = new Bean(null, 0.0);
                BeanItem<Bean> item = new BeanItem<Bean>(bean);
                form.setItemDataSource(item);

                // Make the form a bit nicer
                form.setVisibleItemProperties(
                        new Object[]{"name", "energy"});
                ((TextField)form.getField("name"))
                        .setNullRepresentation("");
                
                table.select(null);
                table.setEnabled(false);
                newBean.setEnabled(false);
                form.setVisible(true);
            }
        });

        // When OK button is clicked, commit the form to the bean
        Button submit = new Button("OK");
        submit.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 6823630748713272361L;

            public void buttonClick(ClickEvent event) {
                form.commit();
                form.setVisible(false); // and close it
                
                // New items have to be added to the container
                if (table.getValue() == null) {
                    BeanItem<Bean> item =
                        (BeanItem<Bean>) form.getItemDataSource();

                    // This creates a new BeanItem
                    beans.addBean(item.getBean());
                    
                    table.setEnabled(true);
                    newBean.setEnabled(true);
                }
            }
        });
        form.getFooter().addComponent(submit);

        Button cancel = new Button("Cancel");
        cancel.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -1749148888766063606L;

            public void buttonClick(ClickEvent event) {
                form.discard(); // Not really necessary
                form.setVisible(false); // and close it
                table.setEnabled(true);
                newBean.setEnabled(true);
            }
        });
        form.getFooter().addComponent(cancel);
        // END-EXAMPLE: datamodel.container.beanitemcontainer.dualbinding

        layout.setSpacing(true);
        vlayout.addComponent(layout);
        vlayout.addComponent(newBean);
    }

    void beanupdate(VerticalLayout vlayout) {
        // BEGIN-EXAMPLE: datamodel.container.beanitemcontainer.beanupdate
        // Create a container for beans
        final BeanItemContainer<Bean> beans =
            new BeanItemContainer<Bean>(Bean.class);
    }

    // BEGIN-EXAMPLE: datamodel.container.beanitemcontainer.nestedbean
    /** Bean to be nested */
    public class EqCoord implements Serializable {
        private static final long serialVersionUID = -3020840929725381667L;

        double rightAscension; /* In angle hours */
        double declination;    /* In degrees     */
        
        public EqCoord(double ra, double decl) {
            this.rightAscension = ra;
            this.declination    = decl;
        }
        
        public double getRightAscension() {
            return rightAscension;
        }
        public void setRightAscension(double rightAscension) {
            this.rightAscension = rightAscension;
        }

        public double getDeclination() {
            return declination;
        }
        public void setDeclination(double declination) {
            this.declination = declination;
        }
    }

    /** Bean containing a nested bean */
    public class Star implements Serializable {
        private static final long serialVersionUID = -312738461368736290L;

        String  name;
        EqCoord equatorial; /* Nested bean */
        
        public Star(String name, EqCoord equatorial) {
            this.name       = name;
            this.equatorial = equatorial;
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        /** Access to the nested bean. */
        public EqCoord getEquatorial() {
            return equatorial;
        }
        public void setEquatorial(EqCoord equatorial) {
            this.equatorial = equatorial;
        }
    }
    
    void nestedbean(VerticalLayout layout) {
        // Create a container for beans
        final BeanItemContainer<Star> stars =
            new BeanItemContainer<Star>(Star.class);
        
        // Declare the nested properties to be used in the container
        stars.addNestedContainerProperty("equatorial.rightAscension");
        stars.addNestedContainerProperty("equatorial.declination");
        
        // Add some items
        stars.addBean(new Star("Sirius",  new EqCoord(6.75, 16.71611)));
        stars.addBean(new Star("Polaris", new EqCoord(2.52, 89.26417)));
        
        // Here the nested bean is null
        stars.addBean(new Star("Vega", null));
        
        // Put them in a table
        Table table = new Table("Stars", stars);
        table.setColumnHeader("equatorial.rightAscension", "RA");
        table.setColumnHeader("equatorial.declination",    "Decl");
        table.setPageLength(table.size());
        
        // Have to set explicitly to hide the "equatorial" property
        table.setVisibleColumns(new Object[]{"name",
            "equatorial.rightAscension", "equatorial.declination"});
        
        layout.addComponent(table);
    }        
    // END-EXAMPLE: datamodel.container.beanitemcontainer.nestedbean
}
