package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ExpandRatioExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9106115858126838561L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        addStyleName("expandratioexample");

        if ("basic".equals(context))
            basic(layout);
        else if ("horizontal".equals(context))
            horizontal(layout);
        else if ("summary".equals(context))
            summary(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public static String basicDescription =
        "<h1>Expand Ratio</h1>"+
        "<p>The <i>expand ratio</i> specified how large portion of unused space "+
        "in a layout is given to each component.</p>"+
        "<p>See also the description of the layouts supporting expand ratios:</p>"+
        "<ul>"+
        "<li><b>VerticalLayout</b></li>"+
        "<li><b>HorizontalLayout</b></li>"+
        "<li><a href='#layout.gridlayout.expandratio'><b>GridLayout</b></a></li>"+
        "</ul>";
    void basic(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: layout.formatting.expandratio.basic
        // FORUM: http://vaadin.com/forum/-/message_boards/message/220232
        // A containing panel - this could just as well be
        // the main window (Window inherits Panel)
        Panel panel = new Panel("A Containing Panel");
        
        // The panel has some defined size just like a browser
        // window would have
        panel.setWidth("400px");
        panel.setHeight("300px");
        
        // Have the panel's root layout takes all space available
        // in the panel (it wouldn't otherwise do so)
        VerticalLayout layout = (VerticalLayout) panel.getContent();
        layout.setSizeFull();

        // Put some regular component in it
        TextField name = new TextField("Name");
        layout.addComponent(name);
        
        // Put a table with some data in it
        Table table = new Table("My Ever-Expanding Table");
        layout.addComponent(table);
        
        // Set the table to expand and take all the available space in
        // the containing layout.
        table.setSizeFull();
        layout.setExpandRatio(table, 1.0f);
        // END-EXAMPLE: layout.formatting.expandratio.basic

        table.setContainerDataSource(TableExample.generateContent());
        
        // Some cosmetics
        layout.setMargin(true);
        layout.setSpacing(true);
        
        rootlayout.addComponent(panel);
    }
    
    void horizontal(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: layout.formatting.expandratio.horizontal
        // FORUM: http://vaadin.com/forum/-/message_boards/message/220232
        // A containing panel - this could just as well be
        // the main window (Window inherits Panel)
        Panel panel = new Panel("A Containing Panel");
        
        // The panel has some defined size just like a browser
        // window would have
        panel.setWidth("600px");
        panel.setHeight("300px");
        
        // Have the panel's root layout takes all space available
        // in the panel (it wouldn't otherwise do so)
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        panel.setContent(layout);

        // Put a table with some data in it
        Table table = new Table("My Shrinking Table");
        layout.addComponent(table);
        
        // An expanding component
        Panel expanding = new Panel("Expanding Panel");
        expanding.setContent(new Label("This stuff expands"));
        expanding.setSizeUndefined();
        layout.addComponent(expanding);
        // END-EXAMPLE: layout.formatting.expandratio.horizontal

        table.setContainerDataSource(TableExample.generateContent());
        
        // Some cosmetics
        layout.setMargin(true);
        layout.setSpacing(true);
        
        rootlayout.addComponent(panel);
    }

    void summary(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.formatting.expandratio.summary
        FormLayout parentLayout = new FormLayout();
        
        HorizontalLayout fittingLayout = new HorizontalLayout();
        fittingLayout.setWidth(null); // Undefined is actually default
        fittingLayout.addComponent(new Button("Small"));
        fittingLayout.addComponent(new Button("Medium-sized"));
        fittingLayout.addComponent(new Button("Quite a big component"));
        parentLayout.addComponent(fittingLayout);

        /*********************************************************************/

        HorizontalLayout fixedLayout = new HorizontalLayout();
        fixedLayout.setWidth("450px"); 
        fixedLayout.addComponent(new Button("Small"));
        fixedLayout.addComponent(new Button("Medium-sized"));
        fixedLayout.addComponent(new Button("Quite a big component"));
        parentLayout.addComponent(fixedLayout);

        /*********************************************************************/
        
        HorizontalLayout fullLayout = new HorizontalLayout();
        fullLayout.setWidth("450px");
         
        // All the buttons take 100% of the available size
        Button button1 = new Button("Small");
        button1.setWidth("100%");
        fullLayout.addComponent(button1);
        Button button2 = new Button("Medium-sized");
        button2.setWidth("100%");
        fullLayout.addComponent(button2);
        Button button3 = new Button("Quite a big component");
        button3.setWidth("100%");
        fullLayout.addComponent(button3);

        parentLayout.addComponent(fullLayout);        

        /*********************************************************************/
        
        HorizontalLayout unbalancedLayout = new HorizontalLayout();
        unbalancedLayout.setWidth("450px");
         
        // These buttons take the minimum size.
        unbalancedLayout.addComponent(new Button("Small"));
        unbalancedLayout.addComponent(new Button("Medium-sized"));
         
        // This button will expand.
        Button expandButton = new Button("Expanding component");
         
        // Use 100% of the expansion cell's width.
        expandButton.setWidth("100%");
         
        // The component must be added to layout before setting the ratio.
        unbalancedLayout.addComponent(expandButton);
         
        // Set the component's cell to expand.
        unbalancedLayout.setExpandRatio(expandButton, 1.0f);
         
        parentLayout.addComponent(unbalancedLayout);        
        // END-EXAMPLE: layout.formatting.expandratio.summary

        parentLayout.addStyleName("expandratioexample");

        fittingLayout.setCaption("Undefined width layout:");
        fittingLayout.addStyleName("redaroundyellow");
        
        fixedLayout.setCaption("Fixed width layout:");
        fixedLayout.addStyleName("redaroundyellow");
        
        fullLayout.setCaption("Full-sized components:");
        fullLayout.addStyleName("redaroundyellow");
        
        unbalancedLayout.setCaption("Expanding component:");
        unbalancedLayout.addStyleName("redaroundyellow");
        
        layout.addComponent(parentLayout);
    }
}
