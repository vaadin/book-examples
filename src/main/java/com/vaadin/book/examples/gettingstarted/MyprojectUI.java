package com.vaadin.book.examples.gettingstarted;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Widgetset("com.vaadin.book.MyAppWidgetset")
@SuppressWarnings("serial")
//BEGIN-EXAMPLE: gettingstarted.firstproject.newproject
@Theme("myproject")
public class MyprojectUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);
    }

    @WebServlet(urlPatterns = "/newproject/*", name = "MyprojectUI", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyprojectUI.class, productionMode = false)
    public static class MyprojectUIServlet extends VaadinServlet {
    }
}
// END-EXAMPLE: gettingstarted.firstproject.newproject

