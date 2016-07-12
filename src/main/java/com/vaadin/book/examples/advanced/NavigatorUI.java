package com.vaadin.book.examples.advanced;

import java.util.function.Consumer;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;

@Theme("book-examples")
// EXAMPLE-FILE: advanced.navigator.basic /com/vaadin/book/examples/advanced/MainView.html
// EXAMPLE-FILE: advanced.navigator.basic /com/vaadin/book/examples/advanced/AnimalView.html
// BEGIN-EXAMPLE: advanced.navigator.basic
public class NavigatorUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;
    
    /** A start view for navigating to the main view.
     * 
     *  Not managed by the Navigator
     **/
    @DesignRoot
    static public class LoginView extends VerticalLayout {
        private static final long serialVersionUID = -3398565663865641952L;

        public static final String NAME = "";
        
        TextField username;
        Button    loginButton;
        
        public LoginView(Consumer<String> loginCallback) {
            setSizeFull();
            Design.read(this);
            
            username.setValue("demouser");

            loginButton.addClickListener(click ->
                loginCallback.accept(username.getValue()));

            Notification.show("Welcome to the Animal Farm");
        }        
    }

    @DesignRoot
    static public class AnimalView extends VerticalLayout implements View {
        private static final long serialVersionUID = 6852298665681141274L;

        Label watching;
        Embedded pic;
        Label back;
        
        public AnimalView() {
            Design.read(this);
        }

        @Override
        public void enter(ViewChangeEvent event) {
            String animal = event.getViewName();

            watching.setValue("You are currently watching a " +
                animal);
            pic.setSource(new ThemeResource(
                "img/" + animal + "-128px.png"));
            back.setValue("and " + animal +
                " is watching you back");
        }
    }

    
    /** Main view with a menu (with declarative layout design) */
    @DesignRoot
    public class MainView extends HorizontalLayout {
        private static final long serialVersionUID = -3398565663865641952L;

        public static final String NAME = "main";

        Navigator  navigator;
        
        class AnimalButton extends Button {
            private static final long serialVersionUID = -8596942814960467072L;

            public AnimalButton(String caption, String id) {
                super(caption);
                addStyleName(ValoTheme.MENU_ITEM);
                setIcon(new ThemeResource("img/" + id + "-16px.png"));
                addClickListener(click -> navigator.navigateTo(id));
            }
        }
        
        Label title;
        CssLayout menu;
        Panel contentArea;
        Button logoutButton;

        public MainView(String username, Runnable logout) {
            setSizeFull();
            addStyleName(ValoTheme.MENU_ROOT);

            title = new Label("Animal Farm");
            title.addStyleName(ValoTheme.MENU_TITLE);

            menu = new CssLayout();
            menu.addStyleName(ValoTheme.MENU_PART);
            addComponent(menu);

            menu.addComponent(new AnimalButton("Pig",      "pig"));
            menu.addComponent(new AnimalButton("Cat",      "cat"));
            menu.addComponent(new AnimalButton("Dog",      "dog"));
            menu.addComponent(new AnimalButton("Reindeer", "reindeer"));
            menu.addComponent(new AnimalButton("Penguin",  "penguin"));
            menu.addComponent(new AnimalButton("Sheep",    "sheep"));
            
            contentArea = new Panel("Equals");
            contentArea.addStyleName(ValoTheme.PANEL_BORDERLESS);
            contentArea.setSizeFull();
            addComponent(contentArea);
            setExpandRatio(contentArea, 1.0f);

            // Create a navigator to control the sub-views
            navigator = new Navigator(UI.getCurrent(), contentArea);
            
            // Create and register the sub-views
            navigator.addView("pig", AnimalView.class);
            navigator.addView("cat", AnimalView.class);
            navigator.addView("dog", AnimalView.class);
            navigator.addView("reindeer", AnimalView.class);
            navigator.addView("penguin",  AnimalView.class);
            navigator.addView("sheep",    AnimalView.class);
            
            // Allow going back to the start
            logoutButton = new Button("Log Out");
            logoutButton.addClickListener(click -> // Java 8
                logout.run());
            addComponent(logoutButton);
        }        

        /*
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
        }*/
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");
        
        // Enable Valo menu
        addStyleName(ValoTheme.UI_WITH_MENU);
        setResponsive(true);

        // Handle login and logout
        setContent(new LoginView(username ->
            setContent(new MainView(username, () -> {
                VaadinSession.getCurrent().close();
                Page.getCurrent().setLocation(VaadinServlet.getCurrent()
                    .getServletContext().getContextPath() +
                    "/navigator");
            }))));
    }
}
// END-EXAMPLE: advanced.navigator.basic
