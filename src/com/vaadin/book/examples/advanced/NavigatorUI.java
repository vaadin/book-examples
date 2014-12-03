package com.vaadin.book.examples.advanced;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("book-examples")
//BEGIN-EXAMPLE: advanced.navigator.basic
public class NavigatorUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;
    
    Navigator  navigator;
    
    protected static final String MAINVIEW = "main";
    
    /** A start view for navigating to the main view */
    public class StartView extends VerticalLayout implements View {
        private static final long serialVersionUID = -3398565663865641952L;

        public StartView() {
            setSizeFull();

            Button button = new Button("Go to Main View",
                    new Button.ClickListener() {
                private static final long serialVersionUID = -1809072471885383781L;

                @Override
                public void buttonClick(ClickEvent event) {
                    navigator.navigateTo(MAINVIEW);
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

    /** Main view with a menu */
    public class MainView extends VerticalLayout implements View {
        private static final long serialVersionUID = -3398565663865641952L;
        Panel panel;

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
                navigator.navigateTo(MAINVIEW + "/" + menuitem);
            }
        }

        public MainView() {
            setSizeFull();
            
            // Layout with menu on left and view area on right
            HorizontalLayout hLayout = new HorizontalLayout();
            hLayout.setSizeFull();

            // Have a menu on the left side of the screen
            Panel menu = new Panel("List of Equals");
            menu.setHeight("100%");
            menu.setWidth(null);
            VerticalLayout menuContent = new VerticalLayout();
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
            menuContent.setWidth(null);
            menuContent.setMargin(true);
            menu.setContent(menuContent);
            hLayout.addComponent(menu);

            // A panel that contains a content area on right
            panel = new Panel("An Equal");
            panel.setSizeFull();
            hLayout.addComponent(panel);
            hLayout.setExpandRatio(panel, 1.0f);

            addComponent(hLayout);
            setExpandRatio(hLayout, 1.0f);
            
            // Allow going back to the start
            Button logout = new Button("Logout",
                       new Button.ClickListener() {
                private static final long serialVersionUID = -1809072471885383781L;
    
                @Override
                public void buttonClick(ClickEvent event) {
                    navigator.navigateTo("");
                }
            });
            addComponent(logout);
        }        
        
        @Override
        public void enter(ViewChangeEvent event) {
            VerticalLayout panelContent = new VerticalLayout();
            panelContent.setSizeFull();
            panelContent.setMargin(true);
            panel.setContent(panelContent); // Also clears

            if (event.getParameters() == null
                || event.getParameters().isEmpty()) {
                panelContent.addComponent(
                    new Label("Nothing to see here, " +
                              "just pass along."));
                return;
            }

            // Display the fragment parameters
            Label watching = new Label(
                "You are currently watching a " +
                event.getParameters());
            watching.setSizeUndefined();
            panelContent.addComponent(watching);
            panelContent.setComponentAlignment(watching,
                Alignment.MIDDLE_CENTER);
            
            // Some other content
            Embedded pic = new Embedded(null,
                new ThemeResource("img/" + event.getParameters() +
                                  "-128px.png"));
            panelContent.addComponent(pic);
            panelContent.setExpandRatio(pic, 1.0f);
            panelContent.setComponentAlignment(pic,
                    Alignment.MIDDLE_CENTER);

            Label back = new Label("And the " +
                event.getParameters() + " is watching you");
            back.setSizeUndefined();
            panelContent.addComponent(back);
            panelContent.setComponentAlignment(back,
                Alignment.MIDDLE_CENTER);
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");
        
        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        
        // Create and register the views
        navigator.addView("", new StartView());
        navigator.addView(MAINVIEW, new MainView());
    }
}
// END-EXAMPLE: advanced.navigator.basic
