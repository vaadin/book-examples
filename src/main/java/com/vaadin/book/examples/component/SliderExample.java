package com.vaadin.book.examples.component;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.VerticalLayout;

public class SliderExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;
    final String BOXSIZE = "200px";

    public void init (String context) {
    	addStyleName("sliderexample");
    	
        // BEGIN-EXAMPLE: component.slider
        GridLayout grid = new GridLayout(3,3);
        grid.setMargin(false);
        grid.setSizeUndefined();

        VerticalLayout boxcontainer = new VerticalLayout();
        boxcontainer.addStyleName("boxcontainer");
        boxcontainer.setWidth(BOXSIZE);
        boxcontainer.setHeight(BOXSIZE);
        boxcontainer.setMargin(true);
        grid.addComponent(boxcontainer, 1, 1);

        // A box of which size is controlled with the sliders
        Label box = new Label();
        box.addStyleName("sizeablebox");
        boxcontainer.addComponent(box);
        boxcontainer.setComponentAlignment(box, Alignment.MIDDLE_CENTER);
        
        // Vertical slider

        // Create a vertical slider
        Slider vertslider = new Slider(1, 100);
        vertslider.setOrientation(SliderOrientation.VERTICAL);
        vertslider.setHeight(BOXSIZE);
        grid.addComponent(vertslider, 0, 1);
        
        // Shows the value of the vertical slider
        Label vertvalue = new Label();
        vertvalue.setSizeUndefined();
        grid.addComponent(vertvalue, 0, 0, 1, 0);

        // Handle changes in slider value
        vertslider.addValueChangeListener(event -> { // Java 8
            double value = (Double) vertslider.getValue();
            box.setHeight((float) value, Unit.PERCENTAGE);
            vertvalue.setValue(String.valueOf(value));
        });
        
        // The slider has to be immediate to send the changes
        // immediately after the user drags the handle.
        vertslider.setImmediate(true);

        // Set the initial value. This has to be set after the
        // listener is added if we want the listener to handle
        // also this value change.
        vertslider.setValue(50.0);
		
		// Horizontal slider

        // Shows the value of the horizontal slider
        Label horvalue = new Label();
        horvalue.setSizeUndefined();
        grid.addComponent(horvalue, 2, 2);
        
        Slider horslider = new Slider(1, 100);
        horslider.setOrientation(SliderOrientation.HORIZONTAL);
        horslider.setWidth(BOXSIZE);
        grid.addComponent(horslider, 1, 2);
        
        // Handle changes in slider value; the slider has to be
        // immediate to send the event immediately.
        horslider.addValueChangeListener(event -> { // Java 8
			double value = (Double) horslider.getValue();
			box.setWidth((float) value, Unit.PERCENTAGE);
			horvalue.setValue(String.valueOf(value));
        });
        horslider.setImmediate(true);
        
        // Set the initial value. This has to be set after the
        // listener is added if we want the listener to handle
        // also this value change.
        horslider.setValue(50.0);

		// A surrounding root layout to allow aligning the grid
		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.addComponent(grid);
		root.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		
		setSizeFull();
        setCompositionRoot(root);
        // END-EXAMPLE: component.slider
    }
}
