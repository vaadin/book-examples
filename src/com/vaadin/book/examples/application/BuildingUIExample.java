package com.vaadin.book.examples.application;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class BuildingUIExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("hierarchical".equals(context))
            hierarchical(layout);

        setCompositionRoot(layout);
    }
    
    public static final String hierarchicalDescription =
            "<h1>Building UIs Hierarchically</h1>" +
            "<p></p>";
    
    // EXAMPLE-REF: application.architecture.hierarchical com.vaadin.book.applications.MyHierarchicalUI application.architecture.hierarchical
    void hierarchical(Layout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/buildingui?restartApplication"));
        frame.setWidth("570px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }
}
