package com.vaadin.book.examples.datamodel;

import java.io.Serializable;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class BeanContainerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: datamodel.container.beancontainer.basic
    // Here is a JavaBean
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
        
        // Custom comparison
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Bean))
                return false;
            
            if (((Bean)obj).name.equals(name))
                return true;
            return false;
        }

        // Custom hash code calculation
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    void basic(VerticalLayout layout) {
        // Create a container for such beans with
        // strings as item IDs.
        BeanContainer<String, Bean> beans =
            new BeanContainer<String, Bean>(Bean.class);
        
        // Use the name property as the item ID of the bean
        beans.setBeanIdProperty("name");

        // Add some beans to it
        beans.addBean(new Bean("Mung bean",   1452.0));
        beans.addBean(new Bean("Chickpea",    686.0));
        beans.addBean(new Bean("Lentil",      1477.0));
        beans.addBean(new Bean("Common bean", 129.0));
        beans.addBean(new Bean("Soybean",     1866.0));

        // Bind a table to it
        Table table = new Table("Beans of All Sorts", beans);
        layout.addComponent(table);
        // END-EXAMPLE: datamodel.container.beancontainer.basic
        
        table.setPageLength(beans.size());
        table.setVisibleColumns(new Object[]{"name", "energy"});
    }
}
