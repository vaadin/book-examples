package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class EnabledExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -4690921662019963336L;

	public void init (String context) {
	    if ("simple".equals(context))
	        simple();
	    else if ("styling".equals(context))
	        styling();
	}
	
	void simple() {
		HorizontalLayout layout = new HorizontalLayout();

		// BEGIN-EXAMPLE: component.features.enabled.simple
		Button enabled = new Button("Enabled");
		enabled.setEnabled(true); // The default
		layout.addComponent(enabled);
		
		Button disabled = new Button("Disabled");
		disabled.setEnabled(false);
		layout.addComponent(disabled);
		// END-EXAMPLE: component.features.enabled.simple
		
		setCompositionRoot(layout);
    }

    void styling() {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.features.enabled.styling
        TextField disabled = new TextField("Disabled");
        disabled.setValue("Read-only value");
        disabled.setEnabled(false);
        layout.addComponent(disabled);
        // END-EXAMPLE: component.features.enabled.styling
        
        setCompositionRoot(layout);
    }
}
