package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class CssLayoutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8458669675797366833L;

    public void init(String context) {
        addStyleName("csslayoutexample");

        if ("basic".equals(context))
            basic();
        else if ("styling".equals(context))
            styling();
        else if ("flow".equals(context))
            flow();
        else
            setCompositionRoot(new Label("Error"));
    }
    
    void basic() {
        // BEGIN-EXAMPLE: layout.csslayout.basic
        CssLayout layout = new CssLayout();
        
        // Component with a layout-managed caption and icon
        TextField tf = new TextField("A TextField");
        tf.setIcon(new ThemeResource("icons/user.png"));
        layout.addComponent(tf);

        // Labels are 100% wide by default so must unset width
        Label label = new Label("A Label");
        label.setWidth(null);
        layout.addComponent(label);
        
        // Button manages its own caption
        layout.addComponent(new Button("A Button"));
        // END-EXAMPLE: layout.csslayout.basic
        
        setCompositionRoot(layout);
    }
    
    void styling() {
        // BEGIN-EXAMPLE: layout.csslayout.styling
        CssLayout layout = new CssLayout();
        layout.addStyleName("mylayout");
        
        layout.addComponent(new TextField("Here's a field"));
        // END-EXAMPLE: layout.csslayout.styling
        
        setCompositionRoot(layout);
    }
    
    void flow() {
        // BEGIN-EXAMPLE: layout.csslayout.flow
        CssLayout layout = new CssLayout() {
            private static final long serialVersionUID = 321137800557050145L;

            @Override
            protected String getCss(Component c) {
                if (c instanceof Label) {
                    // Color the boxes with random colors
                    int rgb = (int) (Math.random()*(1<<24));
                    return "background: #" + Integer.toHexString(rgb);
                }
                return null;
            }
        };
        layout.setWidth("400px"); // Causes to wrap the contents

        // Add boxes of various sizes
        for (int i=0; i<40; i++) {
            Label box = new Label("&nbsp;", ContentMode.HTML);
            box.addStyleName("flowbox");
            box.setWidth((float) Math.random()*50,
                         Unit.PIXELS);
            box.setHeight((float) Math.random()*50,
                          Unit.PIXELS);
            layout.addComponent(box);
        }
        // END-EXAMPLE: layout.csslayout.flow
        
        setCompositionRoot(layout);
    }
}
