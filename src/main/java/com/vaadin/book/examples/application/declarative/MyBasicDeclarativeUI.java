package com.vaadin.book.examples.application.declarative;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.declarative.Design;

@SuppressWarnings("serial")
@Theme("myproject")
@Widgetset("com.vaadin.book.MyAppWidgetset")
// BEGIN-EXAMPLE: application.declarative.basic
public class MyBasicDeclarativeUI extends UI {
    @WebServlet(value = "/basicdeclarativeui/*",
                asyncSupported = true)
    @VaadinServletConfiguration(
                productionMode = false,
                ui = MyBasicDeclarativeUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        setContent(Design.read(getClass().
            getResourceAsStream("MyDeclarativeUI.html")));
    }
}
// END-EXAMPLE: application.declarative.basic
