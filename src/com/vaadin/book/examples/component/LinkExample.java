package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

public class LinkExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("target".equals(context))
            target(layout);
        else if ("popup".equals(context))
            popup(layout);
        else if ("css".equals(context))
            css(layout);
        else
            setCompositionRoot(new Label("Invalid context: " + context));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.link.basic
        // Textual link
        Link link = new Link("Click Me!",
                new ExternalResource("http://vaadin.com/"));
        
        // Image link
        Link iconic = new Link(null,
                new ExternalResource("http://vaadin.com/"));
        iconic.setIcon(new ThemeResource("img/nicubunu_Chain.png"));

        // Image + text link
        Link combo = new Link("To appease both literal and visual",
                new ExternalResource("http://vaadin.com/"));
        combo.setIcon(new ThemeResource("img/nicubunu_Chain.png"));
        // END-EXAMPLE: component.link.basic

        layout.addComponent(link);
        layout.addComponent(iconic);
        layout.addComponent(combo);
        layout.setSpacing(true);
        setCompositionRoot(layout);
    }
    
    void target(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.link.target
        // Hyperlink to a given URL
        Link link = new Link("Take me far away to a faraway land",
                new ExternalResource("http://vaadin.com/"));

        // Open the URL in a new window/tab
        link.setTargetName("_blank");
        
        // Indicate visually that it opens in a new window/tab
        link.setIcon(new ThemeResource("icons/external-link.png"));
        link.addStyleName("icon-after-caption");
        // END-EXAMPLE: component.link.target

        layout.addComponent(link);
        setCompositionRoot(layout);
    }
    
    void popup(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.link.popup
        // Hyperlink to a given URL
        Link link = new Link("Pop up like a poplar tree",
                new ExternalResource("http://vaadin.com/"));

        // Open the URL in a popup
        link.setTargetName("_blank");
        link.setTargetBorder(BorderStyle.NONE);
        link.setTargetHeight(300);
        link.setTargetWidth(400);
        
        // Indicate visually that it opens in a new window/tab
        link.setIcon(new ThemeResource("icons/external-link.png"));
        link.addStyleName("icon-after-caption");
        // END-EXAMPLE: component.link.popup

        layout.addComponent(link);
        setCompositionRoot(layout);
    }
    
    void css(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: component.link.css
        Link link = new Link("This is...Link!",
                new ExternalResource("http://vaadin.com/"));
        link.addStyleName("fancylink");
        // END-EXAMPLE: component.link.css
        rootlayout.addComponent(link);
    }
}
