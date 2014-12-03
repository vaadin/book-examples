package com.vaadin.book.examples.component.properties;

import java.util.Arrays;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

public class MultiSelectExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
	    VerticalLayout layout = new VerticalLayout();
	    
        if ("basic".equals(context))
            basic(layout);
        if ("beanfields".equals(context))
            ;//beanfields();
        
        setCompositionRoot(layout);
	}
	
	public static final String basicDescription =
	    "<h1>Basic Use of Required Field Property</h1>"+
	    "<p><i>Warning: this example has serious problems.</i></p>";
	
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.required.basic
        // BEGIN-EXAMPLE: component.select.listselect.multiselect
        // Create the selection component
        final ListSelect select = new ListSelect("My Selection");
        
        // Add some items
        for (String planet: new String[]{"Mercury", "Venus",
                "Earth", "Mars", "Jupiter", "Saturn", "Uranus",
                "Neptune"})
            select.addItem(planet);
        
        // Multiple selection mode
        select.setMultiSelect(true);
        
        // Must select at least one item
        select.setNullSelectionAllowed(false);

        // Preselect some items 
        select.setValue(Arrays.asList("Venus"));
        layout.addComponent(new Label(
                select.getValue().toString()));

        // Show all items
        select.setRows(select.size());
        
        // Feedback on value changes
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 4777915807221505438L;

            public void valueChange(ValueChangeEvent event) {
                layout.addComponent(new Label(
                        select.getValue().toString()));
            }
        });
        select.setImmediate(true);
        // END-EXAMPLE: component.select.listselect.multiselect
         
        layout.addComponent(select);
    }
}
