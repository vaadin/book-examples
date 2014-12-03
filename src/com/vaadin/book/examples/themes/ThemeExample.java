package com.vaadin.book.examples.themes;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.VerticalLayout;

public class ThemeExample implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    // EXAMPLE-REF: themes.using.default.valo com.vaadin.book.examples.themes.ValoThemeUI themes.using.default.valo
    // EXAMPLE-APPFILE: themes.using.default.valo VAADIN/themes/myvalotheme/styles.scss
    // EXAMPLE-APPFILE: themes.using.default.valo VAADIN/themes/myvalotheme/myvalotheme.scss
    public void valo(VerticalLayout layout) {
        BrowserFrame browser = new BrowserFrame();
        browser.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/myvalothemeui?restartApplication"));
        browser.setWidth("800px");
        browser.setHeight("600px");
        layout.addComponent(browser);
    }
}
