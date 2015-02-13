package com.vaadin.book.examples.application.declarative;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@SuppressWarnings("serial")
@Theme("valo")
// BEGIN-EXAMPLE: application.declarative.designroot
public class DeclarativeViewUI extends UI {
    @WebServlet(value = "/declarativeviewui/*",
                asyncSupported = true)
    @VaadinServletConfiguration(
                productionMode = false,
                ui = DeclarativeViewUI.class)
    public static class Servlet extends VaadinServlet {
    }
    
    @DesignRoot
    public class MyViewDesign extends VerticalLayout
           implements View {
        Tree mytree;
        Table mytable;
        
        public MyViewDesign() {
            Design.read("MyDeclarativeUI.html", this);
        }

        @Override
        public void enter(ViewChangeEvent event) {
            // Show some (example) data
            mytree.setContainerDataSource(
                TreeExample.createTreeContent());
            mytable.setContainerDataSource(
                TableExample.generateContent());
            
            // Some interaction
            mytree.addItemClickListener(click -> // Java 8
                Notification.show("Selected " +
                    click.getItemId()));
        }
    }
    
    @Override
    protected void init(VaadinRequest request) {
        setContent(new MyViewDesign());
    }
}
// END-EXAMPLE: application.declarative.designroot
