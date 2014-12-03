package com.vaadin.book.examples.component;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This example demonstrates the use of generated columns in a table. Generated
 * columns can be used for formatting values or calculating them from other
 * columns (or properties of the items).
 * 
 * For the data model, we use POJOs bound to a custom Container with BeanItem
 * items.
 * 
 * @author magi
 */
public class GeneratedColumnExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2916085990831946682L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("layoutclick".equals(context))
            layoutclick(layout);
        else if ("extended".equals(context))
            extended(layout);
        else if ("accessing".equals(context))
            accessing(layout);
        
        setCompositionRoot(layout);
    }

    public final static String layoutclickDescription =
        "<h1>Problem with Selection in a Generated Column with a Layout</h1>" +
        "<p>If you have certain layouts in a generated column, they " +
        "prevent selection in the table. You need to define a " +
        "<b>LayoutClickListener</b> for the layouts and, when clicked, " +
        "select the table row.</p>";

    void layoutclick(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.generatedcolumns.layoutclick
        // Have a table with one regular column
        final Table table = new Table();
        table.addContainerProperty("number", Integer.class, null);
        
        // Have some data in the table
        for (int i=1; i<=100; i++)
            table.addItem(new Object[]{i}, i);
        
        table.addGeneratedColumn("generated", new ColumnGenerator() {
            private static final long serialVersionUID = -8133822448459398254L;

            @Override
            public Component generateCell(Table source,
                    final Object itemId, Object columnId) {
                // Get the value in the first column
                int num = (Integer) source
                    .getContainerProperty(itemId, "number").getValue();

                // Create the component for the generated column
                VerticalLayout cellLayout = new VerticalLayout();
                cellLayout.addComponent(new Label("Row " + num));
                cellLayout.addComponent(new Label("Square " + num*num));
                cellLayout.addComponent(new Label("Cube " + num*num*num));

                // Forward clicks on the layout as selection
                // in the table
                cellLayout.addLayoutClickListener(new LayoutClickListener() {
                    private static final long serialVersionUID = 606542398049493724L;

                    @Override
                    public void layoutClick(LayoutClickEvent event) {
                        table.select(itemId);
                    }
                });
                return cellLayout;
            }
        });
        
        table.setSelectable(true);
        // END-EXAMPLE: component.table.generatedcolumns.layoutclick
        
        table.setPageLength(5);
        
        layout.addComponent(table);
    }
    
    // BEGIN-EXAMPLE: component.table.generatedcolumns.extended
    /**
     * The business model: fill-up at a gas station.
     */
    public class FillUp implements Serializable {
        private static final long serialVersionUID = -5909762375694974599L;

        Date date;
        double quantity;
        double total;

        public FillUp() {
        }

        public FillUp(int day, int month, int year, double quantity,
                double total) {
            date = new GregorianCalendar(year, month - 1, day).getTime();
            this.quantity = quantity;
            this.total = total;
        }

        /** Calculates price per unit of quantity (€/l). */
        public double price() {
            if (quantity != 0.0) {
                return total / quantity;
            } else {
                return 0.0;
            }
        }

        /** Calculates average daily consumption between two fill-ups. */
        public double dailyConsumption(FillUp other) {
            double difference_ms = date.getTime() - other.date.getTime();
            double days = difference_ms / 1000 / 3600 / 24;
            if (days < 0.5) {
                days = 1.0; // Avoid division by zero if two fill-ups on the
                // same day.
            }
            return quantity / days;
        }

        /** Calculates average daily consumption between two fill-ups. */
        public double dailyCost(FillUp other) {
            return price() * dailyConsumption(other);
        }

        // Getters and setters

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    };

    /**
     * Formats the dates in a column containing Date objects.
     * 
     * This generator produces non-editable components. 
     **/
    class NoneditableDateGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = 3741451390162331681L;

        /**
         * Generates the cell containing the Date value. The column is
         * irrelevant in this use case.
         */
        public Component generateCell(Table source, Object itemId,
                Object columnId) {
            Property<?> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(Date.class)) {
                Label label = new Label(String.format("%tF",
                        new Object[] { (Date) prop.getValue() }));
                return label;
            }

            return null;
        }
    }

    /** Table column generator for calculating price column. */
    class PriceColumnGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = 8970892155507886020L;

        public Component generateCell(Table source, Object itemId,
                                      Object columnId) {
            // Retrieve the item.
            BeanItem<?> item = (BeanItem<?>) source.getItem(itemId);

            // Retrieves the underlying POJO from the item.
            final FillUp fillup = (FillUp) item.getBean();

            // The generated component
            Label label = new Label(calculateAndFormatValue(fillup));

            // Refresh the label always when the source values change
            ValueChangeListener listener = event ->
                label.setValue(calculateAndFormatValue(fillup));
            
            // We must know from the business object that the
            // calculation of the "price" property depends on these
            // properties
            for (String pid: new String[]{"quantity", "total"})
                ((ValueChangeNotifier)item.getItemProperty(pid))
                    .addValueChangeListener(listener);
            
            // We set the style here. You can't use a CellStyleGenerator for
            // generated columns.
            label.addStyleName("column-price");
            return label;
        }
        
        String calculateAndFormatValue(FillUp fillup) {
            return String.format("%1.2f €", fillup.price());            
        }
    }

    /** Table column generator for calculating consumption column. */
    class ConsumptionColumnGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = 2742357693979144870L;

        /**
         * Generates a cell containing value calculated from the item.
         */
        public Component generateCell(final Table source,
                final Object itemId, Object columnId) {
            Indexed container = (Indexed) source.getContainerDataSource();

            // Can not calculate consumption for the first item.
            if (container.isFirstId(itemId)) {
                Label label = new Label("N/A");
                label.addStyleName("column-consumption");
                return label;
            }

            // Index of the previous item.
            Object prevItemId = container.prevItemId(itemId);
            
            // Retrieve the POJOs the calculated value depends on.
            FillUp fillup = (FillUp) ((BeanItem<?>) container.getItem(itemId)).getBean();
            FillUp prev   = (FillUp) ((BeanItem<?>) source.getItem(prevItemId)).getBean();
            
            // Generate the component for displaying the calculated value.
            Label label = new Label(calculateValue(fillup, prev));

            // Refresh the label always when the source values change
            ValueChangeListener listener = event -> // Java 8
                label.setValue(calculateValue(fillup, prev));
            Property<?> quantProp = container.getContainerProperty(itemId, "quantity");
            Property<?> totalProp = container.getContainerProperty(itemId, "total");
            ((ValueChangeNotifier)quantProp).addValueChangeListener(listener);
            ((ValueChangeNotifier)totalProp).addValueChangeListener(listener);
            
            // We set the style here. You can't use a CellStyleGenerator for
            // generated columns.
            label.addStyleName(getColumnStyle());
            
            return label;
        }

        public String calculateValue(FillUp fillup, FillUp prev) {            
            return String.format("%3.2f l", fillup.dailyConsumption(prev));
        }
        
        String getColumnStyle() {
            return "column-consumption";
        }
    }

    /** Table column generator for calculating daily cost column. */
    class DailyCostColumnGenerator extends ConsumptionColumnGenerator {
        private static final long serialVersionUID = -6676173701305381931L;

        @Override
        public String calculateValue(FillUp fillup, FillUp prev) {
            return String.format("%3.2f €", fillup.dailyCost(prev));
        }
        
        @Override
        String getColumnStyle() {
            return "column-dailycost";
        }
    }

    /**
     * Custom field factory that sets the fields as immediate.
     */
    public class ImmediateFieldFactory extends DefaultFieldFactory {
        private static final long serialVersionUID = 7067711376117004831L;

        @Override
        public Field<?> createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
            // Let the DefaultFieldFactory create the fields
            Field<?> field = super.createField(container, itemId,
                                            propertyId, uiContext);

            // ...and just set them as immediate
            ((AbstractField<?>) field).setImmediate(true);
            
            // Modify the width of TextFields
            if (field instanceof TextField)
                field.setWidth("100%");
            
            return field;
        }
    }
    
    class ValueFormatter implements Converter<String, Double> {
        private static final long serialVersionUID = -3820480366853638381L;

        String format;
        
        public ValueFormatter(String format) {
            this.format = format;
        }
        
        @Override
        public Double convertToModel(String value,
            Class<? extends Double> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
            throw new ConversionException("Conversion unsupported");
        }

        @Override
        public String convertToPresentation(Double value,
            Class<? extends String> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
            return String.format(format, new Object[] {(Double) value});
        }

        @Override
        public Class<Double> getModelType() {
            return Double.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }
    }

    void extended(VerticalLayout layout) {
        // Create a data source and bind it to a table
        BeanItemContainer<FillUp> data = new BeanItemContainer<FillUp>(FillUp.class); 
        Table table = new Table(null, data);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.addStyleName("generatedcolumntable");
        table.setHeight("300px");

        // Define formatters for columns needing that
        table.setConverter("quantity", new ValueFormatter("%.2f l"));
        table.setConverter("total",    new ValueFormatter("%.2f €"));

        // Define the generated columns and their generators.
        table.addGeneratedColumn("date",        new NoneditableDateGenerator());
        table.addGeneratedColumn("price",       new PriceColumnGenerator());
        table.addGeneratedColumn("consumption", new ConsumptionColumnGenerator());
        table.addGeneratedColumn("dailycost",   new DailyCostColumnGenerator());
        
        // Generated columns are automatically placed after property columns, so
        // we have to set the order of the columns explicitly.
        table.setVisibleColumns(new Object[] { "date", "quantity", "price",
                "total", "consumption", "dailycost" });
        
        table.setColumnHeader("date", "Date");
        table.setColumnHeader("quantity", "Quantity (l)");
        table.setColumnHeader("price", "Price (€)");
        table.setColumnHeader("total", "Total (€)");
        table.setColumnHeader("consumption", "Consumption (l/day)");
        table.setColumnHeader("dailycost", "Daily Cost (€/day)");

        // Add some data.
        data.addBean(new FillUp(19, 2,  2005, 44.96, 51.21));
        data.addBean(new FillUp(30, 3,  2005, 44.91, 53.67));
        data.addBean(new FillUp(20, 4,  2005, 42.96, 49.06));
        data.addBean(new FillUp(23, 5,  2005, 47.37, 55.28));
        data.addBean(new FillUp(6,  6,  2005, 35.34, 41.52));
        data.addBean(new FillUp(30, 6,  2005, 16.07, 20.00));
        data.addBean(new FillUp(2,  7,  2005, 36.40, 36.19));
        data.addBean(new FillUp(6,  7,  2005, 39.17, 50.90));
        data.addBean(new FillUp(27, 7,  2005, 43.43, 53.03));
        data.addBean(new FillUp(17, 8,  2005, 20,    29.18));
        data.addBean(new FillUp(30, 8,  2005, 46.06, 59.09));
        data.addBean(new FillUp(22, 9,  2005, 46.11, 60.36));
        data.addBean(new FillUp(14, 10, 2005, 41.51, 50.19));
        data.addBean(new FillUp(12, 11, 2005, 35.24, 40.00));
        data.addBean(new FillUp(28, 11, 2005, 45.26, 53.27));

        // Have a check box that allows the user to make the
        // quantity and total columns editable.
        CheckBox editable = new CheckBox(
            "Edit the input values - calculated columns are regenerated");
        editable.addValueChangeListener(event -> { // Java 8
            table.setEditable(editable.getValue().booleanValue());

            // The visible columns are affected by removal and addition of
            // generated columns so we have to redefine them.
            table.setVisibleColumns(new Object[] { "date", "quantity",
                    "total", "price", "consumption", "dailycost" });
        });

        // Use a custom field factory to set the edit fields as immediate.
        // This is used when the table is in editable mode.
        table.setTableFieldFactory(new ImmediateFieldFactory());

        // Build the layout
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.addComponents(new Label(
                "Table with column generators that format and calculate cell values."),
                table, editable, new Label(
                "Columns displayed in blue are calculated from Quantity and Total. "
                        + "Others are simply formatted."));
        layout.setExpandRatio(table, 1);
        layout.setSizeUndefined();
    }
    // END-EXAMPLE: component.table.generatedcolumns.extended

    void accessing(final VerticalLayout vertical) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        vertical.addComponent(layout);

        // BEGIN-EXAMPLE: component.table.generatedcolumns.accessing
        final HashMap<Object,Button> componentRefs = new HashMap<Object,Button>();
        
        final Table table = new Table();
        table.addContainerProperty("name", String.class, null);
        table.addContainerProperty("has", CheckBox.class, null);
        table.setPageLength(5);
        layout.addComponent(table);

        // Add items to the table
        String names[] = {"Hydrogen", "Helium", "Lithium", "Beryllium",
                          "Boron", "Carbon", "Nitrogen", "Oxygen", "Fluorine",
                          "Neon", "Sodium", "Magnesium", "Aluminium",
                          "Silicon", "Phosphorus", "Sulfur", "Chlorine",
                          "Argon", "Potassium", "Calcium", "Scandium"};
        for (int i=0; i<names.length; i++) {
            final int itemId = i;
            table.addItem(new Object[] {names[i], new CheckBox() {
                private static final long serialVersionUID = -3067649618289525969L;

                {
                    setValue(Math.random() > 0.5);
                    
                    // Reflect changes in the generated component
                    addValueChangeListener(new ValueChangeListener() {
                        private static final long serialVersionUID = 2494636734324669660L;
    
                        @Override
                        public void valueChange(Property.ValueChangeEvent event) {
                            boolean value = (Boolean) event.getProperty().getValue();
                            componentRefs.get(itemId).setEnabled(value);
                        }
                    });
                }
            }}, itemId);
        }

        // Show all generation states in a table
        final Table stateTable = new Table();
        stateTable.addContainerProperty("exists", CheckBox.class, null);
        for (int i=0; i<names.length; i++)
            stateTable.addItem(new Object[] {new CheckBox()}, i);
        stateTable.setPageLength(stateTable.size());
        layout.addComponent(stateTable);

        table.addGeneratedColumn("generated", new ColumnGenerator() {
            private static final long serialVersionUID = -8133822448459398254L;

            @Override
            public Component generateCell(Table source,
                    final Object itemId, Object columnId) {
                if (componentRefs.containsKey(itemId)) {
                    return componentRefs.get(itemId);
                } else {
                    Button button = new Button("Does Nothing") {
                        private static final long serialVersionUID = 134347097266425179L;

                        // Clean up generated components when they are detached
                        public void detach() {
                            super.detach();
                            componentRefs.remove(this);
                            CheckBox stateBox = (CheckBox) stateTable.getContainerProperty(itemId, "exists").getValue();
                            stateBox.setValue(false);
                        }
                    };
                    componentRefs.put(itemId, button);
                    
                    // We can access a non-generated value by the IID and PID
                    CheckBox box = (CheckBox) source.getContainerProperty(itemId, "has").getValue();
                    boolean value = (Boolean) box.getValue();
                    button.setEnabled(value);
                    
                    CheckBox stateBox = (CheckBox) stateTable.getContainerProperty(itemId, "exists").getValue();
                    stateBox.setValue(true);

                    return button;
                }
            }
        });
        // END-EXAMPLE: component.table.generatedcolumns.accessing
    }    
}
