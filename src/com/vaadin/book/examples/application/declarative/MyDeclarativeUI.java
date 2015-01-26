package com.vaadin.book.examples.application.declarative;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.declarative.DesignContext;

@SuppressWarnings("serial")
@Theme("myproject")
// BEGIN-EXAMPLE: application.declarative.context
public class MyDeclarativeUI extends UI {
    @WebServlet(value = "/declarativeui/*",
                asyncSupported = true)
    @VaadinServletConfiguration(
                productionMode = false,
                ui = MyDeclarativeUI.class)
    public static class Servlet extends VaadinServlet {
    }
    
    Tree mytree;
    Table mytable;

    @Override
    protected void init(VaadinRequest request) {
        DesignContext design = Design.read(getClass().
            getResourceAsStream("MyDeclarativeUI.html"),
            null);
        setContent(design.getRootComponent());
        
        // Get the components from the design
        mytree = (Tree) design.getComponentByLocalId("mytree");
        mytable = (Table) design.getComponentByLocalId("mytable");

        // Bind the data display components to (example) data
        mytree.setContainerDataSource(
            TreeExample.createTreeContent());
        mytable.setContainerDataSource(
            TableExample.generateContent());
    }
}
// END-EXAMPLE: application.declarative.context
