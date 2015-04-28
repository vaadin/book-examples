package com.vaadin.book.examples.advanced.spring;

import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.spring.navigation
@SpringUI
@Theme("valo")
public class MySpringUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
    
    @Autowired
    SpringViewProvider viewProvider;
    
    @Override
    protected void init(VaadinRequest request) {
        // Set up access denied view
        // viewProvider.setAccessDeniedViewClass(MyAccessDeniedView.class);

        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);

        // Navigate to start view
        navigator.navigateTo(MainView.NAME);
    }
}
// END-EXAMPLE: advanced.spring.navigation
// EXAMPLE-REF: advanced.spring.navigation com.vaadin.book.examples.advanced.spring.LoginView advanced.spring.navigation
// EXAMPLE-REF: advanced.spring.navigation com.vaadin.book.examples.advanced.spring.MainView advanced.spring.navigation
