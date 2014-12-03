package com.vaadin.book.examples.component.properties;

import java.util.Stack;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ReadOnlyExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
        if ("simple".equals(context))
            simple();
        else if ("layouts".equals(context))
            layouts();
	    else if ("styling".equals(context))
	        styling();
	}
	
	void simple() {
		HorizontalLayout layout = new HorizontalLayout();

		// BEGIN-EXAMPLE: component.features.readonly.simple
		TextField readwrite = new TextField("Read-Write");
		readwrite.setValue("You can change this");
		readwrite.setReadOnly(false); // The default
		layout.addComponent(readwrite);
		
		TextField readonly = new TextField("Read-Only");
		readonly.setValue("You can't touch this!");
		readonly.setReadOnly(true);
		layout.addComponent(readonly);
		// END-EXAMPLE: component.features.readonly.simple
		
		readonly.setDescription("Tooltips work ok");
		
		setCompositionRoot(layout);
    }

    void layouts() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSpacing(true);

        // BEGIN-EXAMPLE: component.features.readonly.layouts
        // This doesn't work
        VerticalLayout layout1 = new VerticalLayout();
        layout1.setCaption("Read-Only Layout");
        layout1.addComponent(new TextField("TextField"));
        layout1.setReadOnly(true);
        
        // Iterating over the children is easy
        VerticalLayout layout2 = new VerticalLayout();
        layout2.setCaption("Read-Only Contents");
        layout2.addComponent(new TextField("TextField"));
        for (Component c: layout2)
            c.setReadOnly(true);
        
        // More complex layout (Panel has an inner layout)
        Panel layout3 = new Panel("Read-Only Contents");
        layout3.setContent(new TextField("TextField"));

        // Need to use traversal
        Stack<Component> stack = new Stack<Component>();
        stack.push(layout3);
        while (!stack.isEmpty()) {
            Component c = stack.pop();
            if (c instanceof ComponentContainer)
                for (Component i: (ComponentContainer) c)
                    stack.add(i);
            c.setReadOnly(true);
        }
        // END-EXAMPLE: component.features.readonly.layouts
        
        root.addComponent(layout1);
        root.addComponent(layout2);
        
        setCompositionRoot(root);
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
