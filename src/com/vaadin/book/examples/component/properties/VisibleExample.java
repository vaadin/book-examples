package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class VisibleExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
	    VerticalLayout layout = new VerticalLayout();
	    
	    if ("simple".equals(context))
	        simple();
        else if ("inlayout".equals(context))
            inlayout(layout);
        else if ("styling".equals(context))
            styling();
	    
	    if (getCompositionRoot() == null)
	        setCompositionRoot(layout);
	}
	
    void simple() {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.features.visible.simple
        TextField invisible = new TextField("Not gonna see this");
        invisible.setValue("You can't see this!");
        invisible.setVisible(false);
        layout.addComponent(invisible);
        // END-EXAMPLE: component.features.visible.simple
        
        setCompositionRoot(layout);
    }

    void inlayout(VerticalLayout root) {
        // BEGIN-EXAMPLE: component.features.visible.inlayout
        // Have a layout
        FormLayout layout = new FormLayout();

        // Add some components to it
        layout.addComponent(new TextField("A visible field"));
        layout.addComponent(new TextField("An invisible field"));
        layout.addComponent(new TextField("An invisible field"));
        layout.addComponent(new TextField("A visible field"));
        
        // Make the two invisible
        layout.getComponent(1).setVisible(false);
        layout.getComponent(2).setVisible(false);
        // END-EXAMPLE: component.features.visible.inlayout

        root.addComponent(layout);
    }

    void styling() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName("stylingexample");

        // BEGIN-EXAMPLE: component.features.readonly.styling
        TextField readonly = new TextField("Read-Only");
        readonly.setValue("Read-only value");
        readonly.setReadOnly(true);
        layout.addComponent(readonly);
        // END-EXAMPLE: component.features.readonly.styling
        
        setCompositionRoot(layout);
    }
}
