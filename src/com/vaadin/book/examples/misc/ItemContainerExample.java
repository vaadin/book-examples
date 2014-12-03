package com.vaadin.book.examples.misc;

import java.io.Serializable;

import org.vaadin.itemcontainer.ItemContainer;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class ItemContainerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: misc.itemcontainer.basic
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
        ItemContainer beanContainer =
            new ItemContainer();
        
        // Add some beans to it
        Bean beans[] = {
                new Bean("Mung bean",   1452.0),
                new Bean("Chickpea",    686.0),
                new Bean("Lentil",      1477.0),
                new Bean("Common bean", 129.0),
                new Bean("Soybean",     1866.0)
        };
        for (Bean bean: beans)
            beanContainer.addItem(new BeanItem<Bean>(bean));

        // Bind a table to it
        Table table = new Table("Beans of All Sorts", beanContainer);
        layout.addComponent(table);
        // END-EXAMPLE: datamodel.container.beanitemcontainer.basic
        
        table.setPageLength(beanContainer.size());
        table.setVisibleColumns(new Object[]{"name", "energy"});
    }
}
