package com.vaadin.book.examples.advanced.cdi;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

// BEGIN-EXAMPLE: advanced.cdi.producers
@CDIUI("navevents")
@Theme("valo")
public class CDINavEventsUI extends UI {
    private static final long serialVersionUID = 390555781627137542L;

   
    @Override
    protected void init(VaadinRequest request) {
    }
}
// END-EXAMPLE: advanced.cdi.producers
