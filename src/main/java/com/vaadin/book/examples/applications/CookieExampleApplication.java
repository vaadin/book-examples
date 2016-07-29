package com.vaadin.book.examples.applications;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.gettingstarted.MyprojectUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Widgetset("com.vaadin.book.MyAppWidgetset")
//TODO Vaadin 7: Fix
// BEGIN-EXAMPLE: advanced.servletrequestlistener.cookies
@Theme("valo")
public class CookieExampleApplication extends UI {
    private static final long serialVersionUID = -39894850208484L;
    
    String              username;
    HttpServletResponse response;
    TextField           newuser = null;
    Button              login   = null;
    Button              restart;
    Button              logout;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("URI Fragment Example");
        
        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        
       if (username != null) {
            final Label hello = new Label("Logged in automatically.<br/>" +
                                          "Hello, " + username,
                                          ContentMode.HTML);
            content.addComponent(hello);
        } else {
            HorizontalLayout loginrow = new HorizontalLayout();
            content.addComponent(loginrow);
            
            newuser = new TextField ("Give a user name");
            login = new Button("Login");
            login.addClickListener(new Button.ClickListener() {
                private static final long serialVersionUID = 937474389379L;
    
                public void buttonClick(ClickEvent event) {
                    Object value = newuser.getValue(); 
                    if (value != null &&
                        ! "".equals((String) value)) {
                        username = (String) value;

                        Cookie cookie = new Cookie("username",
                                                   username);
                        // Use a fixed path
                        cookie.setPath("/book-examples");
                        cookie.setMaxAge(3600); // One hour
                        response.addCookie(cookie);
                        BookExamplesUI.getLogger().info("Set cookie.");

                        newuser.setEnabled(false);
                        login.setEnabled(false);
                        restart.setEnabled(true);
                        logout.setEnabled(true);
                    }
                }
            });
            loginrow.addComponent(newuser);
            loginrow.addComponent(login);
            loginrow.setComponentAlignment(login, Alignment.BOTTOM_LEFT);
        }
        
        HorizontalLayout logoutrow = new HorizontalLayout();
        content.addComponent(logoutrow);

        // Button to close and restart the application.
        // As the page redirects to the same page, the
        // application is reloaded in the browser, thus
        // logging in again if the cookie is set.
        restart = new Button("Restart");
        restart.setEnabled(username != null);
        logoutrow.addComponent(restart);
        restart.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 4420489757575L;

            public void buttonClick(ClickEvent event) {
                // TODO Vaadin 7: FIX VaadinSession.getCurrent().close();
            }
        });
        
        // Button to remove the cookie (and logout)
        logout = new Button("Logout");
        logout.setEnabled(username != null);
        logoutrow.addComponent(logout);
        logout.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 76482364287462L;

            public void buttonClick(ClickEvent event) {
                // Delete the cookie
                Cookie cookie = new Cookie("username", username);
                cookie.setPath("/book-examples");
                cookie.setMaxAge(0); // Delete
                response.addCookie(cookie);
                BookExamplesUI.getLogger().info("Deleted cookie.");

                // Close and restart
                // TODO Vaadin 7: FIX VaadinSession.getCurrent().close();
            }
        });
    }

    public void onRequestStart(HttpServletRequest request,
                               HttpServletResponse response) {
        if (username == null) {
            Cookie[] cookies = request.getCookies();
            for (int i=0; i<cookies.length; i++) {
                if ("username".equals(cookies[i].getName()))
                    // Log the user in automatically
                    username = cookies[i].getValue();
            }
        }
            
        // Store the reference to the response object for
        // using it in event listeners
        this.response = response;
    }

    public void onRequestEnd(HttpServletRequest request,
                             HttpServletResponse response) {
        // No need to do anything here
    }

    @WebServlet(urlPatterns = "/cookies/*", name = "CookieExampleApplication", asyncSupported = true)
    @VaadinServletConfiguration(ui = CookieExampleApplication.class, productionMode = false)
    public static class CookieExampleApplicationServlet extends VaadinServlet {
    }
}
//END-EXAMPLE: advanced.servletrequestlistener.cookies
