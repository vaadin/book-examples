package com.vaadin.book.examples.themes;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//BEGIN-EXAMPLE: themes.scss.basic
@Theme("mytheme")
@Widgetset("com.vaadin.book.MyAppWidgetset")
public class ScssThemeUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;

    @Override
    protected void init(VaadinRequest request) {
        // Set the window or tab title
        getPage().setTitle("Window with My Theme");

        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        content.addComponent(new Label("This is my theme!"));
        
        Button button = new Button("Stylish Button");
        button.addStyleName("mybutton");
        content.addComponent(button);
    }

    @WebServlet(urlPatterns = "/scsstheme/*", name = "ScssThemeUI", asyncSupported = true)
    @VaadinServletConfiguration(ui = ScssThemeUI.class, productionMode = false)
    public static class ScssThemeUIServlet extends VaadinServlet {
    }
}
// END-EXAMPLE: themes.scss.basic
