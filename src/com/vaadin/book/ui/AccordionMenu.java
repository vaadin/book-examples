package com.vaadin.book.ui;

import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.themes.Reindeer;

/**
 * Advanced menu with accordion.
 * 
 * This implementation can't be used because of Bug #11391.
 * 
 * @author magi
 */
public class AccordionMenu extends AbstractExampleMenu {
    class TOCTabContent extends CustomComponent {
        public Tree menu;
        
        public TOCTabContent() {
            super();

            final Panel scrollpanel = new Panel();
            scrollpanel.addStyleName(Reindeer.PANEL_LIGHT);
            scrollpanel.addStyleName("menuscrollcontent");
            scrollpanel.setSizeFull();

            // This fails, probably because of Bug #11391
            Page.getCurrent().getJavaScript().execute(
                "$(\".v-accordion-item-content\").mCustomScrollbar("
                + "{mouseWheelPixels: 200, advanced: {updateOnContentResize: true}});");

            menu.addContainerProperty("caption", String.class, "");
            menu.setItemCaptionMode(ItemCaptionMode.PROPERTY);
            menu.setItemCaptionPropertyId("caption");
            menu.setSizeFull();
            menu.setImmediate(true);
            scrollpanel.setContent(menu);
            
            setCompositionRoot(scrollpanel);
        }

        TextField createSearchField() {
            TextField search = new TextField();
            search.addStyleName("searchfield");

            // Filter the tree according to typed input
            search.addTextChangeListener(new TextChangeListener() {
                private static final long serialVersionUID = -3911115489311807856L;

                SimpleStringFilter filter = null;

                public void textChange(TextChangeEvent event) {
                    Filterable f = (Filterable)
                        menu.getContainerDataSource();
                    
                    // Remove old filter
                    if (filter != null)
                        f.removeContainerFilter(filter);
                    
                    // Set new filter for the "caption" property
                    filter = new SimpleStringFilter("caption", event.getText(),
                                                    true, false);
                    f.addContainerFilter(filter);
                }
            });
            
            return search;
        }
    }

    class HistoryTabContent extends CustomComponent {
        private static final long serialVersionUID = -594165791405599715L;
        
        public HistoryTabContent() {
            setCompositionRoot(new Label("Not available yet"));
        }
    }
    
    TOCTabContent toctab;
    TOCTabContent historytab; 
    
    public AccordionMenu(Layout viewLayout, Panel viewpanel) {
        super(viewLayout, viewpanel);

        addStyleName("menupanel");
        setWidth("300px");
        setHeight("100%");

        Accordion accordion = new Accordion();
        accordion.setSizeFull();
        setCompositionRoot(accordion);

        toctab = new TOCTabContent();
        accordion.addTab(toctab, "Table of Contents");

        historytab = new TOCTabContent();
        accordion.addTab(historytab, "Recent Changes");
    }
    
    public Tree getMenu() {
        return toctab.menu;
    }
}
