package com.vaadin.book.examples.advanced;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@Theme("book-examples")
// BEGIN-EXAMPLE: advanced.navigator.customviewprovider
public class CustomViewProviderUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;
    
    @WebServlet(value = "/customviewprovider/*",
                asyncSupported = true)
    @VaadinServletConfiguration(
                productionMode = false,
                ui = CustomViewProviderUI.class)
    public static class Servlet extends VaadinServlet {}

    /** A start view for navigating to the main view */
    public class LoginView extends VerticalLayout implements View {
        private static final long serialVersionUID = -3398565663865641952L;
        
        public final static String NAME = "";

        public LoginView(String greeting) {
            setSizeFull();

            // Use the initialization parameter
            addComponent(new Label("Hello - " + greeting));

            Button button = new Button("Go to Main View",
                    new Button.ClickListener() {
                private static final long serialVersionUID = -1809072471885383781L;

                @Override
                public void buttonClick(ClickEvent event) {
                    getNavigator().navigateTo(MainView.NAME);
                }
            });
            addComponent(button);
            setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        }        
        
        @Override
        public void enter(ViewChangeEvent event) {
            Notification.show("Welcome to the Animal Farm");
        }
    }

    /** Main view with a menu (with declarative layout design) */
    @DesignRoot
    public class MainView extends VerticalLayout implements View {
        private static final long serialVersionUID = -3398565663865641952L;

        public final static String NAME = "main";

        // Menu navigation button listener
        class ButtonListener implements Button.ClickListener {
            private static final long serialVersionUID = -4941184695301907995L;

            String menuitem;
            public ButtonListener(String menuitem) {
                this.menuitem = menuitem;
            }

            @Override
            public void buttonClick(ClickEvent event) {
                // Navigate to a specific state
                getNavigator().navigateTo(MainView.NAME + "/" + menuitem);
            }
        }
        
        Label title;
        VerticalLayout menuContent;
        Panel equalPanel;
        Button logout;

        public MainView(String greeting) {
            Design.read(this);
            
            // Use the initialization parameter
            title.setValue("Animal Farm - " + greeting);

            menuContent.addComponent(new Button("Pig",
                      new ButtonListener("pig")));
            menuContent.addComponent(new Button("Cat",
                      new ButtonListener("cat")));
            menuContent.addComponent(new Button("Dog",      
                      new ButtonListener("dog")));
            menuContent.addComponent(new Button("Reindeer",
                      new ButtonListener("reindeer")));
            menuContent.addComponent(new Button("Penguin",
                      new ButtonListener("penguin")));
            menuContent.addComponent(new Button("Sheep",
                      new ButtonListener("sheep")));

            // Allow going back to the start
            logout.addClickListener(event -> // Java 8
                getNavigator().navigateTo(LoginView.NAME));
        }        
        
        @DesignRoot
        class AnimalViewer extends VerticalLayout {
            private static final long serialVersionUID = 572784347380517093L;

            Label watching;
            Embedded pic;
            Label back;
            
            public AnimalViewer(String animal) {
                Design.read(this);
                
                watching.setValue("You are currently watching a " +
                                  animal);
                pic.setSource(new ThemeResource(
                    "img/" + animal + "-128px.png"));
                back.setValue("and " + animal +
                    " is watching you back");
            }
        }

        @Override
        public void enter(ViewChangeEvent event) {
            if (event.getParameters() == null
                || event.getParameters().isEmpty()) {
                equalPanel.setContent(
                    new Label("Nothing to see here, " +
                              "just pass along."));
                return;
            } else
                equalPanel.setContent(new AnimalViewer(
                    event.getParameters()));
        }
    }
    
    // A custom view provider that manages the creation of
    // application-specific views
    class MyViewProvider implements ViewProvider {
        private static final long serialVersionUID = -5440526676544445698L;

        // Hold the views after creating them
        private HashMap<String,View> views =
            new HashMap<String,View>();
        
        // An initialization parameter to be
        // passed to created views
        String greeting;
        
        public MyViewProvider(String greeting) {
            this.greeting = greeting;
        }
        
        @Override
        public String getViewName(String viewAndParameters) {
            // Extract view name
            int slash = viewAndParameters.indexOf("/");
            if (slash == -1)
                return viewAndParameters;
            else
                return viewAndParameters.substring(0, slash);
        }
        
        @Override
        public View getView(String viewName) {
            if (views.containsKey(viewName))
                return views.get(viewName);
            else if (LoginView.NAME.equals(viewName))
                views.put(viewName, new LoginView(greeting));
            else if (MainView.NAME.equals(viewName))
                views.put(viewName, new MainView(greeting));
            else
                return null;
            return views.get(viewName);
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");
        
        // Create a navigator to control the views
        Navigator navigator = new Navigator(this, this);
        
        // Use the custom view provider to instantiate the views
        navigator.addProvider(new MyViewProvider("funny greeting"));
        
        navigator.navigateTo(LoginView.NAME);
    }
}
// END-EXAMPLE: advanced.navigator.customviewprovider
// EXAMPLE-FILE: advanced.navigator.customviewprovider /com/vaadin/book/examples/advanced/MainView.html
// EXAMPLE-FILE: advanced.navigator.customviewprovider /com/vaadin/book/examples/advanced/AnimalView.html
