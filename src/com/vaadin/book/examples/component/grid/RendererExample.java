package com.vaadin.book.examples.component.grid;

import java.io.Serializable;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderer.ButtonRenderer;
import com.vaadin.ui.renderer.DateRenderer;
import com.vaadin.ui.renderer.HtmlRenderer;
import com.vaadin.ui.renderer.ImageRenderer;
import com.vaadin.ui.renderer.NumberRenderer;
import com.vaadin.ui.renderer.ProgressBarRenderer;
import com.vaadin.ui.renderer.TextRenderer;


public class RendererExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -2498736598078845090L;

    public void summary(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.summary
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("680px");
        grid.setHeight("380px");

        // Style image column and set row heights
        grid.setStyleName("gridwithpics128px");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("picture", Resource.class).setRenderer(new ImageRenderer());
        grid.addColumn("name", String.class);
        grid.addColumn("born", Date.class);
        grid.addColumn("link", String.class);
        grid.addColumn("button", String.class)
            .setRenderer(new ButtonRenderer(e ->
            Notification.show("Clicked " + grid.getContainerDataSource()
                .getContainerProperty(e.getItemId(), "name"))));

        Grid.Column bornColumn = grid.getColumn("born");
        bornColumn.setRenderer(new DateRenderer("%1$tB %1$te, %1$tY",
                                                Locale.ENGLISH));

        Grid.Column linkColumn = grid.getColumn("link");
        linkColumn.setRenderer(new HtmlRenderer(), new Converter<String,String>(){
            private static final long serialVersionUID = 6394779294728581811L;

            @Override
            public String convertToModel(String value,
                Class<? extends String> targetType, Locale locale)
                throws Converter.ConversionException {
                return "not implemented";
            }

            @Override
            public String convertToPresentation(String value,
                Class<? extends String> targetType, Locale locale)
                throws com.vaadin.data.util.converter.Converter.ConversionException {
                return "<a href='http://en.wikipedia.org/wiki/" + value +
                       "' target='_top'>more info</a>";
            }

            @Override
            public Class<String> getModelType() {
                return String.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
        
        // Add some data rows
        grid.addRow(new ThemeResource("img/copernicus-128px.jpg"),
                    "Nicolaus Copernicus", new GregorianCalendar(1473,2,19).getTime(),
                    "Nicolaus_Copernicus", "Delete");
        grid.addRow(new ThemeResource("img/galileo-128px.jpg"),
                    "Galileo Galilei", new GregorianCalendar(1564,2,15).getTime(),
                    "Galileo_Galilei", "Delete");
        grid.addRow(new ThemeResource("img/kepler-128px.jpg"),
                    "Johannes Kepler", new GregorianCalendar(1571,12,27).getTime(),
                    "Johannes_Kepler", "Delete");
        
        grid.setCellStyleGenerator(cell ->
            "picture".equals(cell.getPropertyId())? "imagecol" : null);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.summary
    }

    // A data model
    public class Person implements Serializable {
        private static final long serialVersionUID = 5643002875138191294L;

        private String name;
        private int    born;
        
        public Person(String name, int born) {
            this.name = name;
            this.born = born;
        }
        
        public String getName() {
            return name;
        }
        
        public int getBorn() {
            return born;
        }
    }

    public void button(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.button
        // BOOK: components.grid.renderer
        BeanItemContainer<Person> people =
            new BeanItemContainer<>(Person.class);
        
        people.addBean(new Person("Nicolaus Copernicus", 1473));
        people.addBean(new Person("Galileo Galilei", 1564));
        people.addBean(new Person("Johannes Kepler", 1571));
        
        // Generate button caption column
        GeneratedPropertyContainer gpc =
            new GeneratedPropertyContainer(people);
        gpc.addGeneratedProperty("delete",
            new PropertyValueGenerator<String>() {
            private static final long serialVersionUID = -8571003699455731586L;

            @Override
            public String getValue(Item item, Object itemId,
                                   Object propertyId) {
                return "Delete"; // The caption
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        
        // Create a grid
        Grid grid = new Grid(gpc);
        grid.setWidth("400px");
        grid.setHeight("170px");
        grid.setSelectionMode(SelectionMode.NONE);

        // Render a button that deletes the data row (item)
        grid.getColumn("delete")
            .setRenderer(new ButtonRenderer(e -> // Java 8
            grid.getContainerDataSource()
                .removeItem(e.getItemId())));
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.button
    }

    public void image(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.image
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setStyleName("gridwithpics128px");
        grid.setWidth("400px");
        grid.setHeight("400px");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("picture", Resource.class);
        grid.addColumn("name", String.class);
        
        // Set the image renderer
        grid.getColumn("picture").setRenderer(new ImageRenderer());
        
        // Add some data rows
        grid.addRow(new ThemeResource("img/copernicus-128px.jpg"),
                    "Nicolaus Copernicus");
        grid.addRow(new ThemeResource("img/galileo-128px.jpg"),
                    "Galileo Galilei");
        grid.addRow(new ThemeResource("img/kepler-128px.jpg"),
                    "Johannes Kepler");

        grid.setCellStyleGenerator(cell ->
            "picture".equals(cell.getPropertyId())? "imagecol" : null);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.image
    }

    public void imagebyname(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.imagebyname
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setStyleName("gridwithpics128px");
        grid.setWidth("400px");
        grid.setHeight("400px");
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("picture", String.class); // Filename
        grid.addColumn("name", String.class);
        
        // Set the image renderer
        grid.getColumn("picture").setRenderer(new ImageRenderer(),
            new Converter<Resource, String>() {
                private static final long serialVersionUID = 8586445872396630157L;

                @Override
                public String convertToModel(Resource value,
                    Class<? extends String> targetType, Locale loc)
                    throws Converter.ConversionException {
                    return "not needed";
                }

                @Override
                public Resource convertToPresentation(String value,
                    Class<? extends Resource> targetType, Locale loc)
                    throws Converter.ConversionException {
                    return new ThemeResource("img/" + value);
                }

                @Override
                public Class<String> getModelType() {
                    return String.class;
                }

                @Override
                public Class<Resource> getPresentationType() {
                    return Resource.class;
                }
        });
        
        // Add some data rows
        grid.addRow("copernicus-128px.jpg", "Nicolaus Copernicus");
        grid.addRow("galileo-128px.jpg", "Galileo Galilei");
        grid.addRow("kepler-128px.jpg", "Johannes Kepler");

        grid.setCellStyleGenerator(cell ->
            "picture".equals(cell.getPropertyId())? "imagecol" : null);

        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.imagebyname
    }
    
    public void date(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.date
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("400px");
        grid.setHeight("300px");
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("born", Date.class);

        Grid.Column bornColumn = grid.getColumn("born");
        bornColumn.setRenderer(new DateRenderer("%1$tB %1$te, %1$tY",
                                                Locale.ENGLISH));

        // Add some data rows
        grid.addRow("Nicolaus Copernicus",
                    new GregorianCalendar(1473,2,19).getTime());
        grid.addRow("Galileo Galilei",
                    new GregorianCalendar(1564,2,15).getTime());
        grid.addRow("Johannes Kepler",
                    new GregorianCalendar(1571,12,27).getTime());
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.date
    }

    public void html(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.html
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("500px");
        grid.setHeight("300px");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("born", Integer.class);
        grid.addColumn("link", String.class);

        Grid.Column linkColumn = grid.getColumn("link");
        linkColumn.setRenderer(new HtmlRenderer(), new Converter<String,String>(){

            @Override
            public String convertToModel(String value,
                Class<? extends String> targetType, Locale locale)
                throws Converter.ConversionException {
                return "not implemented";
            }

            @Override
            public String convertToPresentation(String value,
                Class<? extends String> targetType, Locale locale)
                throws com.vaadin.data.util.converter.Converter.ConversionException {
                return "<a href='http://en.wikipedia.org/wiki/" + value +
                       "' target='_blank'>more info</a>";
            }

            @Override
            public Class<String> getModelType() {
                return String.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
        
        // Add some data rows
        grid.addRow("Nicolaus Copernicus", 1473, "Nicolaus_Copernicus");
        grid.addRow("Galileo Galilei",     1564, "Galileo_Galilei");
        grid.addRow("Johannes Kepler",     1571, "Johannes_Kepler");
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.html
    }

    public void number(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.number
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("500px");
        grid.setHeight("300px");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("born", Integer.class);
        grid.addColumn("sletters", Integer.class);
        grid.addColumn("rating", Double.class);

        // Use decimal format
        grid.getColumn("born").setRenderer(new NumberRenderer(
            new DecimalFormat("in #### AD")));

        // Use textual formatting on numeric ranges
        grid.getColumn("sletters").setRenderer(new NumberRenderer(
            new ChoiceFormat("0#none|1#one|2#multiple")));

        // Use String.format() formatting
        grid.getColumn("rating").setRenderer(new NumberRenderer(
            "%02.4f", Locale.ENGLISH));

        // Add some data rows
        grid.addRow("Nicolaus Copernicus", 1473, 2, 0.4);
        grid.addRow("Galileo Galilei",     1564, 0, 4.2);
        grid.addRow("Johannes Kepler",     1571, 1, 2.3);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.number
    }

    public void progressbar(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.progressbar
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("500px");
        grid.setHeight("300px");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("rating", Double.class)
            .setRenderer(new ProgressBarRenderer());

        // Add some data rows
        grid.addRow("Nicolaus Copernicus", 0.1);
        grid.addRow("Galileo Galilei",     0.42);
        grid.addRow("Johannes Kepler",     1.0);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.progressbar
    }

    public void text(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.renderer.text
        // BOOK: components.grid.renderer
        // Create a grid
        Grid grid = new Grid();
        grid.setCaption("My Grid");
        grid.setWidth("500px");
        grid.setHeight("300px");
        
        // Disable selecting items
        grid.setSelectionMode(SelectionMode.NONE);

        // Define some columns
        grid.addColumn("name", String.class);
        grid.addColumn("rating", Double.class)
            .setRenderer(new TextRenderer());

        // Add some data rows
        grid.addRow("Nicolaus Copernicus", 0.1);
        grid.addRow("Galileo Galilei",     0.42);
        grid.addRow("Johannes Kepler",     1.0);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.renderer.text
    }
}
