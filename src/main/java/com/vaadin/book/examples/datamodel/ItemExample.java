package com.vaadin.book.examples.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ItemExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("propertysetitem".equals(context))
            propertysetitem(layout);
        else if ("beanitem-basic".equals(context))
            beanitem_basic(layout);
        else if ("nestedbean".equals(context))
            nestedbean(layout);
        else if ("doublebinding".equals(context))
            doublebinding(layout);
        else if ("implementing".equals(context))
            implementing(layout);
        else if ("valuechangenotifier".equals(context))
            valuechangenotifier(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.items.basic
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));
        
        // Bind it to a component
        Form form = new Form();
        form.setItemDataSource(item);
        // END-EXAMPLE: datamodel.items.basic

        layout.addComponent(form);
    }
    
    void propertysetitem(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.items.propertysetitem
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));
        
        // Bind it to a component
        Form form = new Form();
        form.setItemDataSource(item);
        // END-EXAMPLE: datamodel.items.propertysetitem

        layout.addComponent(form);
    }

    // BEGIN-EXAMPLE: datamodel.items.beanitem.beanitem-basic
    // Here is a bean (or more exactly a POJO)
    public class Person implements Serializable {
        private static final long serialVersionUID = 5199354319677070624L;

        String name;
        int    born;
        
        public Person(String name, int age) {
            setName(name);
            setBorn(age);
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Integer getBorn() {
            return born;
        }
        
        public void setBorn(Integer age) {
            this.born = age.intValue();
        }
    }

    void beanitem_basic(final VerticalLayout layout) {
        // Create an instance of the bean
        Person bean = new Person("Douglas Adams", 49);
        
        // Wrap it in a BeanItem
        BeanItem<Person> item = new BeanItem<Person>(bean);
        
        // Bind it to a component
        Form form = new Form();
        form.setItemDataSource(item);
        // END-EXAMPLE: datamodel.items.beanitem.beanitem-basic

        layout.addComponent(form);
    }

    // BEGIN-EXAMPLE: datamodel.items.beanitem.nestedbean
    // Here is a bean with two nested beans
    public class Planet implements Serializable {
        private static final long serialVersionUID = -7129883308078972363L;

        String name;
        Person discoverer;
        
        public Planet(String name, Person discoverer) {
            this.name = name;
            this.discoverer = discoverer;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public Person getDiscoverer() {
            return discoverer;
        }
        public void setDiscoverer(Person discoverer) {
            this.discoverer = discoverer;
        }
    }

    void nestedbean(final VerticalLayout layout) {
        // Create an instance of the bean
        Planet planet = new Planet("Uranus",
                            new Person("William Herschel", 1738));
        
        // Wrap it in a BeanItem and hide the nested bean property
        BeanItem<Planet> item = new BeanItem<Planet>(planet,
                new String[]{"name"});
        
        // Bind the nested properties.
        // Use NestedMethodProperty to bind using dot notation.
        item.addItemProperty("discoverername",
            new NestedMethodProperty<String>(planet, "discoverer.name"));
        
        // The other way is to use regular MethodProperty.
        item.addItemProperty("discovererborn",
            new MethodProperty<Person>(planet.getDiscoverer(),
                                       "born"));
        
        // Bind it to a component
        Form form = new Form();
        form.setItemDataSource(item);
        
        // Nicer captions
        form.getField("discoverername").setCaption("Discoverer");
        form.getField("discovererborn").setCaption("Born");
        // END-EXAMPLE: datamodel.items.beanitem.nestedbean

        layout.addComponent(form);
    }
    
    public final static String doublebindingDescription =
        "<h1>Binding Multiple Item Editors/Viewers to the Same Bean</h1>" +
        "<p>When two or more item editors or viewers are bound to a single <b>BeanItem</b>, " +
        "any changes made in an editor are propagated to viewers.</p>"+
        "<p>You should avoid binding to one bean through two different <b>BeanItem</b>s, " +
        "as then value change events are not propagated through the <b>Item</b> to all its <b>Viewer</b>s. " +
        "However, if you do so, you can call the <tt>discard()</tt> method to read " +
        "the data from the bean to the viewer/editor.</p>" +
        "<div style='text-align: center'><img src='"+BookExamplesUI.APPCONTEXT + "/images/datamodel/beanitem-doublebinding-lo.png'/></div>";
    
    void doublebinding(VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.items.beanitem.doublebinding
        // Create an instance of the bean
        Person bean = new Person("William Herschel", 1738);
        
        // Wrap it in a BeanItem
        BeanItem<Person> item1 = new BeanItem<Person>(bean);

        // Read-write input form with buffering
        final Form form = new Form();
        form.setItemDataSource(item1);
        form.setCaption("Input Form");
        form.setBuffered(true);

        // Button that calls commit() for the form when clicked
        Button ok = new Button("OK", new Button.ClickListener() {
            private static final long serialVersionUID = 6555662458202314974L;

            @Override
            public void buttonClick(ClickEvent event) {
                form.commit();
            }
        });
        form.getFooter().addComponent(ok);
        
        // Button that calls discard() for the form when clicked
        Button reset = new Button("Reset", new Button.ClickListener() {
            private static final long serialVersionUID = -4487821750311571748L;

            @Override
            public void buttonClick(ClickEvent event) {
                form.discard();
            }
        });
        form.getFooter().addComponent(reset);
        
        // Read-only form bound to the same BeanItem
        Form reader1 = new Form();
        reader1.setItemDataSource(item1);
        reader1.setCaption("Bound to the same BeanItem");
        reader1.setReadOnly(true);

        // Wrap the bean in a second BeanItem
        BeanItem<Person> item2 = new BeanItem<Person>(bean);

        // Read-only form to display the same data
        final Form reader2 = new Form();
        reader2.setItemDataSource(item2);
        reader2.setCaption("Bound through different BeanItem");
        reader2.setReadOnly(true);

        // Button that calls discard() for the form when clicked
        Button readreset = new Button("Read from Bean", new ClickListener() {
            private static final long serialVersionUID = 321466675024267156L;

            @Override
            public void buttonClick(ClickEvent event) {
                reader2.discard();
            }
        });
        reader2.getFooter().addComponent(readreset);
        
        HorizontalLayout h = new HorizontalLayout();
        h.addComponent(form);
        h.addComponent(reader1);
        h.addComponent(reader2);
        // END-EXAMPLE: datamodel.items.beanitem.doublebinding
        
        h.setSpacing(true);
        layout.addComponent(h);
    }

    // BEGIN-EXAMPLE: datamodel.items.implementing
    /** Base the custom item on this */
    public class MyBean implements Serializable {
        private static final long serialVersionUID = -4091252572002768955L;

        String name = "";
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    /** Make the bean a Vaadin data model item. */
    public class MyItem extends MyBean implements Item {
        private static final long serialVersionUID = 6717813701011029695L;

        // Store the property access wrappers
        HashMap<String,MethodProperty<Object>> properties =
            new HashMap<String,MethodProperty<Object>>();

        public MyItem() {
            properties.put("name", new MethodProperty<Object>(this, "name"));
        }

        // Make it possible to modify the property directly
        // through the bean interface so that listeners are
        // notified about value changes.
        @Override
        public void setName(String name) {
            super.setName(name);
            
            // Notify about the value change
            properties.get("name").fireValueChange();
        }
        
        @Override
        public Property<?> getItemProperty(Object id) {
            return properties.get("name");
        }

        @Override
        public Collection<?> getItemPropertyIds() {
            return properties.keySet();
        }

        @Override
        public boolean addItemProperty(Object id, Property property)
                throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeItemProperty(Object id)
                throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
    
    void implementing(VerticalLayout layout) {
        // Create an instance of the bean
        final MyItem myitem = new MyItem();
        
        // Read-write input form with buffering
        final Form form = new Form();
        form.setItemDataSource(myitem);
        form.setCaption("Item Editor");
        form.setBuffered(true);

        // Button that calls commit() for the form when clicked
        Button ok = new Button("OK", new ClickListener() {
            private static final long serialVersionUID = 8857178555919281305L;

            @Override
            public void buttonClick(ClickEvent event) {
                form.commit();
            }
        });
        form.getFooter().addComponent(ok);
        
        // Button that calls discard() for the form when clicked
        Button reset = new Button("Reset", new ClickListener() {
            private static final long serialVersionUID = 2553051168341568987L;

            @Override
            public void buttonClick(ClickEvent event) {
                form.discard();
            }
        });
        form.getFooter().addComponent(reset);
        
        // Read-only form bound to the same BeanItem
        Form viewer = new Form();
        viewer.setItemDataSource(myitem);
        viewer.setCaption("Data Model Viewer");
        viewer.setReadOnly(true);

        // Modify the object past the item interface
        Button modify = new Button("Modify bean directly");
        modify.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 6359038605560050564L;

            @Override
            public void buttonClick(ClickEvent event) {
                myitem.setName("Modified property");
            }
        });
        
        HorizontalLayout h = new HorizontalLayout();
        h.addComponent(form);
        h.addComponent(viewer);
        layout.addComponent(h);
        layout.addComponent(modify);
        // END-EXAMPLE: datamodel.items.implementing
        
        h.setSpacing(true);
    }

    public final static String valuechangenotifierDescription =
        "<h1>Notifying of Direct Changes to a Bean</h1>" +
        "<p>This is an experimental study of how you can make notifications regarding direct changes to a bean to Vaadin components.</p>" +
        "<p>Let us have a typical case where you have a bean or beans bound to Vaadin components using <b>BeanItem</b> or <b>Bean(Item)Container</b>.</p>" +
        "<p>Normally:</p>" +
        "<ul>" +
        "<li>if you modify a bean directly, for example with a setter, the Vaadin data model and the components won't know about the modification.</li>" +
        "<li>if you modify the bean through the <b>BeanItem</b> API, notifications to listening Vaadin components will be sent properly and the " +
        "changes are displayed in the UI.</li>" +
        "</ul>" +
        "<p>The following shows how to implement a notification mechanism to a bean.</p>" +
        "<p>When you click the button, the following calls are made directly to the bean:</p>" +
        "<pre>    thing.setName(\"Modified property\");\n" +
        "    thing.setAge(42);</pre>";
    
    // BEGIN-EXAMPLE: datamodel.items.beanitem.valuechangenotifier
    /** Base the custom item on this */
    public class Thing implements Serializable {
        private static final long serialVersionUID = -4091252572002768955L;

        String name = "";
        int    age  = 0;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
    }

    /** Manages the notifications. Could be integrated in the bean
     *  extension below, but having it separate allows reuse. 
     */
    public class Notifier implements Serializable {
        private static final long serialVersionUID = 8562676299233652968L;
        
        public abstract class ValueChangeListener implements Serializable {
            private static final long serialVersionUID = -5079132299868615897L;

            public abstract void valueChange(Object propertyId);
        }
        
        ArrayList<ValueChangeListener> listeners =
            new ArrayList<ValueChangeListener>();
        
        
        /** Notifies about value change in a property. */
        void fireValueChange(Object propertyId) {
            for (ValueChangeListener listener: listeners)
                listener.valueChange(propertyId);
        }

        public void addListener(ValueChangeListener listener) {
            listeners.add(listener);
        }
    }

    /** Extend the bean to add change notifications.
     *  This could as well be integrated in the bean. */
    public class ExtendedThing extends Thing {
        private static final long serialVersionUID = 6717813701011029695L;
        
        Notifier notifier = new Notifier();

        @Override
        public void setName(String name) {
            super.setName(name);
            notifier.fireValueChange("name");
        }
        
        @Override
        public void setAge(int age) {
            super.setAge(age);
            notifier.fireValueChange("age");
        }

        public Notifier notifier() {
            return notifier;
        }
    }
    
    void valuechangenotifier(VerticalLayout layout) {
        // Create an instance of the bean
        final ExtendedThing thing = new ExtendedThing();

        // Wrap it
        final BeanItem<ExtendedThing> thingItem =
            new BeanItem<ExtendedThing>(thing);
        
        // Relay value changes in the bean to the wrapper. Adding is a bit
        // odd here because the classes are inner classes.
        thing.notifier().addListener(thing.notifier().new ValueChangeListener() {
            private static final long serialVersionUID = 6324285394949966573L;

            @Override
            public void valueChange(Object propertyId) {
                MethodProperty<?> prop = (MethodProperty<?>)
                        thingItem.getItemProperty(propertyId);
                prop.fireValueChange();
            }
        });
        
        // Read-only form bound to the same BeanItem
        Form viewer = new Form();
        viewer.setItemDataSource(thingItem, Arrays.asList("name", "age"));
        viewer.setCaption("Data Model Viewer");
        viewer.setReadOnly(true);

        // Modify the object past the item interface
        Button modify = new Button("Modify bean directly");
        modify.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 6359038605560050564L;

            @Override
            public void buttonClick(ClickEvent event) {
                thing.setName("Modified property");
                thing.setAge(42);
            }
        });
        
        layout.addComponent(viewer);
        layout.addComponent(modify);
        // END-EXAMPLE: datamodel.items.beanitem.valuechangenotifier
        
        layout.setSpacing(true);
    }
}
