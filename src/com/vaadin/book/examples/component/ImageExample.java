package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

public class ImageExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3644786684841597587L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("scrolling-css".equals(context))
            scrollingcss(layout);
        setCompositionRoot(layout);
    }
    
    public void basic(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.image.basic
        // Serve an image from the theme
        Resource res = new ThemeResource("img/reindeer-128px.png");
        
        // Display the image
        Image image = new Image("My Image", res);
        layout.addComponent(image);
        // END-EXAMPLE: component.embedded.image.basic
    }

    public void scrollingcss(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.scrolling-css
        // Serve a big image (300x2918 pixels) from the theme
        Resource rsrc = new ThemeResource("img/Ripley_Scroll-300px.jpg");
        
        // Wrap the image in a component container
        class ScrollImage extends CustomComponent {
            private static final long serialVersionUID = 2396650925631524507L;

            public ScrollImage(String caption, Resource resource) {
                setCaption(caption);
                addStyleName("scrollable");
                setCompositionRoot(new Image(null, resource));
            }
        }
        ScrollImage image = new ScrollImage("My Scroll", rsrc);
        image.setWidth("330px");
        image.setHeight("300px");

        layout.addComponent(image);
        // END-EXAMPLE: component.embedded.scrolling-css
    }
}
