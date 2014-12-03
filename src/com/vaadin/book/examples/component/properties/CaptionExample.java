package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CaptionExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        if ("overview".equals(context)) {
            HorizontalLayout layout = new HorizontalLayout();

            // BEGIN-EXAMPLE: component.features.caption.overview
            // New text field with caption "Name"
            TextField name = new TextField("Name");
            layout.addComponent(name);
            // END-EXAMPLE: component.features.caption.overview
            
            setCompositionRoot(layout);
        } else if ("layout".equals(context)) {
            VerticalLayout layout = new VerticalLayout();
            
            // BEGIN-EXAMPLE: component.features.caption.layout
            VerticalLayout vertical = new VerticalLayout();
            vertical.addComponent(new TextField("A Text Field"));
            vertical.addComponent(new TextField("Second Text Field"));
            vertical.addComponent(new TextField("Third Text Field"));
            layout.addComponent(vertical);
    
            FormLayout form = new FormLayout();
            form.addComponent(new TextField("A Text Field"));
            form.addComponent(new TextField("Second Text Field"));
            form.addComponent(new TextField("Third Text Field"));
            layout.addComponent(form);
            // END-EXAMPLE: component.features.caption.layout

            vertical.setSizeUndefined();
            form.setSizeUndefined();
            layout.setComponentAlignment(vertical, Alignment.MIDDLE_RIGHT);
            layout.setComponentAlignment(form, Alignment.MIDDLE_RIGHT);
            
            layout.setSizeUndefined();
            setSizeUndefined();
            setCompositionRoot(layout);

        } else if ("self".equals(context)) {
            // BEGIN-EXAMPLE: component.features.caption.self
            Panel  panel  = new Panel("Panel");
            Button button = new Button("Button");
            panel.setContent(button);
            // END-EXAMPLE: component.features.caption.self
            
            panel.setSizeUndefined();
            panel.getContent().setSizeUndefined();
            setSizeUndefined();
            setCompositionRoot(panel);
        } else if ("security".equals(context)) {
            HorizontalLayout layout = new HorizontalLayout();

            // BEGIN-EXAMPLE: component.features.caption.security
            // New text field with caption "Name"
            TextField name = new TextField("Name");
            layout.addComponent(name);
            // END-EXAMPLE: component.features.caption.security
            
            setCompositionRoot(layout);
        } else if ("styling".equals(context)) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addStyleName("captionexample");

            // BEGIN-EXAMPLE: component.features.caption.styling
            // Ordinary text field
            TextField firstName = new TextField("First Name");
            layout.addComponent(firstName);

            // Centered caption
            TextField middleName = new TextField("Middle Name");
            middleName.addStyleName("in-middle");
            layout.addComponent(middleName);

            // Right-aligned caption
            TextField lastName = new TextField("Last Name");
            lastName.addStyleName("on-right");
            layout.addComponent(lastName);
            // END-EXAMPLE: component.features.caption.styling
            
            setCompositionRoot(layout);
        } else if ("special".equals(context)) {
            HorizontalLayout layout = new HorizontalLayout();

            // BEGIN-EXAMPLE: component.features.caption.special
            // Caption with special Unicode characters
            TextField name = new TextField("\u00A9 Copyright");
            layout.addComponent(name);
            // END-EXAMPLE: component.features.caption.special
            
            setCompositionRoot(layout);
        }
    }
}
