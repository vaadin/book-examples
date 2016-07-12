package com.vaadin.book.examples.advanced;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RequestHandlerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("staticlogin".equals(context))
            staticlogin(layout);
        else
            layout.addComponent(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.requesthandler.basic
        // A request handler for generating some content
        VaadinSession.getCurrent().addRequestHandler(
                new RequestHandler() {
            private static final long serialVersionUID = -1699187353262991908L;

            @Override
            public boolean handleRequest(VaadinSession session,
                                         VaadinRequest request,
                                         VaadinResponse response)
                    throws IOException {
                if ("/rhexample".equals(request.getPathInfo())) {
                    // Generate a plain text document
                    response.setContentType("text/plain");
                    response.getWriter().append(
                        "Here's some dynamically generated content.\n");
                    response.getWriter().format(Locale.ENGLISH,
                        "Time: %Tc\n", new Date());
                    
                    // Use shared session data
                    response.getWriter().format("Session data: %s\n",
                        session.getAttribute("mydata"));
                    
                    return true; // We wrote a response
                } else
                    return false; // No response was written
            }
        });
        
        // Input some shared data in the session
        TextField dataInput = new TextField("Some data");
        dataInput.addValueChangeListener(event ->
            VaadinSession.getCurrent().setAttribute("mydata",
                event.getProperty().getValue()));
        dataInput.setValue("Here's some");

        // Determine the base path for the servlet
        String servletPath = VaadinServlet.getCurrent()
                .getServletContext().getContextPath()
                + "/book"; // Servlet

        // Display the page in a pop-up window
        Link open = new Link("Click to Show the Page",
            new ExternalResource(servletPath + "/rhexample"),
            "_blank", 500, 350, BorderStyle.DEFAULT);

        layout.addComponents(dataInput, open);
        // END-EXAMPLE: advanced.requesthandler.basic
    }
    
    public final static String staticloginDescription =
        "<h1>External Login Page</h1>"+
        "<p>Having the login page in a Vaadin application causes some problems. " +
        "Password memory feature in browsers does not work with regular Vaadin " +
        "components. This is a common problem in Ajax applications. " +
        "The <b>LoginForm</b> component solves this by using an iframe, " +
        "but this is also awkward, for example because the size of the " +
        "frame is fixed. Moreover, the " +
        "session has to be kept open as expiration of the session in the login " +
        "page can be rather odd and even frustrating.</p>" +
        "<p>This example shows how to</p>" +
        "<ul>" +
        "  <li>handle authentication in a request listener and</li>" +
        "  <li>give possible feedback from failed login in a <b>URIHandler</b>.</li>" +
        "</ul>" +
        "<p>This way, the application is not initialized before the login " +
        "actually succeeds.</p>" +
        "<p>Please see also the source code of the " +
        "<tt><a href='"+BookExamplesUI.APPCONTEXT + "/static-login.html'>static-login.html</a></tt> page in your browser (<b>Ctrl+U</b>).</p>";
    
    void staticlogin(VerticalLayout layout) {
        // EXAMPLE-REF: advanced.requesthandler.staticlogin com.vaadin.book.applications.StaticLoginUI advanced.requesthandler.staticlogin
        // BEGIN-EXAMPLE: advanced.requesthandler.staticlogin
        // A button to open a new window
        Link openNew = new Link("Open Demo Application New Window",
                new ExternalResource(BookExamplesUI.APPCONTEXT + "/static-login.html"),
                "_blank", 500, 350, BorderStyle.DEFAULT);
        layout.addComponent(openNew);
        // END-EXAMPLE: advanced.requesthandler.staticlogin
    }
}
