package com.vaadin.book.examples.advanced.cdi;

import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.internal.CDIUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

// EXAMPLE-FILE: advanced.cdi.navigation /com/vaadin/book/examples/advanced/cdi/HelpView.html
// EXAMPLE-REF: advanced.cdi.navigation com.vaadin.book.examples.advanced.cdi.Greeter advanced.cdi.navigation
// BEGIN-EXAMPLE: advanced.cdi.navigation
@CDIView(value=HelpView.NAME)
@DesignRoot
public class HelpView extends VerticalLayout
                      implements View {
    private static final long serialVersionUID = -7461902820768542976L;
    
    public final static String NAME = "help";

    // Instead of injecting, will be looked up in the constructor
    User user;

    // A custom component that is also a CDI-managed bean,
    // to be injected from the design.
    // Can't use CDI injection here, as the design wants to
    // inject the design components.
    Greeter greeting;

    Button back;
    
    public HelpView() {
        Design.read("HelpView.html", this);

        // Get the managed bean from the CDI container without injection
        BeanManager beanManager = CDIUtil.lookupBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(User.class,
            new AnnotationLiteral<Any>() {});
        Bean<User> bean = (Bean<User>) beanManager.resolve(beans);
        user = (User) beanManager.getReference(bean, User.class,
            beanManager.createCreationalContext(bean));
        
        // On "Back" button click, navigate back to the main view
        back.addClickListener(e ->
            getUI().getNavigator().navigateTo(MainView.NAME));
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
    }
}
// END-EXAMPLE: advanced.cdi.navigation
