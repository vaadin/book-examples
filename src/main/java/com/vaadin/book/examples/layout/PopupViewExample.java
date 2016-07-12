package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.component.table.TableExample;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PopupViewExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9106115858126838561L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        addStyleName("expandratioexample");

        if ("basic".equals(context))
            basic(layout);
        else if ("programmatic".equals(context))
            programmatic(layout);
        else if ("visibilitylistener".equals(context))
            visibilitylistener(layout);
        else if ("subwindow".equals(context))
            subwindow(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    //public static String basicDescription =
    //    "";
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.basic
        // Content for the PopupView
        VerticalLayout popupContent = new VerticalLayout();
        popupContent.addComponent(new TextField("Textfield"));
        popupContent.addComponent(new Button("Button"));

        // The component itself
        PopupView popup = new PopupView("Open the popup", popupContent);
        layout.addComponent(popup);
        // END-EXAMPLE: layout.popupview.basic
    }
    
    // TODO Use this in book
    public static String compositionDescription =
        "<p>This shows how to pop up the view programmatically.</p>";
    void programmatic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.programmatic
        // A pop-up view without minimalized representation
        final PopupView popup = new PopupView(null,
            new Table(null, TableExample.generateContent()));
        
        // A component to open the view
        Button button = new Button("Show table", click -> // Java 8
             popup.setPopupVisible(true));

        layout.addComponents(button, popup);
        // END-EXAMPLE: layout.popupview.programmatic
    }

    // TODO Use this in book
    void visibilitylistener(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.visibilitylistener
        // Pop-up has empty placeholder at first
        VerticalLayout popupContent = new VerticalLayout();

        PopupView popup = new PopupView("Open the popup", popupContent);
        layout.addComponent(popup);
        
        // Fill the pop-up content when it's popped up
        popup.addPopupVisibilityListener(event -> { 
            if (event.isPopupVisible()) {
                popupContent.removeAllComponents();
                popupContent.addComponent(new Table(null,
                    TableExample.generateContent()));
            }});
        // END-EXAMPLE: layout.popupview.visibilitylistener
    }
    
    public static String subwindowDescription =
            "<p>This is essentially just a test that the PopupView overlay works without problems with modal sub-windows.</p>";
    void subwindow(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.subwindow
        Button open = new Button("Open the sub-window", new ClickListener() {
            private static final long serialVersionUID = 6082109927755672629L;

            @Override
            public void buttonClick(ClickEvent event) {
                final Window sub = new Window("This is a sub-window");
                sub.setModal(true);
                sub.center();
                UI.getCurrent().addWindow(sub);
                
                VerticalLayout popupContent = new VerticalLayout();
                popupContent.addComponent(new TextField("Textfield"));
                popupContent.addComponent(new Button("Click this", new ClickListener() {
                    private static final long serialVersionUID = 187716026035152067L;

                    @Override
                    public void buttonClick(ClickEvent event) {
                        sub.close();
                    }
                }));

                final PopupView popup = new PopupView("Open the popup", popupContent);
                sub.setContent(popup);
            }
        });
        layout.addComponent(open);
        // END-EXAMPLE: layout.popupview.subwindow
    }
}
