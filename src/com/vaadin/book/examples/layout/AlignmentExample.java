package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class AlignmentExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 4598073828719119575L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("gridlayout".equals(context))
            gridlayout(layout);
        else if ("verticallayout".equals(context))
            verticallayout(layout);
        else if ("maxwidth".equals(context))
            maxwidth(layout);
        else
            layout.addComponent(new Label("Invalid Context"));
        setCompositionRoot(layout);

    }
    
    void gridlayout(VerticalLayout root) {
        // BEGIN-EXAMPLE: layout.formatting.alignment.gridlayout
        // Create a grid layout
        final GridLayout grid = new GridLayout(3, 3);
        grid.setWidth(400, Unit.PIXELS);
        grid.setHeight(200, Unit.PIXELS);
         
        Button topleft = new Button("Top Left");
        grid.addComponent(topleft, 0, 0);
        grid.setComponentAlignment(topleft, Alignment.TOP_LEFT);
         
        Button topcenter = new Button("Top Center");
        grid.addComponent(topcenter, 1, 0);
        grid.setComponentAlignment(topcenter, Alignment.TOP_CENTER);
         
        Button topright = new Button("Top Right");
        grid.addComponent(topright, 2, 0);
        grid.setComponentAlignment(topright, Alignment.TOP_RIGHT);

        Button middleleft = new Button("Middle Left");
        grid.addComponent(middleleft, 0, 1);
        grid.setComponentAlignment(middleleft, Alignment.MIDDLE_LEFT);
         
        Button middlecenter = new Button("Middle Center");
        grid.addComponent(middlecenter, 1, 1);
        grid.setComponentAlignment(middlecenter, Alignment.MIDDLE_CENTER);
         
        Button middleright = new Button("Middle Right");
        grid.addComponent(middleright, 2, 1);
        grid.setComponentAlignment(middleright, Alignment.MIDDLE_RIGHT);

        Button bottomleft = new Button("Bottom Left");
        grid.addComponent(bottomleft, 0, 2);
        grid.setComponentAlignment(bottomleft, Alignment.BOTTOM_LEFT);
         
        Button bottomcenter = new Button("Bottom Center");
        grid.addComponent(bottomcenter, 1, 2);
        grid.setComponentAlignment(bottomcenter, Alignment.BOTTOM_CENTER);
         
        Button bottomright = new Button("Bottom Right");
        grid.addComponent(bottomright, 2, 2);
        grid.setComponentAlignment(bottomright, Alignment.BOTTOM_RIGHT);
        // END-EXAMPLE: layout.formatting.alignment.gridlayout

        grid.addStyleName("basic-gridlayout");
        
        root.addComponent(grid);
    }

    void verticallayout(VerticalLayout root) {
        // BEGIN-EXAMPLE: layout.formatting.alignment.verticallayout
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("200px");
        layout.setHeight("200px");

        Button button = new Button("My Button");
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        
        // Put it inside something that has a border
        Panel panel = new Panel("It's in here");
        panel.setContent(layout);
        panel.setSizeUndefined(); // Shrink to fit
        // END-EXAMPLE: layout.formatting.alignment.verticallayout
        
        root.addComponent(panel);
    }
    
    public final static String maxwidthDescription =
            "<h1>A Panel That Expands to Maximum Width</h1>" +
            "<p>This is an experiment. It has a limitation that the containing element must have fixed width (not 100%).</p>";

    void maxwidth(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: layout.formatting.alignment.maxwidth
        // A containing panel - this could just as well be any layout.
        Panel panel = new Panel("A Containing Layout");
        panel.addStyleName("maxwidthexample");
        
        // The panel must have a defined width
        panel.setWidth("600px");
        
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setSpacing(true);
        panel.setContent(panelContent);

        for (int i=1; i<=7; i++) {
            Panel expandingPanel = new Panel("Expanding Panel");
            expandingPanel.addStyleName("expandingpanel");
            expandingPanel.setSizeUndefined();
            
            HorizontalLayout hlayout = new HorizontalLayout();
            for (int j=1; j<=i; j++) {
                Label label = new Label("...This stuff expands...");
                label.setSizeUndefined();
                hlayout.addComponent(label);
            }
            expandingPanel.setContent(hlayout);
    
            panel.setContent(expandingPanel);
            panelContent.setComponentAlignment(expandingPanel,
                                               Alignment.MIDDLE_CENTER);
        }
        // END-EXAMPLE: layout.formatting.alignment.maxwidth
        rootlayout.addComponent(panel);
    }    

}
