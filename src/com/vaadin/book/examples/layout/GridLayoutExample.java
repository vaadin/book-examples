package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;

public class GridLayoutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2893838661604268626L;
    
    public void init (String context) {
        if ("basic".equals(context))
            basic();
        else if ("expandratio".equals(context))
            expandRatio();
        else
            setCompositionRoot(new Label("Error"));
    }
    
    void basic() {
        // BEGIN-EXAMPLE: layout.gridlayout.basic
        // Create a 4 by 4 grid layout
        final GridLayout grid = new GridLayout(4, 4);

        // Fill out the first row using the cursor
        grid.addComponent(new Button("R/C 1"));
        for (int i = 0; i < 3; i++)
            grid.addComponent(new Button("Col " + (grid.getCursorX() + 1)));

        // Fill out the first column using coordinates
        for (int i = 1; i < 4; i++)
            grid.addComponent(new Button("Row " + i), 0, i);

        // Add some components of various shapes.
        grid.addComponent(new Button("3x1 button"), 1, 1, 3, 1);
        grid.addComponent(new Label("1x2 cell"),    1, 2, 1, 3);
        final InlineDateField date = new InlineDateField("A 2x2 date field");
        date.setResolution(Resolution.DAY);
        grid.addComponent(date, 2, 2, 3, 3);

        // Needed for the grid borders
        grid.addStyleName("basic-gridlayout");
        grid.setMargin(true);
        grid.setSizeUndefined(); // This isn't enough
        setSizeUndefined();      // This is needed

        // END-EXAMPLE: layout.gridlayout.basic
        setCompositionRoot(grid);
    }
    
    void manipulation() {
        final GridLayout grid = new GridLayout(4, 4);

        grid.removeRow(1);
        grid.insertRow(1);
        
        setCompositionRoot(grid);
    }
    
    void expandRatio() {
        // BEGIN-EXAMPLE: layout.gridlayout.expandratio
        GridLayout grid = new GridLayout(3,2);

        grid.addStyleName("expandratio");
        grid.setMargin(true);
        
        // Layout containing relatively sized components must have
        // a defined size, here a fixed size.
        grid.setWidth("600px");
        grid.setHeight("200px");
        
        // Add some content
        String labels [] = {
                "Shrinking column<br/>Shrinking row",
                "Expanding column (1:)<br/>Shrinking row",
                "Expanding column (5:)<br/>Shrinking row",
                "Shrinking column<br/>Expanding row",
                "Expanding column (1:)<br/>Expanding row",
                "Expanding column (5:)<br/>Expanding row"
        };
        for (int i=0; i<labels.length; i++) {
            Label label = new Label(labels[i], ContentMode.HTML);
            label.setWidth(null); // Set width as undefined
            grid.addComponent(label);
        }
        
        // Set different expansion ratios for the two columns
        grid.setColumnExpandRatio(1, 1);
        grid.setColumnExpandRatio(2, 5);
        
        // Set the bottom row to expand
        grid.setRowExpandRatio(1, 1);
        
        // Align and size the labels.
        for (int col=0; col<grid.getColumns(); col++) {
            for (int row=0; row<grid.getRows(); row++) {
                Component c = grid.getComponent(col, row);
                grid.setComponentAlignment(c, Alignment.TOP_CENTER);
                
                // Make the labels high to illustrate the empty
                // horizontal space.
                if (col != 0 || row != 0)
                    c.setHeight("100%");
            }
        }
        // END-EXAMPLE: layout.gridlayout.expandratio
        
        setCompositionRoot(grid);
    }
}
