package com.vaadin.book.examples.layout;

import java.util.Iterator;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SubWindowExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    VerticalLayout layout = new VerticalLayout();
    String context;
    
    public void init(String context) {
        this.context = context;
        
        setCompositionRoot(layout);
    }
    
    @Override
    public void attach() {
        super.attach();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("inheritance".equals(context))
            inheritance(layout);
        else if ("close".equals(context))
            close(layout);
        else if ("modal".equals(context))
            modal(layout);
        else if ("scrolling".equals(context))
            scrolling(layout);
        else if ("noscroll".equals(context))
            noscroll(layout);
        else if ("styling".equals(context))
            styling(layout);
    }
    
    @Override
    public void detach() {
        super.detach();
        
        // Close any open child windows
        for (Iterator<Window> w = getUI().getWindows().iterator(); w.hasNext();)
            getUI().removeWindow(w.next());
    }
    
    // BEGIN-EXAMPLE: layout.sub-window.basic
    public static class SubWindowUI extends UI {
        private static final long serialVersionUID = -4813641359577526734L;

        @Override
        protected void init(VaadinRequest request) {
            // Some other UI content
            setContent(new Label("Here's my UI"));
            
            // Create a sub-window and set the content
            Window subWindow = new Window("Sub-window");
            VerticalLayout subContent = new VerticalLayout();
            subContent.setMargin(true);
            subWindow.setContent(subContent);
            
            // Put some components in it
            subContent.addComponent(new Label("Meatball sub"));
            subContent.addComponent(new Button("Awlright"));
            
            // Center it in the browser window
            subWindow.center();
            
            // Open it in the UI
            addWindow(subWindow);
        }
    }
    // END-EXAMPLE: layout.sub-window.basic

    void basic(VerticalLayout layout) {
        Button open = new Button("Open Window with Sub-Window");
        BrowserWindowOpener opener = new BrowserWindowOpener(SubWindowUI.class);
        opener.setFeatures("height=200,width=300,resizable");
        opener.extend(open);
        layout.addComponent(open);
    }
    
    void inheritance(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.inheritance
        // Define a sub-window by inheritance
        class MySub extends Window {
            private static final long serialVersionUID = 1L;

            public MySub() {
                super("Subs on Sale"); // Set window caption
                center();

                // Some basic content for the window
                VerticalLayout content = new VerticalLayout();
                content.addComponent(new Label("Just say it's OK!"));
                content.setMargin(true);
                setContent(content);
                
                // Disable the close button
                setClosable(false);

                // Trivial logic for closing the sub-window
                Button ok = new Button("OK");
                ok.addClickListener(new ClickListener() {
                    private static final long serialVersionUID = 1L;

                    public void buttonClick(ClickEvent event) {
                        // Close the sub-window
                        close();
                    }
                });
                content.addComponent(ok);
            }
        }
        
        // Some UI logic to open the sub-window
        final Button open = new Button("Open Sub-Window");
        open.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 5249151941312555613L;

            public void buttonClick(ClickEvent event) {
                MySub sub = new MySub();
                
                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });
        // END-EXAMPLE: layout.sub-window.inheritance
        
        layout.addComponent(open);
        
        Label space = new Label(" ");
        space.setHeight("300px");
        layout.addComponent(space);
    }

    static public String closeDescription =
        "<h1>Closing Sub-Windows</h1>\n"+
        "<p>The user can close a sub-window by clicking the close button in the upper right corner. "+
        "You can close a sub-window programmatically with <tt>close()</tt>. "+
        "In both cases, a <b>CloseEvent</b> is fired, which you can handle with a <b>CloseListener</b>.</p>";

    void close(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.close
        // Create a sub-window and add it to the main window
        Window sub = new Window("Close Me");
        UI.getCurrent().addWindow(sub);
        
        // Center the sub-window in the application-level window
        sub.center();

        // Handle closing of the window by user
        sub.addCloseListener(new CloseListener() {
            private static final long serialVersionUID = -4381415904461841881L;

            public void windowClose(CloseEvent e) {
                layout.addComponent(new Label("Sub-window of "+
                        UI.getCurrent().getCaption() +
                        " was closed"));
            }
        });
        // END-EXAMPLE: layout.sub-window.close
    }

    void modal(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.modal
        // Create a sub-window and add it to the main window
        Window sub = new Window("I'm Modal");
        sub.setContent(new Label("Here's some content"));
        sub.setModal(true);
        UI.getCurrent().addWindow(sub);
        // END-EXAMPLE: layout.sub-window.modal
    }

    void scrolling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.scrolling
        // Create a sub-window of a fixed width
        Window subWindow = new Window("Scrolling Sub");
        subWindow.setModal(true);
        subWindow.setWidth("300px");
        subWindow.setHeight("300px");
        
        // Put some large content in it
        GridLayout g = new GridLayout(10,10);
        g.setWidth("500px");
        g.setHeight("500px");
        for (int i=0; i<g.getRows()*g.getColumns(); i++)
            g.addComponent(new Label("" + (i+1)));
        
        // Set as root layout of the sub-window
        subWindow.setContent(g);
        
        // Attach it to the root component
        UI.getCurrent().addWindow(subWindow);
        // END-EXAMPLE: layout.sub-window.scrolling
    }
    
    void noscroll(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.noscroll
        // Create a sub-window of a fixed width
        //Window subWindow = new Window("Scrolling Sub");
        Window subWindow = new Window("Scrolling Sub");
        subWindow.setModal(true);
        subWindow.setWidth("300px");
        subWindow.setHeight("300px");
        
        // Put some large content in it - scroll bars should
        // now appear
        GridLayout g = new GridLayout(10,10);
        g.setWidth("400px");
        g.setHeight("400px");
        for (int i=0; i<g.getRows()*g.getColumns(); i++)
            g.addComponent(new Label("" + (i+1)));
        
        // Set as root layout of the sub-window
        subWindow.setContent(g);
        
        // Add it to the main window of the application
        UI.getCurrent().addWindow(subWindow);
        // END-EXAMPLE: layout.sub-window.noscroll
    }
    
    public void positioning(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.positioning
        // Create a sub-window and add it to the main window
        Window sub = new Window("I'm in the corner");
        sub.setContent(new Label("Here's some content"));

        // Position in top-right corner
        final int width = 300;
        sub.setWidth(width + "px");
        sub.setPositionX(Page.getCurrent().getBrowserWindowWidth() - width);
        sub.setPositionY(0);

        UI.getCurrent().addWindow(sub);
        // END-EXAMPLE: layout.sub-window.positioning
    }

    void styling(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.sub-window.styling
        // Create a sub-window and attach it to the root component
        Window sub = new Window("Life beneath the waves");
        UI.getCurrent().addWindow(sub);
        sub.center();

        // For themeing just this sub-window
        sub.addStyleName("yellowsub");
        // END-EXAMPLE: layout.sub-window.styling
    }
}
