package com.vaadin.book.examples.advanced.cdi;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@CDIView(value=MainView.VIEWNAME)
public class MainView extends CustomComponent
                      implements View {
    private static final long serialVersionUID = -7461902820768542976L;
    
    public final static String VIEWNAME = "main";

    @Inject
    User user;
    
    Label greeting = new Label();
    
    public MainView() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(greeting);

        // On logout, navigate back to the login view
        layout.addComponent(new Button("Logout", e ->
            getUI().getNavigator().
                navigateTo(LoginView.VIEWNAME)));
        setCompositionRoot(layout);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        greeting.setValue("Hello, " + user.getName());
    }
}
