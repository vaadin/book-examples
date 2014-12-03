package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class WindowExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 3321998143334619838L;

    String context;
    
    public void init(String context) {
        this.context = context;
    }
    
    public void attach() {
        VerticalLayout layout = new VerticalLayout();

        if ("mainwindowsize".equals(context))
            mainwindowsize(layout);
        else
            setCompositionRoot(new Label("Error"));
        
        setCompositionRoot(layout);
    }
    
    void mainwindowsize(VerticalLayout root) {
        // BEGIN-EXAMPLE: layout.window.mainwindowsize
        FormLayout layout = new FormLayout();

        Label height = new Label("" + getUI().getPage().getBrowserWindowHeight());
        height.setCaption("Height:");
        layout.addComponent(height);

        Label width = new Label("" + getUI().getPage().getBrowserWindowWidth());
        width.setCaption("Width:");
        layout.addComponent(width);
        // END-EXAMPLE: layout.window.mainwindowsize
        
        root.addComponent(layout);
    }
}
