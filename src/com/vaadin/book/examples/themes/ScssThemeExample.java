package com.vaadin.book.examples.themes;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ScssThemeExample extends CustomComponent implements
        BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public static final String basicDescription = "<h1>SCSS Themes</h1>" + "<p>You can write a theme using SCSS instead of CSS simply by having a " + "<tt>styles.scss</tt> instead of <tt>styles.css</tt> file in the theme folder.</p>";

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else layout.addComponent(new Label("Invalid context " + context));
        
        setCompositionRoot(layout);
    }

    // EXAMPLE-REF: themes.scss.basic com.vaadin.book.examples.themes.ScssThemeUI themes.scss.basic
    // EXAMPLE-APPFILE: themes.scss.basic VAADIN/themes/mytheme/styles.scss
    void basic(VerticalLayout layout) {
        BrowserFrame browser = new BrowserFrame();
        browser.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/scsstheme?restartApplication"));
        browser.setWidth("300px");
        browser.setHeight("150px");
        layout.addComponent(browser);

        setCompositionRoot(layout);
    }
}
