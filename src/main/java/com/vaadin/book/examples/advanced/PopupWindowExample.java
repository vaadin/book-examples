package com.vaadin.book.examples.advanced;

import com.vaadin.annotations.Theme;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PopupWindowExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("popup".equals(context))
            popup(layout);
        else if ("dynamic".equals(context))
            dynamic(layout);
        else
            layout.addComponent(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    

    // BEGIN-EXAMPLE: advanced.windows.popup
    @Theme("book-examples")
    public static class MyPopupUI extends UI {
        private static final long serialVersionUID = -4265213983602980250L;
    
        @Override
        protected void init(VaadinRequest request) {
            getPage().setTitle("Popup Window");
            
            // Have some content for it
            VerticalLayout content = new VerticalLayout();
            Label label =
                new Label("I just popped up to say hi!");
            label.setSizeUndefined();
            content.addComponent(label);
            content.setComponentAlignment(label,
                Alignment.MIDDLE_CENTER);
            content.setSizeFull();
            setContent(content);
        }
    }

    void popup(Layout layout) {
        // Create an opener extension
        BrowserWindowOpener opener = new BrowserWindowOpener(MyPopupUI.class);
        opener.setFeatures("height=200,width=300,resizable");
        
        // Attach it to a button
        Button button = new Button("Pop It Up");
        opener.extend(button);
        // END-EXAMPLE: advanced.windows.popup
        layout.addComponent(button);
    }
    
    void open(Layout layout) {
        // Create an opener extension
        BrowserWindowOpener opener = new BrowserWindowOpener(MyPopupUI.class);
        opener.setFeatures("height=200,width=300,resizable");
        
        // Attach it to a button
        Button button = new Button("Open It Up", click -> {
            Page.getCurrent().open("/book-examples-vaadin7/popupui", "_blank", true);
        });
        opener.extend(button);
        // END-EXAMPLE: advanced.windows.popup
        layout.addComponent(button);
    }
    
    public final static String dynamicDescription =
        "<h1>Multiple Application Windows</h1>"+
        "<p>This simple code enables multiple application-level windows.</p>"+
        "<p><b>Note:</b> <i>The other window URLs <b>must not</b> have <tt>?restartApplication</tt> parameter.</i></p>";
    
    void dynamic (VerticalLayout layout) {
        // EXAMPLE-REF: advanced.windows.dynamic com.vaadin.book.applications.DynamicWindowApplication advanced.windows.dynamic
        // BEGIN-EXAMPLE: advanced.windows.dynamic
        // A button to open a new window
        Link openNew = new Link("Start the application",
                new ExternalResource(VaadinServlet.getCurrent()
                    .getServletContext().getContextPath() +
                    "/dynamicwindow/?restartApplication"),
                "_blank", 480, 100, BorderStyle.DEFAULT);
        layout.addComponent(openNew);
        // END-EXAMPLE: advanced.windows.dynamic
        
        layout.setSpacing(true);
    }
}
