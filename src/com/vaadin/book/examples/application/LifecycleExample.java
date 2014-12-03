package com.vaadin.book.examples.application;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LifecycleExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;
    String context;
    
    @SuppressWarnings("serial")
    public void expiration(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.lifecycle.expiration
        // Obtain the wrapped HttpSession/PortletSession
        final WrappedSession lowlevelSession = VaadinSession.getCurrent().getSession();

        // Display the HttpSession configuration settings
        Panel httpSessionPanel = new Panel("Http-/PortletSession Parameters");
        FormLayout form1 = new FormLayout();
        form1.addComponent(new Label() {{
            setCaption("Server session timeout (minutes)");
            setValue(String.valueOf(lowlevelSession.getMaxInactiveInterval()));
        }});
        httpSessionPanel.setContent(form1);
        
        // Obtain the deployment configuration for the session
        final DeploymentConfiguration conf =
                VaadinSession.getCurrent().getConfiguration();
        
        // Display the VaadinSession configuration settings
        Panel vaadinSessionPanel = new Panel("VaadinSession Parameters");
        FormLayout form2 = new FormLayout();
        form2.addComponent(new Label() {{
            setCaption("Heartbeat Interval (s)");
            setValue(String.valueOf(conf.getHeartbeatInterval()));
        }});
        form2.addComponent(new Label() {{
            setCaption("Are idle sessions closed?");
            setValue(String.valueOf(conf.isCloseIdleSessions()));
        }});
        form2.addComponent(new Label() {{
            setCaption("Is XSRF Protection enabled?");
            setValue(String.valueOf(conf.isXsrfProtectionEnabled()));
        }});
        form2.addComponent(new Label() {{
            setCaption("Is the servlet in production mode?");
            setValue(String.valueOf(conf.isProductionMode()));
        }});
        form2.addComponent(new Label() {{
            setCaption("Resource Caching Time");
            setValue(String.valueOf(conf.getResourceCacheTime()));
        }});
        vaadinSessionPanel.setContent(form2);
        // END-EXAMPLE: application.lifecycle.expiration
        
        layout.addComponent(httpSessionPanel);
        layout.addComponent(vaadinSessionPanel);
    }

    public static final String closingDescription =
            "<h1>Closing a Session</h1>" +
            "<p><i>Note: also this UI will be invalidated (immediately because it has push enabled) "
            + "when you close the session in the popups, "
            + "as they share the same session.</i></p>";
    
    // BEGIN-EXAMPLE: application.lifecycle.closing
    @Theme("valo")
    public static class MyUI extends UI {
        private static final long serialVersionUID = -2195358290227913163L;

        @Override
        protected void init(VaadinRequest request) {
            setContent(new Button("Logout", event -> {// Java 8
                // Redirect this page immediately to another URL
                getPage().setLocation(
                    "/book-examples-vaadin7/logout.html");
                
                // Close the session
                getSession().close();
            }));

            // Notice quickly if other UIs are closed
            setPollInterval(3000);
        }
    }

    public void closing(VerticalLayout layout) {
        Button open = new Button("Open New Window");
        BrowserWindowOpener opener = new BrowserWindowOpener(MyUI.class);
        opener.setFeatures("width=400,height=300");
        opener.extend(open);
        // END-EXAMPLE: application.lifecycle.closing
        // EXAMPLE-APPFILE: application.lifecycle.closing logout.html

        layout.addComponents(open,
            new Label("Click the button one or more times to open UIs"));
    }

    public static final String closingallDescription =
            "<h1>Closing All UIs in the Session Immediately</h1>" +
            "<p><i>Note: also this window (UI) will be closed when you close the session in the popups, "
            + "as the popups share the session with this main UI.</i></p>";
    
    // BEGIN-EXAMPLE: application.lifecycle.closingall
    @Theme("valo")
    @Push
    public static class MyPushyUI extends UI {
        private static final long serialVersionUID = -2195358290227913163L;

        @Override
        protected void init(VaadinRequest request) {
            setContent(new Button("Logout", event -> {// Java 8
                for (UI ui: VaadinSession.getCurrent().getUIs())
                    ui.access(() -> {
                        // Redirect from the page
                        ui.getPage().setLocation(
                            "/book-examples-vaadin7/logout.html");
                    });

                getSession().close();
            }));
        }
    }

    public void closingall(VerticalLayout layout) {
        Button open = new Button("Open New Window");
        BrowserWindowOpener opener = new BrowserWindowOpener(MyPushyUI.class);
        opener.setFeatures("width=400,height=300");
        opener.extend(open);
        // END-EXAMPLE: application.lifecycle.closingall
        // EXAMPLE-APPFILE: application.lifecycle.closingall logout.html

        layout.addComponents(open,
            new Label("Click the button one or more times to open UIs"));
    }

    // BEGIN-EXAMPLE: application.lifecycle.uiclosing
    @Theme("valo")
    public static class MyPopup extends UI {
        private static final long serialVersionUID = -2195358290227913163L;

        @Override
        protected void init(VaadinRequest request) {
            setContent(new Button("Close Window", event -> {// Java 8
                // Detach the UI from the session
                getUI().close();

                // Close the popup
                JavaScript.eval("close()");
            }));
        }
    }

    public void uiclosing(VerticalLayout layout) {
        Button open = new Button("Open New Window");
        BrowserWindowOpener opener = new BrowserWindowOpener(MyPopup.class);
        opener.setFeatures("width=400,height=300");
        opener.extend(open);
        // END-EXAMPLE: application.lifecycle.uiclosing
        layout.addComponent(open);
    }
}
