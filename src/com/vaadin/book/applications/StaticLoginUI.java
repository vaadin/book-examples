package com.vaadin.book.applications;

// BEGIN-EXAMPLE: advanced.applicationwindow.dynamic
import java.util.logging.Logger;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: advanced.urihandler.staticlogin
public class StaticLoginUI extends UI {
    private static final long serialVersionUID = 8754563610384903614L;
    Logger logger = Logger.getLogger(StaticLoginUI.class.getName());
    
    String     user        = null;
    boolean    loginFailed = false;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("My App");

        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // This is needed both for failed and successful login
        Button logout = new Button("Logout");
        logout.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 5590989789266188372L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Redirect to the login page
                final String path = VaadinServlet.getCurrent()
                        .getServletContext().getRealPath("static-login.html");
                getUI().getPage().setLocation(path);
                getSession().close();
            }
        });

        String requestUser = request.getParameter("username");
        BookExamplesUI.getLogger().info(requestUser);
        if (requestUser != null) {
            user = requestUser;
            
            // Authenticate
            loginFailed = !"example".equals(requestUser);
        
            if (loginFailed) {
                loginFailed = false;
                getPage().setTitle("Login Failed");
                content.addComponent(new Label("Login Failed"));
                content.addComponent(logout);
                logout.setCaption("Go back to login page");
                return;
            }
        }

        // Successful login into the Vaadin application
        content.addComponent(new Label("Logged in as " + user));

        // Logout
        content.addComponent(logout);
    }
}
// END-EXAMPLE: advanced.urihandler.staticlogin
