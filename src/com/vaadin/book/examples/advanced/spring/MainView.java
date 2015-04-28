package com.vaadin.book.examples.advanced.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: advanced.spring.navigation
@SpringView(name=MainView.NAME)
public class MainView extends CustomComponent
                      implements View, ViewAccessControl {
    private static final long serialVersionUID = -7461902820768542976L;
    
    public final static String NAME = "main";

    @Autowired
    User user;
    
    Label greeting = new Label();
    
    public MainView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.addComponent(greeting);

        // On logout, navigate back to the login view
        layout.addComponent(new Button("Logout", e ->
            getUI().getNavigator().navigateTo(LoginView.NAME)));

        setCompositionRoot(layout);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        greeting.setValue("Hello, " + user.getName());
    }

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
        if (user.getName().isEmpty())
            return true;
        else
            return true;
    }
}
// END-EXAMPLE: advanced.spring.navigation
