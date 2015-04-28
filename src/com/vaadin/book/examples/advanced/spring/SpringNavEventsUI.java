package com.vaadin.book.examples.advanced.spring;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.producers
@SpringUI(path = "springnavevents")
@Theme("valo")
public class SpringNavEventsUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

   
    @Override
    protected void init(VaadinRequest request) {
    }
}
// END-EXAMPLE: advanced.cdi.producers
