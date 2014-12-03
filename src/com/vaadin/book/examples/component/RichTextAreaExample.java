package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

public class RichTextAreaExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("localization".equals(context))
            localization(layout);
        
        setCompositionRoot(layout);
    }
     
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.richtextarea.basic
        RichTextArea area = new RichTextArea("My Rich Textarea");
        area.setValue("<h1>Helpful Heading</h1>"+
                      "<p>All this is for you to edit.</p>");
        
        // Handle edits
        Button ok = new Button("OK");
        Label feedback = new Label((String) area.getValue()); 
        ok.addClickListener(event -> // Java 8
            feedback.setValue(area.getValue()));
        // END-EXAMPLE: component.richtextarea.basic

        layout.addComponents(area, ok, feedback);
        layout.setSpacing(true);
    }
    
    void localization(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.richtextarea.localization
        RichTextArea area = new RichTextArea();
        area.setCaption("You can edit stuff here");
        area.setValue("<h1>Helpful Heading</h1>"+
                      "<p>All this is for you to edit.</p>");
        
        // END-EXAMPLE: component.richtextarea.localization

        layout.addComponent(area);
    }
    
}
