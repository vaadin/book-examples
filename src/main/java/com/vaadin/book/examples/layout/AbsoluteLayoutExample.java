package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AbsoluteLayoutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8458669675797366833L;

    public void init(String context) {
        VerticalLayout border = new VerticalLayout();
        Layout layout;

        if ("basic".equals(context))
            layout = basic();
        else if ("area".equals(context))
            layout = area();
        else if ("zindex".equals(context))
            layout = zindex();
        else if ("bottomright".equals(context))
            layout = bottomRightRelative();
        else if ("proportional".equals(context))
            layout = proportional();
        else {
            setCompositionRoot(new Label("Error"));
            return;
        }

        border.addComponent(layout);
        border.setMargin(true);
        border.setSizeUndefined();
        border.addStyleName("borderframe");
        setSizeUndefined();
        setCompositionRoot(border);
        addStyleName("abslayoutexample");
    }

    Layout basic() {
        // BEGIN-EXAMPLE: layout.absolutelayout.basic
        AbsoluteLayout layout = new AbsoluteLayout();
        layout.setWidth("300px");
        layout.setHeight("150px");
        
        // A component with coordinates for its top-left corner
        TextField text = new TextField("Somewhere someplace");
        layout.addComponent(text, "left: 50px; top: 50px;");
        // END-EXAMPLE: layout.absolutelayout.basic
        
        return layout;
    }
    
    Layout bottomRightRelative() {
        // BEGIN-EXAMPLE: layout.absolutelayout.bottomright
        // A 400x250 pixels size layout
        AbsoluteLayout layout = new AbsoluteLayout();
        layout.setWidth("400px");
        layout.setHeight("250px");
        
        // A component with coordinates for its top-left corner
        TextField text = new TextField("Somewhere someplace");
        layout.addComponent(text, "left: 50px; top: 50px;");

        // At the top-left corner
        Button button = new Button( "left: 0px; top: 0px;");
        layout.addComponent(button, "left: 0px; top: 0px;");

        // At the bottom-right corner
        Button buttCorner = new Button( "right: 0px; bottom: 0px;");
        layout.addComponent(buttCorner, "right: 0px; bottom: 0px;");

        // Relative to the bottom-right corner
        Button buttBrRelative = new Button( "right: 50px; bottom: 50px;");
        layout.addComponent(buttBrRelative, "right: 50px; bottom: 50px;");

        // On the bottom, relative to the left side
        Button buttBottom = new Button( "left: 50px; bottom: 0px;");
        layout.addComponent(buttBottom, "left: 50px; bottom: 0px;");

        // On the right side, up from the bottom
        Button buttRight = new Button( "right: 0px; bottom: 100px;");
        layout.addComponent(buttRight, "right: 0px; bottom: 100px;");
        // END-EXAMPLE: layout.absolutelayout.bottomright

        return layout;
    }

    Layout area() {
        // BEGIN-EXAMPLE: layout.absolutelayout.area
        AbsoluteLayout layout = new AbsoluteLayout();
        layout.setWidth("300px");
        layout.setHeight("250px");
        
        // Specify an area that a component should fill
        Panel panel = new Panel("A Panel filling an area");
        panel.setSizeFull(); // Fill the entire given area
        layout.addComponent(panel, "left: 25px; right: 50px; "+
                                   "top: 100px; bottom: 50px;");
        // END-EXAMPLE: layout.absolutelayout.area
        
        return layout;
    }
    
    Layout zindex() {
        // BEGIN-EXAMPLE: layout.absolutelayout.zindex
        AbsoluteLayout layout = new AbsoluteLayout();
        layout.setWidth("300px");
        layout.setHeight("250px");
        
        // Specify an area that a component should fill
        Panel panel = new Panel("A Panel filling an area");
        panel.setSizeFull(); // Fill the entire given area
        layout.addComponent(panel, "left: 25px; right: 50px; "+
                                   "top: 100px; bottom: 50px; z-index:0;");

        // Specify an area that a component should fill
        Panel panel2 = new Panel("A Panel at z-index 1000");
        panel2.setSizeFull(); // Fill the entire given area
        layout.addComponent(panel2, "left: 100px; right: 25px; "+
                                    "top: 50px; bottom: 100px; "+
                                    "z-index:-1;");
        // END-EXAMPLE: layout.absolutelayout.zindex
        
        return layout;
    }
    
    Layout proportional() {
        AbsoluteLayout layout = new AbsoluteLayout();
        layout.setWidth("400px");
        layout.setHeight("300px");
        
        // BEGIN-EXAMPLE: layout.absolutelayout.proportional
        // A panel that takes 30% to 90% horizontally and
        // 20% to 80% vertically
        Panel panel = new Panel("A Panel");
        panel.setSizeFull(); // Fill the specified area
        layout.addComponent(panel, "left: 30%; right: 10%;" +
                                   "top: 20%; bottom: 20%;");
        
        Label topLabel = new Label("0 to 20%");
        topLabel.setSizeFull();
        layout.addComponent(topLabel, "left: 30%; right: 10%;"+
                                      "top: 0px; bottom: 80%;");

        Label leftLabel = new Label("0 to 30%");
        leftLabel.setSizeFull();
        layout.addComponent(leftLabel, "left: 0px; right: 70%;"+
                                       "top: 20%; bottom: 20%;");

        Label rightLabel = new Label("90 to 100%");
        rightLabel.setSizeFull();
        layout.addComponent(rightLabel, "left: 90%; right: 0px;"+
                                        "top: 20%; bottom: 20%;");

        Label bottomLabel = new Label("80 to 100%");
        bottomLabel.setSizeFull();
        layout.addComponent(bottomLabel, "left: 30%; right: 10%;"+
                                         "top: 80%; bottom: 0px;");
        // END-EXAMPLE: layout.absolutelayout.proportional
        
        return layout;
    }
}
