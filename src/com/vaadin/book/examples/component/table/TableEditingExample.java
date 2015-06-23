package com.vaadin.book.examples.component.table;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.Description;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class TableEditingExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 9133568351109399673L;

    @Description(title = "Editable Table", value =
        "<p>You can make a table editable by setting <tt>setEditable(true)</tt>.</p>")
    public void editable(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.editable
        // Table with a component column in non-editable mode
        Table table = new Table("The Important People");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Born", Date.class, null);
        table.addContainerProperty("Alive", Boolean.class, null);
        
        // Insert this data
        Object people[][] = {{"Galileo",  1564, false},
                             {"Monnier",  1715, false},
                             {"Väisälä",  1891, false},
                             {"Oterma",   1915, false},
                             {"Valtaoja", 1951, true}};
        
        // Insert the data, transforming the year number to Date object
        for (int i=0; i<people.length; i++) {
            Object item[] = {people[i][0],
                new GregorianCalendar((Integer)people[i][1], 0, 1).getTime(),
                people[i][2]};
            table.addItem(item, new Integer(i));
        }
        table.setPageLength(table.size());
        
        // Set a custom field factory that overrides the default factory
        table.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = 8585461394836108250L;

            @Override
            public Field<?> createField(Container container, Object itemId,
                    Object propertyId, Component uiContext) {
                // Create fields by their class
                Class<?> cls = container.getType(propertyId);

                // Create a DateField with year resolution for dates
                if (cls.equals(Date.class)) {
                    DateField df = new DateField();
                    df.setResolution(Resolution.YEAR);
                    return df;
                }
                
                // Create a CheckBox for Boolean fields
                if (cls.equals(Boolean.class))
                    return new CheckBox();
                
                // Otherwise use the default field factory 
                return super.createField(container, itemId, propertyId,
                                         uiContext);
            }
        });
        
        // Put the table in editable mode
        table.setEditable(true);
        // END-EXAMPLE: component.table.editable.editable
        table.setSelectable(true);
        layout.addComponent(table);

        // Allow switching to non-editable mode
        final CheckBox editable = new CheckBox("Table is editable", true);
        editable.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 6291942958587745232L;

            public void valueChange(ValueChangeEvent event) {
                table.setEditable((Boolean) editable.getValue());
            }
        });
        editable.setImmediate(true);
        layout.addComponent(editable);        
    }

    @SuppressWarnings("unchecked")
    @Description(title = "Height of Components in Editable Table", value =
        "<p>TextFields components are normally a bit lower in Table than usually, "+
        "as it makes the table more compact and, more importantly, prevents change of "+
        "line height when switching between editable and non-editable mode. "+
        "Some components do not have similar "+
        "adjustments, which can make the table rows uneven.</p>"+
        "<p><b>Solution 1:</b> Override the CSS rules that make TextFields low in Table. (Done in the example below)</p>"+
        "<p><b>Solution 2:</b> Use CSS to make the other components (DateField, ComboBox) the same height as TextField</p>")
    public void editableheights(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.editableheights
        // Have a container with some data
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("birthday", Date.class, null);
        container.addContainerProperty("nationality", String.class, null);
        container.addContainerProperty("name", String.class, null);
        
        // Some example data
        Object people[][] = {
            {new GregorianCalendar(1564, 0, 0).getTime(), "Italian", "Galileo"},
            {new GregorianCalendar(1715, 0, 0).getTime(), "French", "Monnier"},
            {new GregorianCalendar(1891, 0, 0).getTime(), "Finnish", "Väisälä"},
            {new GregorianCalendar(1915, 0, 0).getTime(), "Finnish", "Oterma"},
            {new GregorianCalendar(1951, 0, 0).getTime(), "Finnish", "Valtaoja"}};
        
        // Insert the data
        for (int i=0; i<people.length; i++) {
            Item item = container.addItem(new Integer(i));
            item.getItemProperty("birthday").setValue(people[i][0]);
            item.getItemProperty("nationality").setValue(people[i][1]);
            item.getItemProperty("name").setValue(people[i][2]);
        }

        // Have the table
        Table table = new Table("Edible Table", container);
        table.setPageLength(table.size());
        
        // Set a custom field factory that overrides the default factory
        table.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = -3301080798105311480L;

            @Override
            public Field<?> createField(Container container, Object itemId,
                    Object propertyId, Component uiContext) {
                if ("nationality".equals(propertyId)) {
                    ComboBox select = new ComboBox();
                    select.addItem("Italian");
                    select.addItem("French");
                    select.addItem("Finnish");
                    select.setNullSelectionAllowed(false);
                    return select;
                }
                
                return super.createField(container, itemId, propertyId, uiContext);
            }
        });
        table.setEditable(true);

        // Allow switching to non-editable mode
        CheckBox editable = new CheckBox("Table is editable", true);
        editable.addValueChangeListener(event -> // Java 8
            table.setEditable((Boolean) editable.getValue()));
        editable.setImmediate(true);
        // END-EXAMPLE: component.table.editable.editableheights

        table.addStyleName("editableexample");

        layout.addComponent(table);
        layout.addComponent(editable);        
    }

    public void combobox(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.combobox
        final Table table = new Table("My Table");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Classification", String.class, null);
        table.addContainerProperty("Population", String.class, null);
        for (int col=3; col<15; col++)
            table.addContainerProperty("Col "+col, String.class, null);
        
        class MyFactory implements TableFieldFactory {
            private static final long serialVersionUID = 3024342074320948062L;

            public Field<?> createField(Container container, Object itemId,
                                     Object propertyId, Component uiContext) {
                ComboBox box = new ComboBox();
                String[] items = null;
                if ("Name".equals(propertyId))
                    items = new String[] {"Mercury", "Venus", "Earth", "Mars",
                            "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto",
                            "Ceres", "Eris"};
                else if ("Classification".equals(propertyId))
                    items = new String[] {"Planet", "Minor Planet", "Plutoid",
                        "Dwarf Planet"};
                else if ("Population".equals(propertyId))
                    items = new String[] {"Nobody", "People", "Women", "Men",
                        "Martians", "Monoliths", "Plutonians"};
                else
                    return new TextField();
                
                for (int pl=0; pl<items.length; pl++)
                    box.addItem(items[pl]);
                return box;
            }
        }
        table.setTableFieldFactory(new MyFactory());
        
        String bodies[][] = {
                {"Mercury", "Planet", "Nobody"},
                {"Venus", "Planet", "Women"},
                {"Earth", "Planet", "People"},
                {"Mars", "Planet", "Men"},
                {"Ceres", "Minor Planet", "Nobody"},
                {"Jupiter", "Planet", "Monoliths"},
                {"Saturn", "Planet", "Nobody"},
                {"Uranus", "Planet", "Nobody"},
                {"Neptune", "Planet", "Nobody"},
                {"Pluto", "Plutoid", "Plutonians"},
                {"Eris", "Minor Planet", "Plutonians"}};
        for (int body=0; body<bodies.length; body++) {
            String item[] = new String[15];
            for (int col=0; col<item.length; col++)
                if (col<bodies[body].length)
                    item[col] = bodies[body][col];
                else
                    item[col] = "Col " + col;
            table.addItem(item, item[0]);
        }

        layout.addComponent(table);

        final CheckBox editable = new CheckBox("Editable", true);
        editable.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -7187332079691427001L;

            public void valueChange(ValueChangeEvent event) {
                table.setEditable((Boolean) editable.getValue());
            }
        });
        editable.setImmediate(true);
        layout.addComponent(editable);
        
        table.setEditable(true);
        
        // END-EXAMPLE: component.table.editable.combobox
    }

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

    public void buffering(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.buffering
        // The data model + some data
        BeanItemContainer<Bean> beans =
                new BeanItemContainer<Bean>(Bean.class);
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));
        beans.addItem(new Bean("Java Bean",   0.0));
        
        // This is the buffered editable table
        final Table editable = new Table("Editable");
        editable.setEditable(true);
        editable.setBuffered(true);
        editable.setContainerDataSource(beans);
        
        // Set all fields as immediate
        editable.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = 3552375110999556704L;

            @Override
             public Field<?> createField(Container container, Object itemId,
                                      Object propertyId, Component uiContext) {
                AbstractField<?> field = (AbstractField<?>)
                    super.createField(container, itemId,
                                      propertyId, uiContext);
                field.setImmediate(true);
                field.setBuffered(true);
                return field;
             } 
        });
        
        final Button save = new Button("Save");
        save.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 2279611560864466987L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    save.setComponentError(null); // Clear
                    editable.commit();
                } catch (InvalidValueException e) {
                    save.setComponentError(new UserError("Not valid"));
                }
            }
        });
        
        // Read-only table
        Table rotable = new Table("Rotable");
        rotable.setContainerDataSource(beans);
        // END-EXAMPLE: component.table.editable.buffering
        
        HorizontalLayout hor = new HorizontalLayout();
        hor.addComponent(editable);
        hor.addComponent(rotable);
        hor.setSpacing(true);
        layout.addComponent(hor);
        layout.addComponent(save);
    }

    public void spreadsheet(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.spreadsheet
        // The data model + some data
        final BeanItemContainer<Bean> beans =
                new BeanItemContainer<Bean>(Bean.class);
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));
        beans.addItem(new Bean("Java Bean",   0.0));

        // The table to edit
        final Table table = new Table();
        table.setPageLength(table.size());

        // The table needs to be in editable mode
        table.setEditable(true);

        // This is needed for storing back-references
        class ItemPropertyId {
            Object itemId;
            Object propertyId;
            
            public ItemPropertyId(Object itemId, Object propertyId) {
                this.itemId = itemId;
                this.propertyId = propertyId;
            }
            
            public Object getItemId() {
                return itemId;
            }
            
            public Object getPropertyId() {
                return propertyId;
            }
        }

        // Map to find a field component by its item ID and property ID
        final HashMap<Object,HashMap<Object,Field<?>>> fields = new HashMap<Object,HashMap<Object,Field<?>>>();
        
        // Map to find the item ID of a field
        final HashMap<Field<?>,Object> itemIds = new HashMap<Field<?>,Object>(); 

        table.setTableFieldFactory(new TableFieldFactory() {
            private static final long serialVersionUID = -5741977060384915110L;

            public Field<?> createField(Container container, final Object itemId,
                    final Object propertyId, Component uiContext) {
                final TextField tf = new TextField();
                tf.setData(new ItemPropertyId(itemId, propertyId));
                
                // Needed for the generated column
                tf.setImmediate(true);

                // Manage the field in the field storage
                HashMap<Object,Field<?>> itemMap = fields.get(itemId);
                if (itemMap == null) {
                    itemMap = new HashMap<Object,Field<?>>();
                    fields.put(itemId, itemMap);
                }
                itemMap.put(propertyId, tf);
                
                itemIds.put(tf, itemId);
                
                tf.setReadOnly(true);
                tf.addFocusListener(new FocusListener() {
                    private static final long serialVersionUID = 1006388127259206641L;

                    public void focus(FocusEvent event) {
                        // Make the entire item editable
                        HashMap<Object,Field<?>> itemMap = fields.get(itemId);
                        for (Field<?> f: itemMap.values())
                            f.setReadOnly(false);
                        
                        table.select(itemId);
                    }
                });
                tf.addBlurListener(new BlurListener() {
                    private static final long serialVersionUID = -4497552765206819985L;

                    public void blur(BlurEvent event) {
                        // Make the entire item read-only
                        HashMap<Object,Field<?>> itemMap = fields.get(itemId);
                        for (Field<?> f: itemMap.values())
                            f.setReadOnly(true);
                    }
                });
                
                return tf;
            }
        });
        
        table.setContainerDataSource(beans);
        
        // Add a generated column
        table.addGeneratedColumn("kcal", new ColumnGenerator() {
            private static final long serialVersionUID = 3104134889298321970L;

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                double value = (Double) beans.getItem(itemId).getItemProperty("energy").getValue();
                return value * 0.000239005736;
            }
        });
        
        table.setVisibleColumns("name","energy", "kcal");

        // Keyboard navigation
        class KbdHandler implements Handler {
            private static final long serialVersionUID = -2993496725114954915L;

            Action tab_next = new ShortcutAction("Shift",
                    ShortcutAction.KeyCode.TAB, null);
            Action tab_prev = new ShortcutAction("Shift+Tab",
                    ShortcutAction.KeyCode.TAB,
                    new int[] {ShortcutAction.ModifierKey.SHIFT});
            Action cur_down = new ShortcutAction("Down",
                    ShortcutAction.KeyCode.ARROW_DOWN, null);
            Action cur_up   = new ShortcutAction("Up",
                    ShortcutAction.KeyCode.ARROW_UP,   null);
            Action enter    = new ShortcutAction("Enter",
                    ShortcutAction.KeyCode.ENTER,      null);
            Action add      = new ShortcutAction("Add Below",
                    ShortcutAction.KeyCode.A,          null);
            Action delete   = new ShortcutAction("Delete",
                    ShortcutAction.KeyCode.DELETE,     null);

            public Action[] getActions(Object target, Object sender) {
                return new Action[] {tab_next, tab_prev, cur_down,
                                     cur_up, enter, add, delete};
            }

            public void handleAction(Action action, Object sender,
                                     Object target) {
                if (target instanceof TextField) {
                    TextField tf = (TextField) target;
                    ItemPropertyId ipId = (ItemPropertyId) tf.getData();
                    
                    // On enter, close the edit mode
                    if (action == enter) {
                        // Make the entire item read-only
                        HashMap<Object,Field<?>> itemMap = fields.get(ipId.getItemId());
                        for (Field<?> f: itemMap.values())
                            f.setReadOnly(true);
                        table.select(ipId.getItemId());
                        table.focus();
                        
                        // Updates the generated column
                        table.refreshRowCache();
                        return;
                    }
                    
                    Object propertyId = ipId.getPropertyId();
                    
                    // Find the index of the property
                    Object cols[] = table.getVisibleColumns();
                    int pidIndex = 0;
                    for (int i=0; i<cols.length; i++)
                        if (cols[i].equals(propertyId))
                            pidIndex = i;
                    
                    Object newItemId     = null;
                    Object newPropertyId = null;
                    
                    // Move according to keypress
                    if (action == cur_down)
                        newItemId = beans.nextItemId(ipId.getItemId());
                    else if (action == cur_up)
                        newItemId = beans.prevItemId(ipId.getItemId());
                    else if (action == tab_next)
                        newPropertyId = cols[Math.min(pidIndex+1, cols.length-1)];
                    else if (action == tab_prev)
                        newPropertyId = cols[Math.max(pidIndex-1, 0)];

                    // If tried to go past first or last, just stay there
                    if (newItemId == null)
                        newItemId = ipId.getItemId();
                    if (newPropertyId == null)
                        newPropertyId = ipId.getPropertyId();
                    
                    // On enter, just stay where you were. If we did
                    // not catch the enter action, the focus would be
                    // moved to wrong place.

                    Field<?> newField = fields.get(newItemId).get(newPropertyId);
                    if (newField != null)
                        newField.focus();
                } else if (target instanceof Table) {
                    Table table = (Table) target;
                    Object selected = table.getValue();

                    if (selected == null)
                        return;
                    
                    if (action == enter) {
                        // Make the entire item editable
                        HashMap<Object,Field<?>> itemMap = fields.get(selected);
                        for (Field<?> f: itemMap.values())
                            f.setReadOnly(false);
                        
                        // Focus the first column
                        itemMap.get(table.getVisibleColumns()[0]).focus();
                    } else if (action == add) {
                        // TODO
                    } else if (action == delete) {
                        Item item = table.getItem(selected);
                        if (item != null && item instanceof BeanItem<?>) {
                            // Change selection
                            Object newselected = table.nextItemId(selected);
                            if (newselected == null)
                                newselected = table.prevItemId(selected);
                            table.select(newselected);
                            table.focus();
                            
                            // Remove the item from the container
                            beans.removeItem(selected);
                            
                            // Remove from the map
                            // TODO
                        }
                    }
                }
            }
        }

        // Panel that handles the keyboard navigation
        Panel navigator = new Panel("The \"Spreadsheet\"");
        navigator.addStyleName(Reindeer.PANEL_LIGHT);
        Layout navigatorContent = new VerticalLayout();
        navigator.setContent(navigatorContent);
        navigatorContent.addComponent(new Label("Press" +
                "<ul>" +
                "  <li><b>Enter</b> to edit/accept an item,</li>" +
                "  <li><b>Tab</b> and <b>Shift+Tab</b> to navigate fields, and</li>"+
                "  <li><b>Up</b> and <b>Down</b> to move to previous/next item.</li>" +
                "</ul>", ContentMode.HTML));
        navigatorContent.addComponent(table);
        ((VerticalLayout) navigator.getContent()).setExpandRatio(table, 1.0f);
        navigator.addActionHandler(new KbdHandler());

        // Use selecting the row to be edited
        table.setSelectable(true);
        table.select(table.getItemIds().toArray()[0]);
        table.focus();
        // END-EXAMPLE: component.table.editable.spreadsheet

        layout.setSpacing(true);
        layout.addComponent(navigator);
    }

    public void adding(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.adding
        // The data model + some data
        BeanItemContainer<Bean> beans =
                new BeanItemContainer<Bean>(Bean.class);
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));
        beans.addItem(new Bean("Java Bean",   0.0));

        // The table to edit
        final Table table = new Table(null, beans);
        table.setWidth("500px");
        table.setPageLength(10);
        table.setEditable(true);
        table.setVisibleColumns("name", "energy");
        
        table.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = -5125713307352302436L;

            @Override
            public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
                Field<?> field = super.createField(container, itemId, propertyId, uiContext);
                if (field instanceof TextField) {
                    ((TextField) field).setNullRepresentation("");
                    field.setWidth("100%");
                }
                return field;
            }
        });
        
        // Adding new items
        Button add = new Button("Add New Item", e -> // Java 8
            beans.addBean(new Bean(null, 0.0)));
        
        layout.addComponents(table, add);
        // END-EXAMPLE: component.table.editable.adding

    }

    @Description(value = "This is just a test to see if edits are preserved when the table is scrolled")
    public void longtable(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.longtable
        // A lot of example data
        IndexedContainer container = TableExample.generateContent();

        // The table to edit
        Table table = new Table(null, container) {
            private static final long serialVersionUID = -2812466098538751808L;

            public void refreshRowCache() {
                super.refreshRowCache();
                layout.addComponent(new Label("Refreshed row cache"));
            }
        };
        table.setWidth("500px");
        table.setPageLength(8);
        table.setEditable(true);
        
        layout.addComponents(table);
        // END-EXAMPLE: component.table.editable.longtable
    }
}
