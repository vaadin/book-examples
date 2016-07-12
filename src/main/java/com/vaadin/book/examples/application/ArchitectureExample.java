package com.vaadin.book.examples.application;

import java.io.File;
import java.util.Locale;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ArchitectureExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("extending".equals(context))
            extending(layout);
        else if ("layout".equals(context))
            layouts(layout);
        else if ("customcomponent".equals(context))
            customcomponent(layout);
        else if ("globalaccess".equals(context))
            globalaccess(layout);
        setCompositionRoot(layout);
    }

    void extending(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.architecture.inheritance.particular
        class PlanetName extends TextField {
            private static final long serialVersionUID = 4648791095857453860L;
            
            public PlanetName() {
                super("Planet Name");
                setImmediate(true);
            }
        }
        
        TextField planetName = new PlanetName();
        layout.addComponent(planetName);
        // END-EXAMPLE: application.architecture.inheritance.particular
    }

    void layouts(VerticalLayout toplayout) {
        // BEGIN-EXAMPLE: application.architecture.composition.layout
        class MyView extends VerticalLayout {
            private static final long serialVersionUID = 5947517944112735964L;

            TextField entry   = new TextField("Enter this");
            Label     display = new Label("See this");
            Button    click   = new Button("Click This");

            public MyView() {
                addComponent(entry);
                addComponent(display);
                addComponent(click);

                // Configure it a bit
                setSizeFull();
                addStyleName("myview");
            }
        }
        
        // Use it
        Layout myview = new MyView();
        // END-EXAMPLE: application.architecture.composition.layout
        toplayout.addComponent(myview);
    }
    
    void customcomponent(VerticalLayout toplayout) {
        // BEGIN-EXAMPLE: application.architecture.composition.customcomponent
        class MyView extends CustomComponent {
            private static final long serialVersionUID = 5947517944112735964L;

            TextField entry   = new TextField("Enter this");
            Label     display = new Label("See this");
            Button    click   = new Button("Click This");

            public MyView() {
                Layout layout = new VerticalLayout();
                
                layout.addComponent(entry);
                layout.addComponent(display);
                layout.addComponent(click);
                
                setCompositionRoot(layout);
                
                setSizeFull();
            }
        }
        
        // Use it
        MyView myview = new MyView();
        // END-EXAMPLE: application.architecture.composition.customcomponent
        toplayout.addComponent(myview);
    }

    @SuppressWarnings({ "unused" })
    void globalaccess(VerticalLayout toplayout) {
        // BEGIN-EXAMPLE: application.architecture.globalaccess
        // Set the default locale of the UI
        UI.getCurrent().setLocale(new Locale("en"));

        // Set the page title (window or tab caption)
        Page.getCurrent().setTitle("My Page");
        
        // Set a session attribute
        VaadinSession.getCurrent().setAttribute("myattrib", "hello");

        // Get the name of the servlet
        String name = VaadinServlet.getCurrent().getServletName();

        // Access the HTTP service parameters
        File baseDir = VaadinService.getCurrent().getBaseDirectory();
        // END-EXAMPLE: application.architecture.globalaccess
        
        toplayout.addComponent(new Label("Nothing to see here except the code"));
    }
}
