package com.vaadin.book.examples.advanced.cdi;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

//EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.MyEvent advanced.cdi.events
//EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.InputPanel advanced.cdi.events
//EXAMPLE-REF: advanced.cdi.events com.vaadin.book.examples.advanced.cdi.DisplayPanel advanced.cdi.events
// BEGIN-EXAMPLE: advanced.cdi.events
@CDIUI("cdievents")
@Theme("valo")
public class CDIEventUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

    @Inject
    InputPanel inputPanel;

    @Inject
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
