package com.vaadin.book.examples.advanced.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

//EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.MyEvent advanced.cdi.events
//EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.InputPanel advanced.cdi.events
//EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.DisplayPanel advanced.cdi.events
// BEGIN-EXAMPLE: advanced.cdi.events
@SpringUI(path = "springevents")
@Theme("valo")
public class SpringEventUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

    @Autowired
    InputPanel inputPanel;

    @Autowired
    DisplayPanel display;

    @Override
    protected void init(VaadinRequest request) {
        Layout content =
            new HorizontalLayout(inputPanel, display);
        content.setSizeFull();
        setContent(content);
    }
}
// END-EXAMPLE: advanced.cdi.events
