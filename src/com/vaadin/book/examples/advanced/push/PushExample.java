package com.vaadin.book.examples.advanced.push;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PushExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9754344337L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("pusharound".equals(context))
            pusharound(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void basic (VerticalLayout layout) {
        // EXAMPLE-REF: advanced.push.basic com.vaadin.book.examples.advanced.PushyUI advanced.push.basic
        // BEGIN-EXAMPLE: advanced.push.basic
        BrowserFrame frame = new BrowserFrame("A UI");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/pushy"));
        frame.setWidth("600px");
        frame.setHeight("350px");
        layout.addComponent(frame);
        // END-EXAMPLE: advanced.push.basic
    }

    void pusharound (VerticalLayout layout) {
        // EXAMPLE-REF: advanced.push.pusharound com.vaadin.book.examples.advanced.push.PushAroundUI advanced.push.pusharound
        // EXAMPLE-REF: advanced.push.pusharound com.vaadin.book.examples.advanced.push.Broadcaster advanced.push.pusharound
        // BEGIN-EXAMPLE: advanced.push.pusharound
        HorizontalLayout uis = new HorizontalLayout();
        uis.setSpacing(true);
        
        BrowserFrame frame1 = new BrowserFrame("UI 1");
        frame1.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/pusharound"));
        frame1.setWidth("400px");
        frame1.setHeight("350px");
        uis.addComponent(frame1);

        BrowserFrame frame2 = new BrowserFrame("UI 2");
        frame2.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/pusharound"));
        frame2.setWidth("400px");
        frame2.setHeight("350px");
        uis.addComponent(frame2);
        
        layout.addComponent(uis);
        // END-EXAMPLE: advanced.push.pusharound
    }
}
