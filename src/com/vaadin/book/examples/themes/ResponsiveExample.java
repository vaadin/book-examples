package com.vaadin.book.examples.themes;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ResponsiveExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("flexwrap".equals(context))
            flexwrap(layout);
        else if ("wrapgrid".equals(context))
            wrapgrid(layout);
        else if ("display".equals(context))
            display(layout);
        else if ("complex".equals(context))
            complex(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout content) {
        // BEGIN-EXAMPLE: themes.responsive.basic
        // Have some component with an appropriate style name
        Label c = new Label("Here be text");
        c.addStyleName("myresponsive");
        c.setResponsive(true); // Enable responsive selectors
        content.addComponent(c);
        // END-EXAMPLE: themes.responsive.basic
    }
    
    void flexwrap(VerticalLayout content) {
        // BEGIN-EXAMPLE: themes.responsive.flexwrap
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("flexwrap");
        content.addComponent(layout);
        
        // Enable Responsive CSS selectors for the layout
        Responsive.makeResponsive(layout);

        Label title = new Label("Space is big, really big");
        title.addStyleName("title");
        layout.addComponent(title);

        Label description = new Label("This is a " +
            "long description of the image shown " +
            "on the right or below, depending on the " +
            "screen width. The text here could continue long.");
        description.addStyleName("itembox");
        description.setSizeUndefined();
        layout.addComponent(description);
        
        Image image = new Image(null, new ThemeResource("img/planets/Earth.jpg"));
        image.addStyleName("itembox");
        layout.addComponent(image);
        // END-EXAMPLE: themes.responsive.flexwrap
    }

    void wrapgrid(VerticalLayout content) {
        // BEGIN-EXAMPLE: themes.responsive.wrapgrid
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("wrapgrid");
        content.addComponent(layout);
        
        // Enable Responsive CSS selectors for the layout
        Responsive.makeResponsive(layout);

        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet: planets) {
            Image image = new Image(null,
                new ThemeResource("img/planets/"+planet+".jpg"));
            image.addStyleName("box");
            layout.addComponent(image);
        }
        // END-EXAMPLE: themes.responsive.wrapgrid
    }
    
    void display(VerticalLayout content) {
        // BEGIN-EXAMPLE: themes.responsive.display
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("toggledisplay");
        content.addComponent(layout);
        
        // Enable Responsive CSS selectors for the layout
        Responsive.makeResponsive(layout);

        Label enoughspace =
            new Label("This space is big, mindbogglingly big");
        enoughspace.addStyleName("enoughspace");
        layout.addComponent(enoughspace);
        
        Label notenoughspace = new Label("Quite small space");
        notenoughspace.addStyleName("notenoughspace");
        layout.addComponent(notenoughspace);
        // END-EXAMPLE: themes.responsive.display
    }
    
    void complex(VerticalLayout content) {
        // BEGIN-EXAMPLE: themes.responsive.complex
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        layout.addStyleName("veryresponsive");
        content.addComponent(layout);
        
        // Enable Responsive CSS selectors for the layout
        Responsive.makeResponsive(layout);

        Label enoughspace =
                new Label("Space is a big place, really big");
        enoughspace.addStyleName("enoughspace");
        layout.addComponent(enoughspace);
        
        Label notenoughspace = new Label("Quite little space");
        notenoughspace.addStyleName("notenoughspace");
        layout.addComponent(notenoughspace);

        Label description = new Label("This is a " +
                "long description of the image shown " +
                "on the right or below, depending on the " +
                "screen width. The text here could continue long.");
        description.addStyleName("itembox");
        description.setSizeUndefined();
        layout.addComponent(description);
        
        Image image = new Image(null, new ThemeResource("img/planets/Earth.jpg"));
        image.addStyleName("itembox");
        layout.addComponent(image);
        // END-EXAMPLE: themes.responsive.complex
    }
}