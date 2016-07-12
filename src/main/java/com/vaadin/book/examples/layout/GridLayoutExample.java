package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.BookExampleBundle;
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
        GridLayout grid = new GridLayout(4, 4);

        Button button11 = new Button("R/C 1");
        button11.setHeight("80%"); // This is pretty hacky (facepalm)
        button11.setWidth("85%");
        grid.addComponent(button11);

        // Fill out the first row using the cursor
        for (int col = 1; col <= 3; col++) {
            Button button = new Button("Col " + (col+1));
            if (col == 2 || col == 3)
                button.setWidth("90%");
            grid.addComponent(button);
        }

        // Fill out the first column using coordinates
        for (int row = 1; row <= 3; row++) {
            Button button = new Button("Row " + (row+1));
            if (row == 2 || row == 3)
                button.setHeight("90%");
            grid.addComponent(button, 0, row);
        }

        // Add some components of various shapes.
        Button button3x1 = new Button("3x1 button");
        button3x1.setWidth("95%");
        grid.addComponent(button3x1, 1, 1, 3, 1);

        Label label1x2 = new Label("1x2 cell");
        label1x2.setHeight("90%");
        grid.addComponent(label1x2, 1, 2, 1, 3);
        InlineDateField date = new InlineDateField("A 2x2 date field");
        date.setResolution(Resolution.DAY);
        grid.addComponent(date, 2, 2, 3, 3);
        
        grid.setColumnExpandRatio(2, 1.0f);
        grid.setColumnExpandRatio(3, 1.0f);

        // Needed for the grid borders
        grid.addStyleName("cellborders");
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

        grid.addStyleName("cellborders");
        grid.setMargin(false);
        
        // Layout containing relatively sized components must have
        // a defined size, here a fixed size.
        grid.setWidth("600px");
        grid.setHeight("200px");
        
        // Add some content
        String labels [][] = {
                {"Shrinking col<br/>Shrinking row",
                 "Expanding col (1:)<br/>Shrinking row",
                 "Expanding col (2:)<br/>Shrinking row"},
                {"Shrinking col<br/>Expanding row",
                 "Expanding col (1:)<br/>Expanding row",
                 "Expanding col (2:)<br/>Expanding row"}
        };
        for (int row = 0; row < grid.getRows(); row++)
            for (int col = 0; col < grid.getColumns(); col++) {
                Label label = new Label(labels[row][col], ContentMode.HTML);
                if (col == 0 && row == 1)
                    label.setWidth(null); // Shrink to fit
                else
                    label.setWidth("100%"); // (the default)
                grid.addComponent(label);
            }
        
        // Set different expansion ratios for the two columns
        // grid.setColumnExpandRatio(0, 0.5f);
        grid.setColumnExpandRatio(1, 1.0f);
        grid.setColumnExpandRatio(2, 2.0f);
        
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
