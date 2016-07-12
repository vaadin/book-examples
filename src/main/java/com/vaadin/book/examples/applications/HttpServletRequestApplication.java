package com.vaadin.book.examples.applications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.annotations.Theme;
import com.vaadin.book.BookExamplesUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: advanced.servletrequestlistener.introduction
// TODO Vaadin 7: Fix
@Theme("book-examples")
public class HttpServletRequestApplication extends UI {
    private static final long serialVersionUID = -278347984723847L;

    int   requestCount  = 0;
    Label starts        = null;
    Label ends          = null;
    Label contextName   = null;
    Label vaadinClicks  = null;
    Label servletClicks = null;
    
    @Override
    protected void init(VaadinRequest request) {
        BookExamplesUI.getLogger().info("  UI.init() called.");
        
        getPage().setTitle("Servlet Request Example");
        
        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);
                
        // Does nothing but causes a request
        Button button = new Button ("Make a request");
        content.addComponent(button);
        
        // Gives some feedback about the requests
        content.addComponent(starts = new Label("Not started"));
        content.addComponent(ends   = new Label("Not ended"));
        content.addComponent(contextName = new Label());
        content.addComponent(vaadinClicks = new Label("No clicks yet"));
        content.addComponent(servletClicks = new Label("No servlet comm yet"));
    }

    public void onRequestStart(HttpServletRequest request,
                               HttpServletResponse response) {

        // The init() is called after the first request,
        // so the UI objects can be uninitialized.
        requestCount++;
        if (starts != null)
            starts.setValue("Start-of-Requests: " + requestCount);
        
        // Get some Servlet info
        if (contextName != null)
            contextName.setValue(request.getSession().
                    getServletContext().getServletContextName());

        // Get data from another Vaadin application
        // FIXME Vaadin 7
        /*
        if (vaadinClicks != null)
            for (Application a: getApplication().get)
                if (a instanceof BookExamplesApplication) {
                    BookExamplesApplication examples = 
                        (BookExamplesApplication) a;
                    vaadinClicks.setValue("Other app: " + examples.getClicks());
                }
        */
        // Get data from Servlet context
        if (servletClicks != null) {
            Object attribute = request.getSession()
                    .getServletContext().getAttribute("otherclicks");
            String other = attribute != null? (String) attribute : "";
            servletClicks.setValue("Context attribute: " + other);
        }
        
        BookExamplesUI.getLogger().info("[Start of request");
        BookExamplesUI.getLogger().info(" Query string: " +
                           request.getQueryString());
        BookExamplesUI.getLogger().info(" Path: " +
                           request.getPathTranslated());
    }

    public void onRequestEnd(HttpServletRequest request,
                             HttpServletResponse response) {
        // Count end-of-requests
        ends.setValue("End-of-Requests: " + requestCount);

        // Set data in the Servlet context 
        request.getSession().getServletContext()
                .setAttribute("otherclicks",
                              "Hello from " + requestCount);       
        
        BookExamplesUI.getLogger().info(" End of request]");
    }
}
// END-EXAMPLE: advanced.servletrequestlistener.introduction
