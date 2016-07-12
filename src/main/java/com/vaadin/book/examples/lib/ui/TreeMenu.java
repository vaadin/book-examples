package com.vaadin.book.examples.lib.ui;

import java.util.List;

import com.vaadin.book.examples.lib.AbstractExampleItem;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * The old plain and simple tree menu. This will be replaced later.
 * 
 * @author magi
 */
public class TreeMenu extends AbstractExampleMenu {
    private static final long serialVersionUID = -5810026236836035167L;

    public TreeMenu(Layout viewLayout, Label exampleTitle) {
        super(viewLayout, exampleTitle);

        addStyleName("examplemenu");
        setWidth("400px");
        setHeight("100%");
        
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        
        Image logo = new Image(null, new ThemeResource("img/vaadin-logo.png"));
        logo.addStyleName("vaadinlogo");
        content.addComponent(logo);

        Panel scrollpanel = new Panel("Table of Contents");
        scrollpanel.setSizeFull();
        scrollpanel.addStyleName(Reindeer.PANEL_LIGHT);
        scrollpanel.addStyleName("menupanel");
        scrollpanel.addStyleName("scrollmenu");

        scrollpanel.setContent(menu);
        
        content.addComponent(scrollpanel);
        content.setExpandRatio(scrollpanel, 1.0f);

        setCompositionRoot(content);
    }

}
