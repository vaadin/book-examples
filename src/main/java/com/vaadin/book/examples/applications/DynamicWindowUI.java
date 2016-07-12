package com.vaadin.book.examples.applications;

// BEGIN-EXAMPLE: advanced.applicationwindow.dynamic
// TODO Vaadin 7: Fix
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DynamicWindowUI extends UI {
    private static final long serialVersionUID = 1340937891969118454L;

    @Override
    protected void init(VaadinRequest request) {
        // Create the content root layout for the UI
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        // Create just the main window initially
        layout.addComponent(new Label("This is the main window."));
    }
    
    // Puts some content in the window
    /*
    void initWindow(final UI window) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        window.setContent(layout);
        
        // A button to open a new window
        Link openNew = new Link("Open New Window",
                new ExternalResource(BookExamplesUI.APPCONTEXT + "/dynamicwindow/"),
                "_blank", 480, 100, Page.BORDER_DEFAULT);
        window.addComponent(openNew);

        // Demonstrate closing pop-up windows
        Button close = new Button("Close This Window");
        close.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -86208708122352874L;

            public void buttonClick(ClickEvent event) {
                JavaScript.getCurrent().execute("close();");
            }
        });
        window.addComponent(close);
    }

    int windowCount = 0;
    */
}
// END-EXAMPLE: advanced.applicationwindow.dynamic
