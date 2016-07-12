package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class PanelExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 3321998143334619838L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic();
        else if ("complex".equals(context))
            complex(layout);
        else if ("scroll".equals(context))
            scroll(layout);
        else if ("scrollbars".equals(context))
            scrollbars(layout);
        else if ("styling".equals(context))
            styling();
        else if ("light".equals(context))
            light(layout);
        else
            setCompositionRoot(new Label("Error"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void basic() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.panel.basic
        final Panel panel = new Panel("Panel");
        layout.addComponent(panel);
        
        panel.setContent(new Label("Here is some content"));
        // END-EXAMPLE: layout.panel.basic
        
        setCompositionRoot(layout);
    }
    
    void complex(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.panel.complex
        Panel panel = new Panel("Astronomer Panel");
        panel.addStyleName("mypanelexample");
        panel.setSizeUndefined(); // Shrink to fit content
        layout.addComponent(panel);
        
        // Create the content
        FormLayout content = new FormLayout();
        content.addStyleName("mypanelcontent");
        content.addComponent(new TextField("Participant"));
        content.addComponent(new TextField("Organization"));
        content.setSizeUndefined(); // Shrink to fit
        content.setMargin(true);
        panel.setContent(content);
        // END-EXAMPLE: layout.panel.complex
        
        setCompositionRoot(layout);
    }

    public void scrollbars(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: layout.panel.scrollbars
        // Display an image stored in theme
        Image image = new Image(null,
            new ThemeResource("img/Ripley_Scroll-300px.jpg"));
        
        // To enable scrollbars, the size of the panel content
        // must not be relative to the panel size
        image.setSizeUndefined(); // Actually the default

        // The panel will give it scrollbars.
        Panel panel = new Panel("Scroll");
        panel.setWidth("300px");
        panel.setHeight("300px");
        panel.setContent(image);

        layout.addComponent(panel);
        // END-EXAMPLE: layout.panel.scrollbars
    }
    
    void scroll(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.panel.scroll
        // A panel with fixed height
        final Panel panel = new Panel("Scrolling Panel");
        panel.setHeight("300px");
        panel.setWidth(null); // Fit to content width

        // Content with known size of 300x2918 pixels
        final int contentHeight = 2918; 
        Image content = new Image(null,
            new ThemeResource("img/Ripley_Scroll-300px.jpg"));
        panel.setContent(content);
        layout.addComponent(panel);

        HorizontalLayout scrollButtons = new HorizontalLayout();
        layout.addComponent(scrollButtons);
        final int scrollStep = 100;
        
        Button scrollUp = new Button("Scroll Up");
        scrollUp.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 8557421620094669457L;

            public void buttonClick(ClickEvent event) {
                int scrollPos = panel.getScrollTop() - scrollStep;
                if (scrollPos < 0)
                    scrollPos = 0;
                panel.setScrollTop(scrollPos);
            }
        });
        scrollButtons.addComponent(scrollUp);
        
        Button scrollDown = new Button("Scroll Down");
        scrollDown.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 4653752867355423116L;

            public void buttonClick(ClickEvent event) {
                int scrollPos = panel.getScrollTop();
                if (scrollPos > contentHeight)
                    scrollPos = contentHeight;
                panel.setScrollTop(scrollPos + scrollStep);
            }
        });
        scrollButtons.addComponent(scrollDown);

        // Report current scroll position
        layout.addComponent(new Button("Scroll Position", e ->
            Notification.show("Scroll position: " +
                              panel.getScrollTop())));
        // END-EXAMPLE: layout.panel.scroll
    }

    void styling() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.panel.styling
        final Panel panel = new Panel("Stylish Panel");
        panel.addStyleName("mypanelexample");
        layout.addComponent(panel);
        
        VerticalLayout content = new VerticalLayout();
        content.addStyleName("mypanelcontent");
        panel.setContent(content);

        content.addComponent(new Label("Here is some content"));
        // END-EXAMPLE: layout.panel.styling
        
        
        setCompositionRoot(layout);
    }

    void light(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.panel.light
        final Panel panel = new Panel("Light Panel");
        panel.addStyleName(Reindeer.PANEL_LIGHT);
        layout.addComponent(panel);
        
        panel.setContent(new Label("Here is some content"));
        // END-EXAMPLE: layout.panel.light
    }
}
