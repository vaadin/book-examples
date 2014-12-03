package com.vaadin.book.examples.advanced;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class NavigatorExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9754344337L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    // EXAMPLE-REF: advanced.navigator.basic com.vaadin.book.examples.advanced.NavigatorUI advanced.navigator.basic
    void basic (VerticalLayout layout) {
        Button button = new Button("Click to Open");
        layout.addComponent(button);
        
        BrowserWindowOpener opener = new BrowserWindowOpener(
            BookExamplesUI.APPCONTEXT + "/navigator");
        opener.setFeatures("width=640,height=480,resizable");
        opener.extend(button);
/*        layout.addComponent(,
           new Button.ClickListener() {
            private static final long serialVersionUID = -7037664490633263704L;

            @Override
            public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
                Page.getCurrent().open(
                   new ExternalResource(BookExamplesUI.APPCONTEXT +
                                        "/navigator?restartApplication"),
                                      "_blank", 640, 480, Page.BORDER_DEFAULT);
            }
        }));
*/    }
}
