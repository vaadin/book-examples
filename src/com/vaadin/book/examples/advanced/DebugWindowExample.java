package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DebugWindowExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        if ("analyze1".equals(context))
            analyze(layout);
        else if ("analyze2".equals(context))
            analyze2(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
    }
    
    @Override
    public void detach() {
        super.detach();
        
        for (Window w: UI.getCurrent().getWindows())
            UI.getCurrent().removeWindow(w);
    }
    
    void analyze (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.debug.analyze1
        // Have a panel with a layout
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        Panel panel = new Panel("Panel", content);
        panel.setWidth(null);

        // Inside that, have a layout that shrinks to fit its content
        VerticalLayout vl = new VerticalLayout();
        vl.setWidth(null);
        
        // This button expands to fill the container
        Button button = new Button("A 100% wide button, invalid");
        button.setWidth("100%");
        vl.addComponent(button);
        
        content.addComponent(vl);
        // END-EXAMPLE: advanced.debug.analyze1
        
        layout.addComponent(panel);
    }
    
    void analyze2 (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.debug.analyze2
        // Have a sub-window with a layout
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        Window window = new Window("Sub window", content);
        window.center();

        // Inside that, have a layout that shrinks to fit its content
        VerticalLayout vl = new VerticalLayout();
        vl.setWidth(null);
        
        // This button expands to fill the container
        Button button = new Button("A 100% wide button, invalid");
        button.setWidth("100%");
        vl.addComponent(button);

        content.addComponent(vl);
        // END-EXAMPLE: advanced.debug.analyze2
        
        UI.getCurrent().addWindow(window);
        layout.addComponent(new Label("See the sub-window"));
    }
}
