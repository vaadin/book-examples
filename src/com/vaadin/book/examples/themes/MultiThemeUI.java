package com.vaadin.book.examples.themes;

// BEGIN-EXAMPLE: themes.scss.switching
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("multitheme")
public class MultiThemeUI extends UI {

    @WebServlet(value = "/multitheme/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MultiThemeUI.class)
    public static class Servlet extends VaadinServlet {}

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().setStyleName("firsttheme");

        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        setContent(content);

        // Allow choosing the theme
        ComboBox themeSelector = new ComboBox("Theme");
        themeSelector.addItem("firsttheme");
        themeSelector.addItem("secondtheme");
        themeSelector.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                UI.getCurrent().setStyleName((String) event.getProperty().getValue());
            }
        });
        themeSelector.setNullSelectionAllowed(false);
        themeSelector.setImmediate(true);
        themeSelector.setValue("firsttheme");
        content.addComponent(themeSelector);
        
        Panel panel = new Panel("A Panel");
        VerticalLayout panelContent = new VerticalLayout();
        panel.setContent(panelContent);
        panelContent.setSpacing(true);

        panelContent.addComponent(new Label("This theme changes"));
        
        Button button = new Button("Stylish Button");
        button.addStyleName("mybutton");
        panelContent.addComponent(button);
        
        content.addComponent(panel);
    }

}
// END-EXAMPLE: themes.scss.switching

