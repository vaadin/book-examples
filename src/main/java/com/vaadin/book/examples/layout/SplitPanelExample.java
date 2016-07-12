package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

public class SplitPanelExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8458669675797366833L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        addStyleName("splitpanelexample");

        if ("basic".equals(context))
            basic(layout);
        else if ("splitposition".equals(context))
            splitposition(layout);
        else if ("small".equals(context))
            small(layout);
        else if ("styling".equals(context))
            styling(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        HorizontalLayout hlayout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: layout.splitpanel.basic
        // Have some layout to split - could be Window as well
        Panel hpanel = new Panel("Horizontal Split");

        // Have a horizontal split panel as its root layout
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hpanel.setContent(hsplit);

        // Put a tree in the left panel
        Tree tree = new Tree("Menu", TreeExample.createTreeContent());
        hsplit.setFirstComponent(tree);

        // Put a label in the right panel
        hsplit.setSecondComponent(new Label("Here's the right panel"));

        // In a similar way, split a layout vertically
        Panel vpanel = new Panel("Vertical Split");
        VerticalSplitPanel vsplit = new VerticalSplitPanel();
        vpanel.setContent(vsplit);
        
        // Use addComponent() to add the components in order
        vsplit.addComponent(new Label("Here's the upper panel"));
        vsplit.addComponent(new Label("Here's the lower panel"));
        // END-EXAMPLE: layout.splitpanel.basic
        
        hpanel.setWidth("200px");
        hpanel.setHeight("200px");
        hlayout.addComponent(hpanel);
        
        vpanel.setWidth("200px");
        vpanel.setHeight("200px");
        hlayout.addComponent(vpanel);
        
        hlayout.setSpacing(true);
        layout.addComponent(hlayout);
    }
    
    void splitposition(VerticalLayout layout) {
        // Have some layout to split - could be Window as well
        Panel hpanel = new Panel("Locked Split Position");

        // BEGIN-EXAMPLE: layout.splitpanel.splitposition
        // Have a horizontal split panel
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setFirstComponent(new Label("75% wide panel"));
        hsplit.setSecondComponent(new Label("25% wide panel"));

        // Set the position of the splitter as percentage
        hsplit.setSplitPosition(75, Unit.PERCENTAGE);
        
        // Lock the splitter
        hsplit.setLocked(true);
        // END-EXAMPLE: layout.splitpanel.splitposition
        
        hpanel.setContent(hsplit);
        hpanel.setWidth("200px");
        hpanel.setHeight("200px");
        layout.addComponent(hpanel);
    }
    
    void combined(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.splitpanel.basic
        // Have a panel to put stuff in
        Panel panel = new Panel("Split Panels Inside This Panel");

        // Have a horizontal split panel as its root layout
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        panel.setContent(hsplit);

        // Put a component in the left panel
        Tree tree = new Tree("Menu", TreeExample.createTreeContent());
        hsplit.setFirstComponent(tree);

        // Put a vertical split panel in the right panel
        VerticalSplitPanel vsplit = new VerticalSplitPanel();
        hsplit.setSecondComponent(vsplit);

        // Put other components in the right panel
        vsplit.addComponent(new Label("Here's the upper panel"));
        vsplit.addComponent(new Label("Here's the lower panel"));
        // END-EXAMPLE: layout.splitpanel.basic

        panel.setWidth("400px");
        panel.setHeight("200px");
        
        layout.addComponent(panel);
    }
    
    void small(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.splitpanel.small
        HorizontalSplitPanel splitpanel = new HorizontalSplitPanel();
        
        // Set the 1 pixel wide small style
        splitpanel.addStyleName(Reindeer.SPLITPANEL_SMALL);
        
        // Disallow moving the divider
        splitpanel.setLocked(true);
        // END-EXAMPLE: layout.splitpanel.small
        
        // Put a component in the left panel
        Tree tree = new Tree("Menu", TreeExample.createTreeContent());
        splitpanel.addComponent(tree);
        
        // Put another component in the right panel
        Label someText = new Label("Here's some text");
        splitpanel.setSecondComponent(someText);

        // Put it in a panel
        Panel panel = new Panel("SplitPanel with Small Divider inside a Panel");
        panel.setContent(splitpanel);

        // The containing layout must have defined size because
        // SplitPanel has relative 100%x100% size.
        panel.setWidth("400px");
        panel.setHeight("200px");
        
        layout.addComponent(panel);
    }

    void styling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.splitpanel.styling
        // FORUM: http://vaadin.com/forum/-/message_boards/message/219031
        HorizontalSplitPanel splitpanel = new HorizontalSplitPanel();
        
        // Use a custom style
        splitpanel.addStyleName("invisiblesplitter");
        // END-EXAMPLE: layout.splitpanel.styling
        
        // Put a component in the left panel
        Tree tree = new Tree("Menu", TreeExample.createTreeContent());
        splitpanel.addComponent(tree);
        
        // Put another component in the right panel
        Label someText = new Label("Here's some content");
        splitpanel.setSecondComponent(someText);

        // Put it in a panel
        Panel panel = new Panel("Dividerless SplitPanel inside a Panel");
        panel.setContent(splitpanel);

        // The containing layout must have defined size because
        // SplitPanel has relative 100%x100% size.
        panel.setWidth("400px");
        panel.setHeight("200px");
        
        layout.addComponent(panel);
    }
}
