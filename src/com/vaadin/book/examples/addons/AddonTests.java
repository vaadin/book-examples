package com.vaadin.book.examples.addons;

import org.vaadin.arraycontainer.ArrayContainer;
import org.vaadin.csvalidation.CSValidator;
import org.vaadin.gridview.GridView;
import org.vaadin.gridview.data.FiniteGrid;
import org.vaadin.minichat.MiniChatManager;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddonTests extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        if ("csvalidation".equals(context))
            csvalidation(layout);
        else if ("arraycontainer".equals(context))
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
    
    void csvalidation(VerticalLayout layout) {
        // BEGIN-EXAMPLE: misc.addons.csvalidation
        TextField tf = new TextField("Numbers Only");

        CSValidator validator = new CSValidator();
        String fn_regexp = "^\\d+$";
        validator.setRegExp(fn_regexp);
        validator.setErrorMessage("Enter a number");
        validator.setPreventInvalidTyping(true);
        validator.extend(tf);
        
        layout.addComponent(tf);
        // END-EXAMPLE: misc.addons.csvalidation
    }

    void arraycontainer(VerticalLayout layout) {
        // BEGIN-EXAMPLE: misc.addons.arraycontainer
        ListSelect select = new ListSelect("Select", new ArrayContainer<String>(new String[]{"One", "Two", "Three"}));
        layout.addComponent(select);
        // END-EXAMPLE: misc.addons.arraycontainer
    }

    void gridview(VerticalLayout layout) {
        // BEGIN-EXAMPLE: misc.addons.gridview
        FiniteGrid data = new FiniteGrid(50, 50);
        data.set(40, 30, 1);
        
        GridView grid = new GridView(data);
        grid.setWidth("500px");
        grid.setHeight("500px");
        layout.addComponent(grid);
        // END-EXAMPLE: misc.addons.gridview
    }

    void questiontree (VerticalLayout layout) {
        // BEGIN-EXAMPLE: misc.addons.arraycontainer
        layout.addComponent(new Label("Not implemented"));
        // END-EXAMPLE: misc.addons.arraycontainer
    }

    void minichat (VerticalLayout layout) {
        // BEGIN-EXAMPLE: misc.addons.minichat
        MiniChatManager chat = new MiniChatManager();
        layout.addComponent(chat);
        // END-EXAMPLE: misc.addons.minichat
    }
}    