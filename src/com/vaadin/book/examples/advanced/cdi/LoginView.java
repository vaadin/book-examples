package com.vaadin.book.examples.advanced.cdi;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@CDIView(LoginView.VIEWNAME)
public class LoginView extends CustomComponent
                       implements View {
    private static final long serialVersionUID = -7461902820768542976L;

    public final static String VIEWNAME = "";
    
    // Here we inject to the constructor and actually do
    // not store the injected object to use it later
    @Inject
    public LoginView(User user) {
        VerticalLayout layout = new VerticalLayout();
        
        // An input field for editing injected data
        BeanItem<User> item = new BeanItem<User>(user);
        TextField username = new TextField("User name",
                item.getItemProperty("name"));
        username.setNullRepresentation("");
        layout.addComponent(username);

        // Login button (authentication omitted) / Java 8
        layout.addComponent(new Button("Login", e ->
            getUI().getNavigator().
                navigateTo(MainView.VIEWNAME)));
        
        setCompositionRoot(layout);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {}
}
