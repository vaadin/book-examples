package com.vaadin.book.examples.advanced.cdi;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.URLMapping;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@CDIUI("")
@URLMapping("mycdiuis")
@Theme("valo")
public class MyCDIUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

    @Inject
    CDIViewProvider viewProvider;
    
    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);

        // Navigate to start view
        navigator.navigateTo("");
    }
}
