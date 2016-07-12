package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SpacingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 4598073828719119575L;

    public void init (String context) {
        addStyleName("spacingexample");
        
        if ("vertical".equals(context))
            vertical();
        else if ("horizontal".equals(context))
            horizontal();
        else if ("grid".equals(context))
            grid();
        else
            setCompositionRoot(new Label("Error"));
    }
    
    void vertical() {
        // BEGIN-EXAMPLE: layout.formatting.spacing.vertical 
        HorizontalLayout layout = new HorizontalLayout();
        
        VerticalLayout layout1 = new VerticalLayout();
        layout1.setSpacing(false);
        layout1.addComponent(new Label("Item 1"));
        layout1.addComponent(new Label("Item 2"));
        layout1.addComponent(new Label("Item 3"));
        layout1.addComponent(new Label("Item 4"));
        layout1.addComponent(new Label("Item 5"));
        layout.addComponent(layout1);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.setSpacing(true);
        layout2.addStyleName("myspacing");
        layout2.addComponent(new Label("Item 1"));
        layout2.addComponent(new Label("Item 2"));
        layout2.addComponent(new Label("Item 3"));
        layout2.addComponent(new Label("Item 4"));
        layout2.addComponent(new Label("Item 5"));
        layout.addComponent(layout2);
        // END-EXAMPLE: layout.formatting.spacing.vertical 
        setCompositionRoot(layout);
    }

    void horizontal() {
        // BEGIN-EXAMPLE: layout.formatting.spacing.horizontal
        VerticalLayout layout = new VerticalLayout();
        
        HorizontalLayout layout1 = new HorizontalLayout();
        layout1.setSpacing(false);
        layout1.addComponent(new Label("Item 1"));
        layout1.addComponent(new Label("Item 2"));
        layout1.addComponent(new Label("Item 3"));
        layout1.addComponent(new Label("Item 4"));
        layout1.addComponent(new Label("Item 5"));
        layout.addComponent(layout1);

        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.setSpacing(true);
        layout2.addStyleName("myspacing");
        layout2.addComponent(new Label("Item 1"));
        layout2.addComponent(new Label("Item 2"));
        layout2.addComponent(new Label("Item 3"));
        layout2.addComponent(new Label("Item 4"));
        layout2.addComponent(new Label("Item 5"));
        layout.addComponent(layout2);
        // END-EXAMPLE: layout.formatting.spacing.horizontal
        
        setCompositionRoot(layout);
    }

    void grid() {
        // BEGIN-EXAMPLE: layout.formatting.spacing.grid
        HorizontalLayout layout = new HorizontalLayout();
        
        GridLayout layout1 = new GridLayout(3, 3);
        layout1.setSpacing(false);
        layout1.addComponent(new Label("Item 1,1"));
        layout1.addComponent(new Label("Item 1,2"));
        layout1.addComponent(new Label("Item 1,3"));
        layout1.addComponent(new Label("Item 2,1"));
        layout1.addComponent(new Label("Item 2,2"));
        layout1.addComponent(new Label("Item 2,3"));
        layout1.addComponent(new Label("Item 3,1"));
        layout1.addComponent(new Label("Item 3,2"));
        layout1.addComponent(new Label("Item 3,3"));
        layout.addComponent(layout1);

        GridLayout layout2 = new GridLayout(3, 3);
        layout2.setSpacing(true);
        layout2.addStyleName("myspacing");
        layout2.addComponent(new Label("Item 1,1"));
        layout2.addComponent(new Label("Item 1,2"));
        layout2.addComponent(new Label("Item 1,3"));
        layout2.addComponent(new Label("Item 2,1"));
        layout2.addComponent(new Label("Item 2,2"));
        layout2.addComponent(new Label("Item 2,3"));
        layout2.addComponent(new Label("Item 3,1"));
        layout2.addComponent(new Label("Item 3,2"));
        layout2.addComponent(new Label("Item 3,3"));
        layout.addComponent(layout2);
        // END-EXAMPLE: layout.formatting.spacing.grid
        
        setCompositionRoot(layout);
    }
}
