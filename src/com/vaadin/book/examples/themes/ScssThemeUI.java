package com.vaadin.book.examples.themes;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//BEGIN-EXAMPLE: themes.scss.basic
@Theme("mytheme")
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
}
// END-EXAMPLE: themes.scss.basic
