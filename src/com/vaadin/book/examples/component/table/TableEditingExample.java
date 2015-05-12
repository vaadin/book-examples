package com.vaadin.book.examples.component.table;

import java.io.Serializable;
import java.util.HashMap;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.Description;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
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

    public class Bean implements Serializable {
        private static final long serialVersionUID = -1520923107014804137L;

        Integer id;
        String name;
        double energy; // Energy content in kJ/100g
        
        public Bean(Integer id, String name, double energy) {
            this.id = id;
            this.name   = name;
            this.energy = energy;
        }
        
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
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

    public void spreadsheet(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.editable.spreadsheet
        // The data model + some data
        final BeanItemContainer<Bean> beans =
                new BeanItemContainer<Bean>(Bean.class);
        beans.addBean(new Bean(beans.size(), "Mung bean",   1452.0));
        beans.addBean(new Bean(beans.size(), "Chickpea",    686.0));
        beans.addBean(new Bean(beans.size(), "Lentil",      1477.0));
        beans.addBean(new Bean(beans.size(), "Common bean", 129.0));
        beans.addBean(new Bean(beans.size(), "Soybean",     1866.0));
        beans.addItem(new Bean(beans.size(), "Java Bean",   0.0));

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
        beans.addBean(new Bean(beans.size(), "Mung bean",   1452.0));
        beans.addBean(new Bean(beans.size(), "Chickpea",    686.0));
        beans.addBean(new Bean(beans.size(), "Lentil",      1477.0));
        beans.addBean(new Bean(beans.size(), "Common bean", 129.0));
        beans.addBean(new Bean(beans.size(), "Soybean",     1866.0));
        beans.addItem(new Bean(beans.size(), "Java Bean",   0.0));

        // The table to edit
        final Table table = new Table(null, beans);
        table.setWidth("500px");
        table.setPageLength(10);
        table.setEditable(true);
        table.setVisibleColumns("id", "name", "energy");
        
        table.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = -5125713307352302436L;

            @Override
            public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
                if ("id".equals(propertyId))
                    return null;
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
            beans.addBean(new Bean(beans.size(), null, 0.0)));
        
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
