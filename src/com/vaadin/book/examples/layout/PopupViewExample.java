package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.PopupView.PopupVisibilityListener;
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
        VerticalLayout popupContent = new VerticalLayout();
        popupContent.addComponent(new TextField("Textfield"));
        popupContent.addComponent(new Button("Button"));

        PopupView popup = new PopupView("Open the popup", popupContent);
        layout.addComponent(popup);
        // END-EXAMPLE: layout.popupview.basic
    }
    
    // TODO Use this in book
    public static String compositionDescription =
        "<p>This shows how to pop up the view programmatically.</p>";
    void programmatic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.programmatic
        Button button = new Button("Show table");
        layout.addComponent(button);
        
        // Without small representation it's an invisible component
        final PopupView popup = new PopupView(null,
            new Table(null, TableExample.generateContent()));
        layout.addComponent(popup);
        
        button.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 7017927419155889334L;

            @Override
            public void buttonClick(ClickEvent event) {
                popup.setPopupVisible(true);
            }
        });
        // END-EXAMPLE: layout.popupview.programmatic
    }

    // TODO Use this in book
    void visibilitylistener(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.visibilitylistener
        // Pop-up has empty placeholder at first
        final VerticalLayout popupContent = new VerticalLayout();

        PopupView popup = new PopupView("Open the popup", popupContent);
        layout.addComponent(popup);
        
        // Fill the pop-up content when it's popped up
        popup.addPopupVisibilityListener(new PopupVisibilityListener() {
            private static final long serialVersionUID = 3867601150859170266L;

            @Override
            public void popupVisibilityChange(PopupVisibilityEvent event) {
                if (event.isPopupVisible()) {
                    popupContent.removeAllComponents();
                    popupContent.addComponent(new Table(null, TableExample.generateContent()));
                }
            }
        });
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
