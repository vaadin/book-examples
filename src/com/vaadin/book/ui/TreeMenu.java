package com.vaadin.book.ui;

import com.vaadin.server.Page;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Reindeer;

/**
 * The old plain and simple tree menu. This will be replaced later.
 * 
 * @author magi
 */
public class TreeMenu extends AbstractExampleMenu {
    
    public TreeMenu(Layout viewLayout, Panel viewpanel) {
        super(viewLayout, viewpanel);

        addStyleName("examplemenu");
        setWidth("300px");
        setHeight("100%");
        
        Panel scrollpanel = new Panel("Table of Contents");
        scrollpanel.setSizeFull();
        scrollpanel.addStyleName(Reindeer.PANEL_LIGHT);
        scrollpanel.addStyleName("menupanel");
        scrollpanel.addStyleName("scrollmenu");

        Page.getCurrent().getJavaScript().execute(
            "$(\".v-panel-content-scrollmenu\").mCustomScrollbar("
            + "{mouseWheelPixels: 200, advanced: {updateOnContentResize: true}});");
        
        scrollpanel.setContent(menu);
        
        setCompositionRoot(scrollpanel);
    }
}
