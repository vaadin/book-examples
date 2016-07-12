package com.vaadin.book.examples.component.table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.book.examples.lib.Description;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class TableExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 8927954340596631505L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("singleselect".equals(context))
            singleselect(layout);
        else if ("multiselect".equals(context))
            multiselect(layout);
        else if ("components".equals(context))
            components();
        else if ("nestedtables".equals(context))
            nestedtables();
        else if ("interactingcomponents".equals(context))
            interactingcomponents(layout);
        else if ("beancomponents".equals(context))
            beancomponents();
        else if ("layouts".equals(context))
            layouts();
        else if ("reversebyindex".equals(context))
            reversebyindex(layout);
        else if ("ratios".equals(context))
            ratios();
        else if ("generatedcolumn".equals(context))
            generatedColumn();
        else if ("headers".equals(context))
            headers();
        else if ("fakeheaders".equals(context))
            fakeheaders();
        else if ("htmlheaders".equals(context))
            htmlheaders();
        else if ("footer-basic".equals(context))
            footer_basic();
        else if ("footer-sum".equals(context))
            footer_sum();
        else if ("headerclick".equals(context))
            headerclick();
        else if ("rowheaders".equals(context))
            rowheaders();
        else if ("contextmenu".equals(context))
            contextmenu(layout);
        else if ("filtering".equals(context))
            filtering();
        else if ("columnresize".equals(context))
            columnResize();
        else if ("columnreordering".equals(context))
            columnReordering();
        else if ("columncollapsing".equals(context))
            columnCollapsing();
        else if ("propertyformatter".equals(context))
            propertyformatter();
        else if ("columnformatting-simple".equals(context))
            columnformatting_simple();
        else if ("columnformatting-extended".equals(context))
            columnformatting_extended();
//        else if ("columnformatting-component".equals(context))
//            columnformatting_component();
        else if ("cellstylegenerator".equals(context))
            cellstylegenerator();
        else if ("cssinjection".equals(context))
            cssinjection();
        else if ("varyingrows".equals(context))
            varyingrows();
        else if ("varyingheightlabels".equals(context))
            varyingheightlabels();
        else if ("cellrenderer".equals(context))
            cellrenderer();
        else if ("editorform".equals(context))
            editorform();
        else if ("scrolltoitem".equals(context))
            scrolltoitem(layout);
        else if ("removeallitems".equals(context))
            removeallitems(layout);
        else if ("detailsshrink".equals(context))
            detailsshrink(layout);
        else
            setCompositionRoot(new Label("Invalid context: " + context));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.basic
        Table table = new Table("The Brightest Stars");
        
        // Define two columns for the built-in container
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Mag",  Float.class, null);

        // Add a row the hard way
        Object newItemId = table.addItem();
        Item row1 = table.getItem(newItemId);
        row1.getItemProperty("Name").setValue("Sirius");
        row1.getItemProperty("Mag").setValue(-1.46f);
        
        // Add a few other rows using shorthand addItem()
        table.addItem(new Object[]{"Canopus",        -0.72f}, 2);
        table.addItem(new Object[]{"Arcturus",       -0.04f}, 3);
        table.addItem(new Object[]{"Alpha Centauri", -0.01f}, 4);
        
        // Show exactly the currently contained rows (items)
        table.setPageLength(table.size());
        // END-EXAMPLE: component.table.basic
        
        layout.addComponent(table);
    }

    void singleselect(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.selecting.singleselect
        // Table with a component column in non-editable mode
        Table table = new Table("Important People",
                                generateContent());
        table.setPageLength(10);
        
        // Allow selecting
        table.setSelectable(true);
        
        // Trigger selection change events immediately
        table.setImmediate(true);
        
        // Handle selection changes
        table.addValueChangeListener(event -> { // Java 8
            if (event.getProperty().getValue() != null)
                layout.addComponent(new Label("Selected item " +
                        event.getProperty().getValue().toString()));
            else // Item deselected
                layout.addComponent(new Label("Nothing selected"));
        });
        // END-EXAMPLE: component.table.selecting.singleselect
        layout.addComponent(table);
    }

    void multiselect(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.selecting.multiselect
        // Table with a component column in non-editable mode
        Table table = new Table("Important People",
                                generateContent());
        table.setPageLength(10);

        // Allow selecting
        table.setSelectable(true);
        
        // Trigger selection change events immediately
        table.setImmediate(true);
        
        // Trigger selection change events immediately
        table.setMultiSelect(true);

        // Handle selection changes
        table.addValueChangeListener(event -> // Java 8
            layout.addComponent(new Label("Selected items " +
                    table.getValue().toString())));
        // END-EXAMPLE: component.table.selecting.multiselect
        layout.addComponent(table);
    }

    void components() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.table.components.components
        // Table with a component column in non-editable mode
        final Table table = new Table("My Table");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Description", TextArea.class, null);
        table.addContainerProperty("Delete", CheckBox.class, null);
        
        // Insert this data
        String people[][] = {{"Galileo",  "Liked to go around the Sun"},
                             {"Monnier",  "Liked star charts"},
                             {"Väisälä",  "Liked optics"},
                             {"Oterma",   "Liked comets"},
                             {"Valtaoja", "Likes cosmology and still "+
                                 "lives unlike the others above"},
                             };
        
        // Insert the data and the additional component column
        for (int i=0; i<people.length; i++) {
            TextArea area = new TextArea(null, people[i][1]);
            area.setRows(2);
            
            // Add an item with two components
            Object obj[] = {people[i][0], area, new CheckBox()};
            table.addItem(obj, new Integer(i));
        }
        table.setPageLength(table.size());
        // END-EXAMPLE: component.table.components.components
        layout.addComponent(table);
        
        setCompositionRoot(layout);
    }
    
    static public String nestedtablesDescription =
            "<h1>Nested Tables</h1>" +
            "<p>You can put tables inside tables. " +
            "If they do not have fixed heights, you may have a lot of trouble with scrollbars. " +
            "You may have trouble even if they have. " +
            "Try to use sufficient fixed widths whereever possible.</p>";
    
    void nestedtables() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.table.components.nestedtables
        // Table with a component column in non-editable mode
        Table table = new Table("My Nested Table");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Moons", Table.class, null);
        table.setColumnWidth("Moons", 120); // To avoid scrollbars
        table.setWidth("200px");            // To avoid scrollbars
        
        // Insert this data
        String planets[][]= {{"Mercury"},
                             {"Venus"},
                             {"Earth",   "The Moon"},
                             {"Mars",    "Phobos", "Deimos"},
                             {"Jupiter", "Io", "Europa", "Ganymedes", "Callisto"},
                             {"Saturn",  "Titan", "Tethys", "Dione", "Rhea", "Iapetus"},
                             {"Uranus",  "Miranda", "Ariel", "Umbriel", "Titania", "Oberon"},
                             {"Neptune", "Triton", "Proteus", "Nereid", "Larissa"}};
        
        // Insert the data and the additional component column
        for (int i=0; i<planets.length; i++) {
            Table moonTable = null;
            moonTable = new Table();
            moonTable.addContainerProperty("Name", String.class, null);
            moonTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
            moonTable.setWidth("100px");

            for (int j=1; j < planets[i].length; j++)
                moonTable.addItem(new String[]{planets[i][j]}, j);
            
            // Should be fixed height or there will be trouble
            moonTable.setPageLength(4);
            
            table.addItem(new Object[]{planets[i][0], moonTable}, i);
        }
        table.setPageLength(table.size());
        // END-EXAMPLE: component.table.components.nestedtables
        layout.addComponent(table);
        
        setCompositionRoot(layout);
    }
    
    public static String interactingcomponentsDescription =
            "<h1>Interaction Between Components in a Row</h1>" +
            "<p>Check the boxes in the Delete column to change the " +
            "state of the text area in the Description column.</p>";

    void interactingcomponents(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.components.interactingcomponents
        // Table with a component column in non-editable mode
        final Table table = new Table("My Table");
        table.addContainerProperty("name", String.class, null);
        table.addContainerProperty("description", TextArea.class, null);
        table.addContainerProperty("delete", CheckBox.class, null);
        
        // Insert this data
        String people[][] = {{"Galileo",  "Liked to go around the Sun"},
                             {"Monnier",  "Liked star charts"},
                             {"Väisälä",  "Liked optics"},
                             {"Oterma",   "Liked comets"},
                             {"Valtaoja", "Likes cosmology and still "+
                                 "lives unlike the others above"},
                             };
        
        // Insert the data and the additional component column
        for (int i=0; i<people.length; i++) {
            Object itemId = new Integer(i);

            TextArea area = new TextArea(null, people[i][1]);
            area.setRows(2);
            
            final CheckBox checkbox = new CheckBox();
            checkbox.setData(itemId); // Store item ID
            checkbox.addValueChangeListener(new ValueChangeListener() {
                private static final long serialVersionUID = -3557459528116040875L;

                @Override
                public void valueChange(ValueChangeEvent event) {
                    // Get the stored item ID
                    Object itemId = checkbox.getData();
                    
                    // As the property (column) type is a component type,
                    // we just get the property and its value to get the component.
                    TextArea textArea = ((TextArea)table.getContainerProperty
                            (itemId, "description").getValue());

                    // Modify the referenced component
                    boolean value = ((Boolean) checkbox.getValue()).booleanValue();
                    textArea.setEnabled(!value);
                }
            });
            checkbox.setImmediate(true);
            
            // Add an item with two components
            table.addItem(new Object[] {people[i][0], area, checkbox}, itemId);
        }
        
        
        table.setPageLength(table.size());
        // END-EXAMPLE: component.table.components.interactingcomponents

        layout.addComponent(table);
    }

    
    // BEGIN-EXAMPLE: component.table.components.beancomponents
    public class ComponentBean implements Serializable {
        private static final long serialVersionUID = 7475373552146980903L;

        TextField textfield = new TextField();
        CheckBox  checkbox  = new CheckBox();
        
        public ComponentBean(String text, boolean value) {
            textfield.setValue(text);
            checkbox.setValue(value);
        }
        
        public CheckBox getCheckbox() {
            return checkbox;
        }
        public void setCheckbox(CheckBox checkbox) {
            this.checkbox = checkbox;
        }
        
        public TextField getTextfield() {
            return textfield;
        }
        public void setTextfield(TextField textfield) {
            this.textfield = textfield;
        }
    }

    void beancomponents() {
        VerticalLayout layout = new VerticalLayout();
        
        final Table table = new Table("My Table");
        
        BeanItemContainer<ComponentBean> container =
            new BeanItemContainer<ComponentBean> (ComponentBean.class);
        for (int i=0; i<100; i++) {
            container.addBean(new ComponentBean("Hello", true));
            container.addBean(new ComponentBean("There", false));
        }
        table.setContainerDataSource(container);
        
        table.setPageLength(6);

        layout.addComponent(table);
        // END-EXAMPLE: component.table.components.beancomponents
        
        setCompositionRoot(layout);
    }

    // TODO: Unused.
    void components2() {
        // BEGIN-EXAMPLE: component.table.components.components2
        // Create a table and add a style to allow setting the row height in theme.
        final Table table = new Table();
        table.addStyleName("components-inside");
        
        // Define the names and data types of columns.
        // The "default value" parameter is meaningless here.
        table.addContainerProperty("Sum",            Label.class,     null);
        table.addContainerProperty("Is Transferred", CheckBox.class,  null);
        table.addContainerProperty("Comments",       TextField.class, null);
        table.addContainerProperty("Details",        Button.class,    null);

        /* Add a few items in the table. */
        for (int i=0; i<100; i++) {
            // Create the fields for the current table row
            Label sumField = new Label(String.format(
                           "Sum is <b>$%04.2f</b><br/><i>(VAT incl.)</i>",
                           new Object[] {new Double(Math.random()*1000)}),
                                       ContentMode.HTML);
            CheckBox transferredField = new CheckBox("is transferred");
            
            // Multiline text field. This required modifying the 
            // height of the table row.
            TextArea commentsField = new TextArea();
            commentsField.setRows(3);
            
            // The Table item identifier for the row.
            Integer itemId = new Integer(i);
            
            // Create a button and handle its click. A Button does not
            // know the item it is contained in, so we have to store the
            // item ID as user-defined data.
            Button detailsField = new Button("show details");
            detailsField.setData(itemId);
            detailsField.addClickListener(new Button.ClickListener() {
                private static final long serialVersionUID = -7750760166698945019L;

                public void buttonClick(ClickEvent event) {
                    // Get the item identifier from the user-defined data.
                    Integer itemId = (Integer)event.getButton().getData();
                    Notification.show("Link "+
                                      itemId.intValue()+" clicked.");
                } 
            });
            detailsField.addStyleName("link");
            
            // Create the table row.
            table.addItem(new Object[] {sumField, transferredField,
                                        commentsField, detailsField},
                          itemId);
        }

        // Show just three rows because they are so high.
        table.setPageLength(3);        
        // END-EXAMPLE: component.table.components.components2
    }
    
    public static final String varyingrowsDescription =
        "<h1>Varying Table Row Heights</h1>" +
        "<p>The table rows can have varying heights, but only if the table has no scrollbar. " +
        "Scrolling does not work fully with such tables.</p>" +
        "<p>Notice that rendering complex tables with a lot of nested components is slow.</p>" +
        "<p>See also: <a href='#component.table.features.cellrenderer'>Pattern: Cell Renderer</a>.</p>";
    
    void varyingrows() {
        final VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.table.components.varyingrows
        final Table table = new Table();
        table.addContainerProperty("column1", Component.class, null);
        
        for (int i=0; i<10; i++) {
            int height = 10*i+10;
            Label label = new Label("Height " + height);
            label.setHeight(height, Unit.PIXELS);
            table.addItem(new Object[]{label}, i);
        }
        
        // No scrollbar
        table.setPageLength(table.size());

        // TEST: Change the height of a row
        Button button = new Button("Change the heights of a row");
        button.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 7702133679440372939L;

            Table tableref = table;
            
            @Override
            public void buttonClick(ClickEvent event) {
                final Table newtable = new Table();
                newtable.addContainerProperty("column1", Component.class, null);
                
                for (int i=0; i<10; i++) {
                    int height = 10*i+10;
                    Label label = new Label("Height " + height);
                    label.setHeight(height, Unit.PIXELS);
                    newtable.addItem(new Object[]{label}, i);
                }

                Label label = new Label("Foo");
                label.setHeight((float) Math.random()*200, Unit.PIXELS);
                newtable.getItem(5).getItemProperty("column1").setValue(label);
                newtable.setPageLength(newtable.size());

                layout.replaceComponent(tableref, newtable);
                tableref = newtable;
            }
        });
        // END-EXAMPLE: component.table.components.varyingrows
        
        layout.addComponent(table);
        layout.addComponent(button);
        setCompositionRoot(layout);
    }

    void varyingheightlabels() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.table.components.varyingheightlabels
        final Table table = new Table();
        table.addStyleName("multirowlabels");
        table.addContainerProperty("column1", Component.class, null);
        
        String labels[] = {
                "This is a label",
                "This is a longer label",
                "This is yet a bit longer label",
                "This is significantly longer label "+
                "that will most certainly span over "+
                "several rows."};
        for (int i=0; i < 100*labels.length; i++) {
            Label label = new Label(i + ": " + labels[i % labels.length]);
            label.setWidth("100px");
            table.addItem(new Object[]{label}, i);
        }

        table.setPageLength(10);
        // END-EXAMPLE: component.table.components.varyingheightlabels
        
        layout.addComponent(table);
        setCompositionRoot(layout);
    }

    
    public static final String cellrendererDescription =
        "<h1>Pattern: CellRenderer</h1>" +
        "<p>This way you can invert the creation of cell content in a <b>Table</b></p>" +
        "<p>Notice that rendering complex tables with a lot of nested components is slow.</p>";
    
    void cellrenderer() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: component.table.components.cellrenderer
        // This should be an interface but can't define such here
        class CellRenderer {
            public Component render(int row, int column) {
                return null;
            }
        }

        // A table that uses the renderer to render it's content
        class RenderedTable extends Table {
            private static final long serialVersionUID = -2841078790151823345L;

            public RenderedTable(int rows, int columns,
                                 CellRenderer renderer) {
                for (int i=0; i<columns; i++)
                    addContainerProperty("column"+i,
                                         Component.class, null);

                for (int i=0; i<rows; i++) {
                    Object cols[] = new Object[columns];
                    for (int c=0; c<columns; c++)
                        cols[c] = renderer.render(i, c);
                    addItem(cols, i);
                }
                
                setPageLength(size());
            }
        }

        // Implement the rendering
        CellRenderer renderer = new CellRenderer() {
            @Override
            public Component render(int row, int column) {
                int height = 5*row*(1+column)+10;
                Component c;
                if (column == 0) {
                    c  = new Label("Height " + height);
                } else {
                    VerticalLayout l = new VerticalLayout();
                    l.setWidth("200px");
                    l.addComponent(new TextField("Hello"));
                    l.addComponent(new Button("Push Me!"));
                    c = l;
                }
                c.setHeight(height, Unit.PIXELS);
                return c;
            }
        };

        // Create the table using the renderer
        RenderedTable table = new RenderedTable(10, 2, renderer);
        // END-EXAMPLE: component.table.components.cellrenderer
        
        layout.addComponent(table);
        setCompositionRoot(layout);
    }

    void reversebyindex(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.sorting.reversebyindex
        Table table = new Table("Normal order");
        table.addContainerProperty("index", Integer.class, 0);
        for (int i=0; i<5; i++)
            table.addItem(new Object[]{i}, i);
        table.setSortContainerPropertyId("index");
        table.setSortAscending(true);

        Table reverse = new Table("Reverse order");
        reverse.addContainerProperty("index", Integer.class, 0);
        for (int i=0; i<5; i++)
            reverse.addItem(new Object[]{i}, i);
        reverse.setSortContainerPropertyId("index");
        reverse.setSortAscending(false);
        // END-EXAMPLE: component.table.sorting.reversebyindex

        table.setPageLength(table.size());
        reverse.setPageLength(reverse.size());
        HorizontalLayout hor = new HorizontalLayout(table, reverse);
        hor.setSpacing(true);
        layout.addComponent(hor);
    }
    
    /* Just a test, doesn't work. */
    void layouts() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.overview.layouts
        Table table = new Table("Wrapping Table");
        table.setWidth("100%");
        table.addContainerProperty("item", VerticalLayout.class, null);
        for (int i=0; i<3; i++) {
            VerticalLayout cellbase = new VerticalLayout();
            cellbase.setWidth("100%");
            CssLayout wrapping = new CssLayout();
            for (int j=0; j<60; j++) {
                Label label = new Label("Box");
                label.setWidth("200px");
                label.setHeight("200px");
                label.addStyleName("squarebox");
                wrapping.addComponent(label);
            }
            cellbase.addComponent(wrapping);
            table.addItem(new Object[]{cellbase}, new Integer(i));
        }
        // END-EXAMPLE: component.table.overview.layouts
        layout.addComponent(table);
        
        setCompositionRoot(layout);
    }
    
    void ratios() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.ratios
        Table table = new Table("Rational Table");
        // END-EXAMPLE: component.table.ratios
        layout.addComponent(table);

        setCompositionRoot(layout);
    }

    void generatedColumn() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.generatedcolumn
        Table table = new Table("Table with generated column");

        // END-EXAMPLE: component.table.generatedcolumn
        layout.addComponent(table);

        setCompositionRoot(layout);
    }

    void headers() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.headersfooters.headers
        Table table = new Table("Custom Table Headers");
        
        table.addContainerProperty("lastname", String.class, null);
        table.addContainerProperty("born", Integer.class, null);
        
        // Insert some data
        Object people[][] = {{"Galileo",  1564},
                             {"Väisälä",  1891},
                             {"Valtaoja", 1951}};
        for (int i=0; i<people.length; i++)
            table.addItem(people[i], new Integer(i));

        // Set nicer header names 
        table.setColumnHeader("lastname", "Name");
        table.setColumnHeader("born", "Born In");
        // END-EXAMPLE: component.table.headersfooters.headers

        // Adjust the table height a bit
        table.setPageLength(table.size());

        layout.addComponent(table);

        setCompositionRoot(layout);
    }

    public static String fakeheadersDescription =
        "<h1>Table with Fake Headers</h1>" +
        "<p>If you need multirow headers, header cells that span over multiple cells, or filter components in the header, " +
        "you can disable the standard header in Table and build a fake header. This requires that the columns "+
        "have fixed width.</p>"+
        "<p>Select a filter from the ComboBox.</p>";

    void fakeheaders() {
        VerticalLayout rootLayout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.headersfooters.fakeheaders
        final Table table = new Table();
        table.addContainerProperty("lastname", String.class, null);
        table.addContainerProperty("country", String.class, null);
        table.addContainerProperty("born", Integer.class, null);
        
        // Insert some data
        Object people[][] = {{"Galileo",  "Italian", 1564},
                             {"Väisälä",  "Finnish", 1891},
                             {"Valtaoja", "Finnish", 1951}};
        for (int i=0; i<people.length; i++)
            table.addItem(people[i], new Integer(i));

        // Hide the normal table header
        table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        
        // Use fixed widths for the columns
        final int lastnameWidth = 100;
        final int countryWidth = 100;
        final int bornWidth = 40;
        table.setColumnWidth("lastname", lastnameWidth);
        table.setColumnWidth("country", countryWidth);
        table.setColumnWidth("born", bornWidth);
        
        // Create country filter
        ComboBox countryFilterBox = new ComboBox();
        countryFilterBox.setWidth(countryWidth + 2*6, Unit.PIXELS);
        countryFilterBox.addItem("No filter");
        countryFilterBox.addItem("Italian");
        countryFilterBox.addItem("Finnish");
        countryFilterBox.setNullSelectionItemId("No filter");
        countryFilterBox.setInputPrompt("No filter");
        countryFilterBox.setImmediate(true);
        countryFilterBox.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6534784356634621623L;

            public void valueChange(ValueChangeEvent event) {
                // The filter API is not accessible in Table
                IndexedContainer container = (IndexedContainer) table.getContainerDataSource();

                String filter = (String) event.getProperty().getValue();
                if (filter == null) {
                    container.removeContainerFilters("country");
                    return;
                }

                // Set the filter
                container.removeContainerFilters("country");
                container.addContainerFilter("country", filter, true, true);
            }
        });
        
        // Custom header
        GridLayout tableHeader = new GridLayout(3,2);
        tableHeader.addStyleName("fakeheader");
        Label nameLabel = new Label("Name");
        nameLabel.setWidth(lastnameWidth + 2*6, Unit.PIXELS);
        tableHeader.addComponent(nameLabel, 0, 0, 0, 1);
        Label countryLabel = new Label("Country");
        countryLabel.setWidth(countryWidth + 2*6, Unit.PIXELS);
        tableHeader.addComponent(new Label("Country"), 1, 0);
        Label bornLabel = new Label("Born");
        bornLabel.setWidth(bornWidth + 2*6, Unit.PIXELS);
        tableHeader.addComponent(bornLabel, 2, 0, 2, 1);
        tableHeader.addComponent(countryFilterBox, 1, 1);
        
        // Put the header and table inside a vertical layout
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(tableHeader);
        layout.addComponent(table);
        // END-EXAMPLE: component.table.headersfooters.fakeheaders

        // Adjust the table height a bit
        table.setPageLength(table.size());

        rootLayout.addComponent(layout);

        setCompositionRoot(rootLayout);
    }

    void htmlheaders() {
        VerticalLayout rootLayout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.headersfooters.htmlheaders
        final Table table = new Table();
        table.addContainerProperty("lastname", String.class, null);
        table.addContainerProperty("country", String.class, null);
        table.addContainerProperty("born", Integer.class, null);
        
        // Normal column header
        table.setColumnHeader("lastname", "Last Name");

        // This column header is two rows high with the upper row
        // bleeding on the right
        table.setColumnHeader("country", "<div width='300px'>Details</div><br/>Country");
        
        // This is in the lower row
        table.setColumnHeader("born", "<br/>Born");
        
        // CSS modifications are necessary to make the header higher
        table.addStyleName("multirowheaders");
        
        // Insert some data
        Object people[][] = {{"Galileo",  "Italian", 1564},
                             {"Väisälä",  "Finnish", 1891},
                             {"Valtaoja", "Finnish", 1951}};
        for (int i=0; i<people.length; i++)
            table.addItem(people[i], new Integer(i));
        
        // The sort indicators are not rendered properly - could
        // perhaps fix the problem
        table.setSortEnabled(false);
        // END-EXAMPLE: component.table.headersfooters.htmlheaders

        // Adjust the table height a bit
        table.setPageLength(table.size());

        rootLayout.addComponent(table);

        setCompositionRoot(rootLayout);
    }
    
    void footer_basic() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.headersfooters.footers.footer-basic
        // Have a table with a numeric column
        Table table = new Table("Custom Table Footer");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Died At Age", Integer.class, null);
        
        // Insert some data
        Object people[][] = {{"Galileo",  77},
                             {"Monnier",  83},
                             {"Väisälä",  79},
                             {"Oterma",   86}};
        for (int i=0; i<people.length; i++)
            table.addItem(people[i], new Integer(i));
        
        // Calculate the average of the numeric column
        double avgAge = 0;
        for (int i=0; i<people.length; i++)
            avgAge += (Integer) people[i][1];
        avgAge /= people.length;

        // Set the footers
        table.setFooterVisible(true);
        table.setColumnFooter("Name", "Average");
        table.setColumnFooter("Died At Age", String.valueOf(avgAge));

        // Adjust the table height a bit
        table.setPageLength(table.size());
        // END-EXAMPLE: component.table.headersfooters.footers.footer-basic

        layout.addComponent(table);

        setCompositionRoot(layout);
    }

    void footer_sum() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.headersfooters.footers.footer-sum
        // Have a table with a numeric column
        final Table table = new Table("Roster of Fear");
        table.addContainerProperty("Source of Fear", String.class, null);
        table.addContainerProperty("Fear Factor", Double.class, null);
        
        // Put the table in edit mode to allow editing the fear factors
        table.setEditable(true);
        
        // Insert some data
        final Object fears[][] =  {{"Psycho",       8.7},
                                   {"Alien",        8.5},
                                   {"The Shining",  8.5},
                                   {"The Thing",    8.2}};
        for (int i=0; i<fears.length; i++)
            table.addItem(fears[i], new Integer(i));
        
        // Set the footers
        table.setFooterVisible(true);
        table.setColumnFooter("Source of Fear", "Sum of All Fears");

        // Calculate the sum every time any of the values change
        final Property.ValueChangeListener listener =
            new Property.ValueChangeListener() {
            private static final long serialVersionUID = -8630084084077626011L;

            public void valueChange(ValueChangeEvent event) {
                // Calculate the sum of the numeric column
                double sum = 0;
                for (Iterator<?> i = table.getItemIds().iterator(); i.hasNext();)
                    sum += (Double) table.getItem(i.next()).getItemProperty("Fear Factor").getValue();
                sum = Math.round(sum*100)/100.0; // Round to two decimals
                
                // Set the new sum in the footer
                table.setColumnFooter("Fear Factor", String.valueOf(sum));
            }
        };
        
        // Can't access the editable components from the table so
        // must store the information
        final HashMap<Integer,TextField> valueFields =
            new HashMap<Integer,TextField>();

        // Set the same listener of all textfields, and set them as immediate 
        table.setTableFieldFactory(new TableFieldFactory () {
            private static final long serialVersionUID = 5952914141719426842L;

            public Field<?> createField(Container container, Object itemId,
                    Object propertyId, Component uiContext) {
                TextField field = new TextField((String) propertyId);
                
                // User can only edit the numeric column
                if ("Source of Fear".equals(propertyId))
                    field.setReadOnly(true);
                else { // The numeric column
                    field.setImmediate(true);
                    field.addValueChangeListener(listener);
                    field.setColumns(7);
                    
                    // The field needs to know the item it is in
                    field.setData(itemId);
                    
                    // Remember the field
                    valueFields.put((Integer) itemId, field);
                    
                    // Focus the first editable value
                    if (((Integer)itemId) == 0)
                        field.focus();
                }
                return field;
            }
        });
        
        // Keyboard navigation
        class KbdHandler implements Handler {
            private static final long serialVersionUID = 6022713597760832050L;
            Action tab_next = new ShortcutAction("Tab",
                    ShortcutAction.KeyCode.TAB, null);
            Action tab_prev = new ShortcutAction("Shift+Tab",
                    ShortcutAction.KeyCode.TAB,
                    new int[] {ShortcutAction.ModifierKey.SHIFT});
            Action cur_down = new ShortcutAction("Down",
                    ShortcutAction.KeyCode.ARROW_DOWN, null);
            Action cur_up   = new ShortcutAction("Up",
                    ShortcutAction.KeyCode.ARROW_UP,   null);
            Action enter   = new ShortcutAction("Enter",
                    ShortcutAction.KeyCode.ENTER,      null);
            public Action[] getActions(Object target, Object sender) {
                return new Action[] {tab_next, tab_prev, cur_down,
                                     cur_up, enter};
            }

            public void handleAction(Action action, Object sender,
                                     Object target) {
                if (target instanceof TextField) {
                    // Move according to keypress
                    int itemid = (Integer) ((TextField) target).getData();
                    if (action == tab_next || action == cur_down)
                        itemid++;
                    else if (action == tab_prev || action == cur_up)
                        itemid--;
                    // On enter, just stay where you were. If we did
                    // not catch the enter action, the focus would be
                    // moved to wrong place.
                    
                    // TODO: Can't wrap cursor navigation because only
                    // the next and previous item fields are in the cache,
                    // the one at the other end of the table may not be.
                    if (itemid >= 0 && itemid < table.size()) {
                        TextField newTF = valueFields.get(itemid);
                        if (newTF != null)
                            newTF.focus();
                    }
                }
            }
        }
        
        // Panel that handles keyboard navigation
        Panel navigator = new Panel();
        navigator.addStyleName(Reindeer.PANEL_LIGHT);
        navigator.setContent(table);
        navigator.addActionHandler(new KbdHandler());
        
        // Adjust the table height a bit
        table.setPageLength(table.size());
        // END-EXAMPLE: component.table.headersfooters.footers.footer-sum

        layout.addComponent(navigator);

        setCompositionRoot(layout);
    }

    Table createSmallTable(String caption) {
        Table table = new Table(caption);
        
        table.addContainerProperty("lastname", String.class, null);
        table.addContainerProperty("born", Integer.class, null);
        table.addContainerProperty("died", Integer.class, null);
        
        // Insert some data
        Object people[][] = {{"Galileo",  1564, 1642},
                             {"Väisälä",  1891, 1971},
                             {"Valtaoja", 1951, null}};
        for (int i=0; i<people.length; i++)
            table.addItem(people[i], new Integer(i));

        // Set nicer header names 
        table.setColumnHeader("lastname", "Name");
        table.setColumnHeader("born", "Born");
        table.setColumnHeader("died", "Died");
        
        return table;
    }
    
    void headerclick() {
        VerticalLayout layout = new VerticalLayout();
        Table table = createSmallTable("Custom Table Headers");
        
        // table.setColumnReorderingAllowed(true);
        table.setImmediate(true);

        // BEGIN-EXAMPLE: component.table.headersfooters.headerclick
        // Handle the header clicks
        table.addHeaderClickListener(new Table.HeaderClickListener() {
            private static final long serialVersionUID = 2927158541717666732L;

            public void headerClick(HeaderClickEvent event) {
                String column = (String) event.getPropertyId();
                Notification.show("Clicked " + column +
                        "with " + event.getButtonName());
            }
        });
        
        // Disable the default sorting behavior
        table.setSortEnabled(false);
        // END-EXAMPLE: component.table.headersfooters.headerclick

        // Adjust the table height a bit
        table.setPageLength(table.size());

        layout.addComponent(table);
        setCompositionRoot(layout);
    }
    
    void columnResize() {
        VerticalLayout layout = new VerticalLayout();
        final Table table = createSmallTable("ColumnResize Events");

        // BEGIN-EXAMPLE: component.table.features.columnresize
        table.addColumnResizeListener(new Table.ColumnResizeListener() {
            private static final long serialVersionUID = 565232334910929589L;

            public void columnResize(ColumnResizeEvent event) {
                // Get the new width of the resized column
                int width = event.getCurrentWidth();
                
                // Get the property ID of the resized column
                String column = (String) event.getPropertyId();

                // Do something with the information
                table.setColumnFooter(column, String.valueOf(width) + "px");
            }
        });
        
        // Must be immediate to send the resize events immediately
        table.setImmediate(true);
        
        // Enable the footer
        table.setFooterVisible(true);
        
        // Give plenty of width
        table.setWidth("400px");
        // END-EXAMPLE: component.table.features.columnresize
        
        // Adjust the table height a bit
        table.setPageLength(table.size());

        layout.addComponent(table);
        setCompositionRoot(layout);
    }

    void columnReordering() {
        VerticalLayout layout = new VerticalLayout();
        final Table table = createSmallTable("Reordering Columns");

        // BEGIN-EXAMPLE: component.table.features.columnreordering
        table.setColumnReorderingAllowed(true);
        // END-EXAMPLE: component.table.features.columnreordering

        // Adjust the table height a bit
        table.setPageLength(table.size());

        layout.addComponent(table);
        layout.addComponent(new Label("Reorder the columns"));
        setCompositionRoot(layout);
    }

    void columnCollapsing() {
        VerticalLayout layout = new VerticalLayout();
        final Table table = createSmallTable("Column Collapsing");
        
        // BEGIN-EXAMPLE: component.table.features.columncollapsing
        // Allow the user to collapse and uncollapse columns
        table.setColumnCollapsingAllowed(true);

        // Collapse this column programmatically
        try {
            table.setColumnCollapsed("born", true);
        } catch (IllegalStateException e) {
            // Can't occur - collapsing was allowed above
            System.err.println("Something horrible occurred");
        }
        
        // Give enough width for the table to accommodate the
        // initially collapsed column later
        table.setWidth("250px");
        // END-EXAMPLE: component.table.features.columncollapsing

        // Adjust the table height a bit
        table.setPageLength(table.size());

        layout.addComponent(table);
        setCompositionRoot(layout);
    }
    
    public class Planet implements Serializable {
        private static final long serialVersionUID = -8382333346748462076L;

        String  name;
        float   equatorDiameter; // Relative to Earth
        float   mass;            // Relative to Earth
        float   orbitalRadius;   // AU
        float   orbitalPeriod;   // Years
        boolean rings;
        
        public Planet(String name, float equatorDiameter, float mass, float orbitalRadius, float orbitalPeriod, boolean rings) {
            this.name            = name;
            this.equatorDiameter = equatorDiameter;
            this.mass            = mass;
            this.orbitalRadius   = orbitalRadius;
            this.orbitalPeriod   = orbitalPeriod;
            this.rings           = rings;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public float getEquatorDiameter() {
            return equatorDiameter;
        }
        public void setEquatorDiameter(float equatorDiameter) {
            this.equatorDiameter = equatorDiameter;
        }
        public float getMass() {
            return mass;
        }
        public void setMass(float mass) {
            this.mass = mass;
        }
        public float getOrbitalRadius() {
            return orbitalRadius;
        }
        public void setOrbitalRadius(float orbitalRadius) {
            this.orbitalRadius = orbitalRadius;
        }
        public float getOrbitalPeriod() {
            return orbitalPeriod;
        }
        public void setOrbitalPeriod(float orbitalPeriod) {
            this.orbitalPeriod = orbitalPeriod;
        }
        public boolean isRings() {
            return rings;
        }
        public void setRings(boolean rings) {
            this.rings = rings;
        }
    }
    
    @Description("Shows how to add row header icon")
    public void rowheaders() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.rowheaders
        Planet planets[] = {
                new Planet("Mercury", 0, 0, 0, 0, false),      
                new Planet("Venus", 0, 0, 0, 0, false),     
                new Planet("Earth", 0, 0, 0, 0, false),    
                new Planet("Mars", 0, 0, 0, 0, false),    
                new Planet("Jupiter", 0, 0, 0, 0, false),    
                new Planet("Saturn", 0, 0, 0, 0, false),    
                new Planet("Uranus", 0, 0, 0, 0, false),    
                new Planet("Neptune", 0, 0, 0, 0, false),    
        };

        // Put the beans to a container 
        BeanItemContainer<Planet> bic = new BeanItemContainer<Planet>(Planet.class);
        bic.addAll(Arrays.asList(planets));
        
        // Add an icon property
        GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(bic);
        gpc.addGeneratedProperty("icon", new PropertyValueGenerator<Resource>() {
            private static final long serialVersionUID = -5268147622752316987L;

            @Override
            public Resource getValue(Item item, Object itemId,
                Object propertyId) {
                return new ThemeResource("img/planets/" +
                    item.getItemProperty("name").getValue().toString() +
                    "_symbol.png");
            }

            @Override
            public Class<Resource> getType() {
                return Resource.class;
            }
        });

        // Bind it to a table
        Table table = new Table("Custom Column Headers", gpc);
        table.setVisibleColumns(new Object[]{"name"});
        
        // Use only icons in the row header
        table.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
        table.setItemIconPropertyId("icon");
        // END-EXAMPLE: component.table.rowheaders

        // Adjust the table height a bit
        table.setPageLength(table.size());

        layout.addComponent(table);

        setCompositionRoot(layout);
    }
    
    void contextmenu(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.contextmenu
        // Have a table with some data
        final Table table = new Table("My Table", generateContent());
        table.setSelectable(true);
        table.addActionHandler(new Handler() {
            private static final long serialVersionUID = -2852187765422922564L;
            
            Action add = new Action("Add After");
            Action delete = new Action("Delete Item");

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                if (action == add) {
                    Object itemId;
                    if (target != null) // Clicked on an item
                        itemId = table.addItemAfter(target);
                    else
                        itemId = table.addItem();
                    Item item = table.getItem(itemId);
                    item.getItemProperty("name").setValue("Someone");
                    item.getItemProperty("city").setValue("Somewhere");
                    item.getItemProperty("year").setValue(1901);
                } else if (action == delete) {
                    table.removeItem(target);
                }
            }
            
            @Override
            public Action[] getActions(Object target, Object sender) {
                ArrayList<Action> actions = new ArrayList<Action>();
                actions.add(add);

                // Enable deleting only if clicked on an item
                if (target != null)
                    actions.add(delete);
                
                return (Action[]) (actions.toArray(new Action[actions.size()]));
            }
        });
        // END-EXAMPLE: component.table.contextmenu
        layout.addComponent(table);
    }

    void filtering() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.filtering
        final Table table = new Table("Table with column filters");
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Born", Integer.class, null);
        
        // Insert the filter row
        final Integer filterItemId = new Integer(0);
        table.addItem(filterItemId);
        
        // Insert this data
        Object people[][] = {{"Galileo",  1564},
                             {"Monnier",  1715},
                             {"Väisälä",  1891},
                             {"Oterma",   1915},
                             {"Valtaoja", 1951}};

        // Insert the data
        for (int i=0; i < people.length; i++)
            table.addItem(people[i], new Integer(i+1));
        
        table.setTableFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = -5733607818421877225L;

            @Override
            public Field<?> createField(Container container, Object itemId,
                    final Object propertyId, Component uiContext) {
                final TextField tf = new TextField();
                tf.setNullRepresentation("");
                if (itemId != filterItemId)
                    tf.setReadOnly(true);
                else {
                    tf.addValueChangeListener(new Property.ValueChangeListener() {
                        private static final long serialVersionUID = -2710408149417521841L;

                        public void valueChange(ValueChangeEvent event) {
                            IndexedContainer c = (IndexedContainer)
                            table.getContainerDataSource();
                            
                            // Remove old filter
                            c.removeContainerFilters(propertyId);
                            
                            // Set new filter
                            String filter = (String) tf.getValue();
                            if (filter != null)
                                c.addContainerFilter(propertyId, filter, true, false);
                        }
                    });
                    tf.setImmediate(true);
                }
                return tf;
            } 
        });
        table.setEditable(true);
        table.setImmediate(true);
        // END-EXAMPLE: component.table.filtering
        layout.addComponent(table);

        setCompositionRoot(layout);
    }
    
    void junk() {
    }
    
    public static String propertyformatterDescription = 
        "Shows how to use a property formatter to format table columns.";

    void propertyformatter() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.propertyformatter
        // 
        final Table table = new Table("Table with column filters");
        table.addContainerProperty("Time", Date.class, null);
        table.addContainerProperty("Value", Date.class, null);
        table.addContainerProperty("Message", Integer.class, null);
        
        // Put some data in the table
        long date = 0;
        for (int i=0; i<100; i++) {
            table.addItem(new Object[] {
                    new Date(date),
                    new Integer((int) (100*Math.random()))
            }, new Integer(i));
            date += (long) Math.random()*1000000;
        }
        
        // Define property formatters
        /*
        class TimeFormatter extends PropertyFormatter { 
            private static final long serialVersionUID = -7388054524856283447L;

            @Override
            public Object parse(String formattedValue) throws Exception {
                throw new UnsupportedOperationException("Not implemented");
            }
            
            @Override
            public String format(Object value) {
//<                ((Date) value)
                return null;
            }
        };*/
        // END-EXAMPLE: component.table.propertyformatter
        
        layout.addComponent(table);
        
        setCompositionRoot(layout);
    }
    
    public static String columnformatting_simpleDescription =
        "<h1>Formatting Table Columns</h1>"+
        "<p>You can format table columns in non-editable mode by overriding the <tt>formatPropertyValue()</tt> method.</p>";

    void columnformatting_simple() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.columnformatting.columnformatting-simple
        // Create a table that overrides the default property (column) format
        final Table table = new Table("Formatted Table") {
            private static final long serialVersionUID = -8802654824146931960L;

            @Override
            protected String formatPropertyValue(Object rowId,
                    Object colId, Property<?> property) {
                // Format by property type
                if (property.getType() == Date.class) {
                    SimpleDateFormat df =
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    return df.format((Date)property.getValue());
                }

                return super.formatPropertyValue(rowId, colId, property);
            }
        };
        
        // The table has some columns
        table.addContainerProperty("Time", Date.class, null);
        
        // Put some sample data in the table
        long date = 0;
        for (int i=0; i<100; i++) {
            table.addItem(new Object[]{new Date(date)}, new Integer(i));
            date += (long) (Math.random()*1E11);
        }
        // END-EXAMPLE: component.table.columnformatting.columnformatting-simple
        
        table.setPageLength(6);

        layout.addComponent(table);
        setCompositionRoot(layout);
    }    

    public static String columnformatting_extendedDescription =
        "<h1>Formatting Table Columns</h1>"+
        "<p>You can format table columns in non-editable mode by overriding the <tt>formatPropertyValue()</tt> method.</p>";

    void columnformatting_extended() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.columnformatting.columnformatting-extended
        // Use a specific locale for formatting decimal numbers
        final Locale locale = new Locale("fi", "FI");
        
        // Create a table that overrides the default property (column) format
        final Table table = new Table("Formatted Table") {
            private static final long serialVersionUID = -8802654824146931960L;

            @Override
            protected String formatPropertyValue(Object rowId,
                    Object colId, Property<?> property) {
                String pid = (String) colId;
                
                if ("Time".equals(pid)) {
                    // Format a date
                    SimpleDateFormat df =
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    return df.format((Date)property.getValue());
                    
            } else if ("Value".equals(pid)) {
                // Format a decimal value for a specific locale
                DecimalFormat df = new DecimalFormat("#.00",
                        new DecimalFormatSymbols(locale));
                return df.format((Double) property.getValue());
                
            } else if ("Message".equals(pid)) {
                    // You can add text while formatting, etc.
                    return "Msg #" + ((Integer) property.getValue());
                }
                return super.formatPropertyValue(rowId, colId, property);
            }
        };
        
        // Right-align the decimal column
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            private static final long serialVersionUID = -6387617214591002210L;

            @Override
            public String getStyle(Table source, Object itemId,
                                   Object propertyId) {
                if (propertyId != null &&
                        ("Value".equals((String) propertyId)))
                    return "right-aligned";
                return null;
            }
        });
        table.addStyleName("columnformatting");
        
        // The table has some columns
        table.addContainerProperty("Time", Date.class, null);
        table.addContainerProperty("Value", Double.class, null);
        table.addContainerProperty("Message", Integer.class, null);
        
        // Put some data in the table
        long date = 0;
        for (int i=0; i<100; i++) {
            table.addItem(new Object[] {
                    new Date(date),
                    new Double(Math.random()*1000),
                    new Integer((int) (100*Math.random()))
            }, new Integer(i));
            date += (long) (Math.random()*10000000000l);
        }
        // END-EXAMPLE: component.table.columnformatting.columnformatting-extended
        
        table.setPageLength(6);

        layout.addComponent(table);
        setCompositionRoot(layout);
    }    
/*    
    public static String columnformatting_componentDescription =
        "<h1>Formatting Table Columns with a Component</h1>"+
        "<p>You can format table columns with a component, in non-editable mode, by overriding the <tt>getPropertyValue()</tt> method.</p>" +
        "<p>Notice that the original method has logic to distinguish editable mode, so if you use the table also in editable " +
        "mode, please see the original implementation how it works.</p>";

    void columnformatting_component() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.table.columnformatting.columnformatting-component
        // Create a table that overrides the default property (column) format
        final Table table = new Table("Formatted Table") {
            private static final long serialVersionUID = -8802654824146931960L;
            
            @Override
            protected Object getPropertyValue(Object rowId, Object colId,
                    Property property) {
                // Format according to property type
                if (property.getType() == Boolean.class)
                    return new CheckBox(null, property);
                return super.getPropertyValue(rowId, colId, property);
            }
        };
        
        // The table has some columns
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Alive", Boolean.class, null);
        
        // Put some sample data in the table
        Object people[][] = {{"Galileo",  false},
                {"Monnier",  false},
                {"Väisälä",  false},
                {"Oterma",   false},
                {"Valtaoja", true}};
        for (int i=0; i<people.length; i++)
            table.addItem(people[i], new Integer(i));
        // END-EXAMPLE: component.table.columnformatting.columnformatting-component
        
        table.setPageLength(table.size());
        layout.addComponent(table);
        setCompositionRoot(layout);
    }    
*/
    public static IndexedContainer generateContent() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("city", String.class, null);
        container.addContainerProperty("year", Integer.class, null);
        
        addContent(container);
        return container;
    }
    
    public static void addContent(IndexedContainer container) {
        String[] firstnames = new String[]{"Isaac", "Ada", "Charles", "Douglas"};
        String[] lastnames  = new String[]{"Newton", "Lovelace", "Darwin", "Adams"};
        String[] cities     = new String[]{"London", "Oxford", "Innsbruck", "Turku"};
        for (int i=0; i<1000; i++) {
            Object itemId = container.addItem();
            String name = firstnames[(int) (Math.random()*4)] + " " + lastnames[(int) (Math.random()*4)];
            container.getItem(itemId).getItemProperty("name").setValue(name);
            String city = cities[(int) (Math.random()*4)];
            container.getItem(itemId).getItemProperty("city").setValue(city);
            Integer year = 1800 + (int) (Math.random()*200);
            container.getItem(itemId).getItemProperty("year").setValue(year);
        }
    }
    
    public static String cellstylegeneratorDescription =
        "<h1>Using Table Cell Style Generator</h1>";
    
    void cellstylegenerator() {
        // BEGIN-EXAMPLE: component.table.styling.cellstylegenerator
        Table table = new Table("Table with Cell Styles");
        table.addStyleName("checkerboard");

        // Add some columns in the table. In this example, the property
        // IDs of the container are integers so we can determine the
        // column number easily.
        table.addContainerProperty("0", String.class, null, "", null, null);
        for (int i=0; i<8; i++)
            table.addContainerProperty(String.valueOf(i+1), String.class, null,
                                 String.valueOf((char) (65+i)), null, null);

        // Add some items in the table.
        table.addItem(new Object[]{
            "1", "R", "N", "B", "Q", "K", "B", "N", "R"}, new Integer(0));
        table.addItem(new Object[]{
            "2", "P", "P", "P", "P", "P", "P", "P", "P"}, new Integer(1));
        for (int i=2; i<6; i++)
            table.addItem(new Object[]{String.valueOf(i+1), 
                         "", "", "", "", "", "", "", ""}, new Integer(i));
        table.addItem(new Object[]{
            "7", "P", "P", "P", "P", "P", "P", "P", "P"}, new Integer(6));
        table.addItem(new Object[]{
            "8", "R", "N", "B", "Q", "K", "B", "N", "R"}, new Integer(7));
        table.setPageLength(8);

        // Set cell style generator
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            private static final long serialVersionUID = 1001115748563712068L;

            @Override
            public String getStyle(Table source, Object itemId,
                                   Object propertyId) {
                // Per-row style setting, not relevant in this example.
                if (propertyId == null)
                    return "green";

                int row = ((Integer)itemId).intValue();
                int col = Integer.parseInt((String)propertyId);
                
                // The first column.
                if (col == 0)
                    return "rowheader";
                
                // Other cells.
                if ((row+col)%2 == 0)
                    return "black";
                else
                    return "white";
            }
        });        
        // END-EXAMPLE: component.table.styling.cellstylegenerator
        
        setCompositionRoot(table);
    }
    
    public static final String dualbindingDescription =
        "<h1>Binding a bean both to a Table and a Form</h1>"+
        "<p>Editing the <b>BeanItem</b> in the form fires value change events, "+
        "which are communicated to the other components bound to the same data source, in this case the <b>Table</b>.</p>";

    // BEGIN-EXAMPLE: component.table.binding.editorform
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
    
    void editorform() {
        VerticalLayout vlayout = new VerticalLayout();

        // Create a container for such beans
        final BeanItemContainer<Bean> beans =
            new BeanItemContainer<Bean>(Bean.class);
        
        // Add some beans to it
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));
        beans.addItem(new Bean("Java Bean",   0.0));

        // A layout for the table and form
        HorizontalLayout layout = new HorizontalLayout();

        // Bind a table to it
        final Table table = new Table("Beans of All Sorts", beans);
        table.setVisibleColumns(new Object[]{"name", "energy"});
        table.setPageLength(7);
        table.setBuffered(false);
        layout.addComponent(table);
        
        // Create a form for editing a selected or new item.
        // It is invisible until actually used.
        final Form form = new Form();
        form.setCaption("Edit Item");
        form.setVisible(false);
        form.setBuffered(true);
        layout.addComponent(form);
        
        // When the user selects an item, show it in the form
        table.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1162945655606583495L;

            public void valueChange(ValueChangeEvent event) {
                // Close the form if the item is deselected
                if (event.getProperty().getValue() == null) {
                    form.setVisible(false);
                    return;
                }

                // Bind the form to the selected item
                form.setItemDataSource(table.getItem(table.getValue()));
                form.setVisible(true);

                // The form was opened for editing an existing item
                table.setData(null);
            }
        });
        table.setSelectable(true);
        table.setImmediate(true);

        // Creates a new bean for editing in the form before adding
        // it to the table. Adding is handled after committing
        // the form.
        final Button newBean = new Button("New Bean");
        newBean.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -7340189561756261036L;

            public void buttonClick(ClickEvent event) {
                // Create a new item; this will create a new bean
                Object itemId = beans.addItem(new Bean("Foo", 42.0));
                form.setItemDataSource(table.getItem(itemId));

                // Make the form a bit nicer
                form.setVisibleItemProperties(
                        new Object[]{"name", "energy"});
                //((TextField)form.getField("name"))
                //        .setNullRepresentation("");
                
                // The form was opened for editing a new item
                table.setData(itemId);
                
                table.select(itemId);
                table.setEnabled(false);
                newBean.setEnabled(false);
                form.setVisible(true);
            }
        });

        // When OK button is clicked, commit the form to the bean
        final Button submit = new Button("Save");
        submit.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 6823630748713272361L;

            public void buttonClick(ClickEvent event) {
                form.commit();
                form.setVisible(false); // and close it
                
                // New items have to be added to the container
                if (table.getValue() == null) {
                    // Commit the addition
                    table.commit();
                    
                    table.setEnabled(true);
                    newBean.setEnabled(true);
                }
            }
        });
        form.getFooter().addComponent(submit);
        
        // Make modification to enable/disable the Save button
        form.setFormFieldFactory(new DefaultFieldFactory() {
            private static final long serialVersionUID = -1844463910858883298L;

            @Override
            public Field createField(Item item, Object propertyId, Component uiContext) {
                final AbstractField field = (AbstractField)
                        super.createField(item, propertyId, uiContext);
                field.addValueChangeListener(new ValueChangeListener() {
                    private static final long serialVersionUID = 5216192896151241766L;

                    @Override
                    public void valueChange(ValueChangeEvent event) {
                        submit.setEnabled(form.isModified());
                    }
                });
                if (field instanceof TextField) {
                    final TextField tf = (TextField) field;
                    tf.addTextChangeListener(new TextChangeListener() {
                        private static final long serialVersionUID = 5674948434239591166L;

                        @Override
                        public void textChange(TextChangeEvent event) {
                            if (form.isModified() ||
                                !event.getText().equals(tf.getValue())) {
                                submit.setEnabled(true);
                                
                                // Not needed after first event unless
                                // want to detect also changes back to
                                // unmodified value.
                                tf.removeListener(this);
                                
                                // Has to be reset because the
                                // removeListener() setting causes
                                // updating the field value from the
                                // server-side.
                                tf.setValue(event.getText());
                            }
                        }
                    });
                }
                field.setImmediate(true);
                
                return field;
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -1749148888766063606L;

            public void buttonClick(ClickEvent event) {
                form.discard();  // Not really necessary
                form.setVisible(false); // and close it
                table.discard(); // Discards possible addItem()
                table.setEnabled(true);
                if (table.getData() != null)
                    beans.removeItem(table.getData());
                newBean.setEnabled(true);
            }
        });
        form.getFooter().addComponent(cancel);
        // END-EXAMPLE: component.table.binding.editorform

        layout.setSpacing(true);
        vlayout.addComponent(layout);
        vlayout.addComponent(newBean);
        setCompositionRoot(vlayout);
    }
    
    void scrolltoitem(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.features.scrolltoitem
        // Have some data
        IndexedContainer container = generateContent();
        
        // Create to table to show it
        Table table = new Table("Scrolled Table");
        table.setContainerDataSource(container);
        
        // Pick up some item
        Object itemId = container.getIdByIndex(container.size()/2);
        layout.addComponent(new Label("Scrolling to... " +
                container.getContainerProperty(itemId, "name")));
        
        // Scroll the table to that item
        table.setCurrentPageFirstItemId(itemId);
        
        table.setPageLength(10);

        layout.addComponent(table);
        // END-EXAMPLE: component.table.features.scrolltoitem
    }

    void removeallitems(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.features.removeallitems
        // Have some data
        final IndexedContainer container = generateContent();
        
        // Create to table to show it
        final Table table = new Table("Table with Stuff");
        table.setContainerDataSource(container);
        layout.addComponent(table);
        
        Button recreate = new Button("Recreate Data");
        recreate.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -4359709701596552064L;

            @Override
            public void buttonClick(ClickEvent event) {
                container.removeAllItems();
                
                // Add some content to it
                addContent(container);
            }
        });
        layout.addComponent(recreate);
        // END-EXAMPLE: component.table.features.removeallitems
    }

    void detailsshrink(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.features.detailsshrink
        Panel panel = new Panel("A fixed-size area for the stuff");
        panel.setWidth("600px");
        panel.setHeight("500px");
        layout.addComponent(panel);

        // Intermediate layout to contain both table and details
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setSizeFull();
        panelContent.setMargin(true);
        panel.setContent(panelContent);
        
        // Have a table with some data
        final Table table = new Table("Master Table",
            generateContent());
        table.setSizeFull();
        table.setSelectable(true);
        panelContent.addComponent(table);
        panelContent.setExpandRatio(table, 1.0f);
        
        // Show item details here
        class MyForm extends FormLayout {
            private static final long serialVersionUID = -5437365204238811631L;

            TextField name = new TextField("Name");
            TextField city = new TextField("City");
            TextField year = new TextField("Year");
            
            public void setItemDataSource(Item item) {
                addComponents(name, city, year);
                
                FieldGroup binder = new FieldGroup(item);
                binder.bindMemberFields(this);
            }
        };
        final MyForm myform = new MyForm();
        myform.setVisible(false);
        panelContent.addComponent(myform);

        // When an item is selected, resize the table
        table.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 7008036072560258913L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                Object selectedItemId = event.getProperty().getValue();
                if (selectedItemId != null) {
                    myform.setItemDataSource(table.getItem(selectedItemId));
                    if (! myform.isVisible()) {
                        table.setCurrentPageFirstItemId(selectedItemId);
                        myform.setVisible(true);
                    }
                } else
                    myform.setVisible(false);
            }
        });
        table.setImmediate(true);
        
        // END-EXAMPLE: component.table.features.detailsshrink
    }    
    
    public static String cssinjectionDescription =
        "<p>Another way to control the appearance of table cells is to inject CSS by using the <b>CssLayout</b></p>";

    void cssinjection() {
        // BEGIN-EXAMPLE: component.table.styling.cssinjection
        Table table = new Table("Colorful Table");
        table.addStyleName("colorful");
        table.setPageLength(16);

        // We wrap cell contents inside a CssLayout, which
        // allows CSS injection.
        table.addContainerProperty("Color", CssLayout.class, null);
        
        for (int i=0; i<16; i++) {
            final int color = 255-i*16;
            
            // Get hexadecimal representation
            StringBuilder sb = new StringBuilder();
            new Formatter(sb).format("#%1$02x%1$02xff", color);
            final String colorcode = sb.toString();

            // Stylable wrapper for the cell content 
            CssLayout content = new CssLayout() {
                private static final long serialVersionUID = -376388455069345789L;

                @Override
                public String getCss(Component c) {
                    return "background: " + colorcode + ";";
                }
            };

            // The actual cell content
            Label label = new Label("Here's color " + colorcode);
            label.setSizeUndefined();
            content.addComponent(label);

            table.addItem(new Object[] {content}, new Integer(i));
        }
        // END-EXAMPLE: component.table.styling.cssinjection
        
        setCompositionRoot(table);
    }
}
