package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TooltipConfiguration;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DescriptionExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -7205982527018773943L;

	public void init (String context) {
	    VerticalLayout layout = new VerticalLayout();

	    if ("plain".equals(context))
    		plain();
        else if ("richtext".equals(context))
            richtext();
        else if ("customization".equals(context))
            customization(layout);

    	if (getCompositionRoot() == null)
    	    setCompositionRoot(layout);
    }
    
    void plain () {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        
        // BEGIN-EXAMPLE: component.features.description.plain
        Button button = new Button("A Button");
        button.setDescription("This is the tooltip");
        // END-EXAMPLE: component.features.description.plain

        layout.addComponent(button);
        setCompositionRoot(layout);
    }

    void richtext () {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setHeight("150px");
        
        // BEGIN-EXAMPLE: component.features.description.richtext
        Button button = new Button("A Button");
    	button.setDescription(
    		    "<h2><img src=\"VAADIN/themes/book-examples/icons/comment_yellow.gif\"/>"+
    		    "A Richtext Tooltip</h2>"+
    		    "<ul>"+
    		    "  <li>Use rich formatting with XHTML</li>"+
    		    "  <li>Include images from themes</li>"+
    		    "  <li>etc.</li>"+
    		    "</ul>");    	
        // END-EXAMPLE: component.features.description.richtext

        layout.addComponent(button);
        setCompositionRoot(layout);
    }
    
    void customization(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.features.description.customization
        Button button = new Button("A Button");
        button.setDescription("This is the tooltip");
        
        TooltipConfiguration conf =
                UI.getCurrent().getTooltipConfiguration();
        conf.setOpenDelay(2000);
        conf.setCloseTimeout(1000);
        
        layout.addComponent(new Button("Reset Defaults", new ClickListener() {
            
            private static final long serialVersionUID = 1592768122684554454L;

            @Override
            public void buttonClick(ClickEvent event) {
                TooltipConfiguration conf =
                    UI.getCurrent().getTooltipConfiguration();
                conf.setOpenDelay(750);
                conf.setCloseTimeout(300);
            }
        }));
        // END-EXAMPLE: component.features.description.customization

        layout.addComponent(button);
        layout.setSpacing(true);
    }
}
