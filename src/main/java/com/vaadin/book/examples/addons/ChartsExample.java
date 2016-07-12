package com.vaadin.book.examples.addons;

import org.vaadin.arraycontainer.ArrayContainer;
import org.vaadin.gridview.GridView;
import org.vaadin.gridview.data.FiniteGrid;
import org.vaadin.minichat.MiniChatManager;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

public class ChartsExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        if ("arraycontainer".equals(context))
            arraycontainer(layout);
        else if ("gridview".equals(context))
            gridview(layout);
        else if ("questiontree".equals(context))
            questiontree(layout);
        else if ("minichat".equals(context))
            minichat(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    void arraycontainer (VerticalLayout layout) {
        // BEGIN-EXAMPLE: addons.test.arraycontainer
        ListSelect select = new ListSelect("Select", new ArrayContainer<String>(new String[]{"One", "Two", "Three"}));
        layout.addComponent(select);
        // END-EXAMPLE: addons.test.arraycontainer
    }

    void gridview (VerticalLayout layout) {
        // BEGIN-EXAMPLE: addons.test.arraycontainer
        FiniteGrid data = new FiniteGrid(50, 50);
        data.set(40, 30, 1);
        
        GridView grid = new GridView(data);
        grid.setWidth("500px");
        grid.setHeight("500px");
        layout.addComponent(grid);
        // END-EXAMPLE: addons.test.arraycontainer
    }

    void questiontree (VerticalLayout layout) {
        // BEGIN-EXAMPLE: addons.test.arraycontainer
        layout.addComponent(new Label("Not implemented"));
        // END-EXAMPLE: addons.test.arraycontainer
    }

    void minichat (VerticalLayout layout) {
        // BEGIN-EXAMPLE: addons.test.minichat
        MiniChatManager chat = new MiniChatManager();
        layout.addComponent(chat);
        // END-EXAMPLE: addons.test.minichat
    }
}    