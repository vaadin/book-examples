package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class LayoutFeaturesExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 7999287318358131515L;

    public void init(String context) {
        VerticalLayout border = new VerticalLayout();
        Layout layout;

        if ("layoutclick".equals(context))
            layout = layoutclick();
        else {
            setCompositionRoot(new Label("Error"));
            return;
        }

        border.addComponent(layout);
        border.setMargin(true);
        border.setSizeUndefined();
        border.addStyleName("borderframe");
        setSizeUndefined();
        setCompositionRoot(border);
    }

    Layout layoutclick() {
        // BEGIN-EXAMPLE: layout.layoutfeatures.layoutclick
        // Have a layout, any layout
        VerticalLayout clickLayout = new VerticalLayout();
        clickLayout.setWidth("200px");
        clickLayout.setHeight("200px");
        
        clickLayout.addLayoutClickListener(new LayoutClickListener() {
            private static final long serialVersionUID = -4930574497612644393L;

            public void layoutClick(LayoutClickEvent event) {
                new Notification("Click! ",
                        "<p>Position: " + event.getRelativeX() +
                        "," + event.getRelativeY() + "</p>"+
                        "<p>Button: " + event.getButtonName() + "</p>")
                    .show(Page.getCurrent());
            }
        });

        // Put an informative text inside the layout
        Label label = new Label("Click anywhere around here!");
        label.setSizeUndefined();
        clickLayout.addComponent(label);
        clickLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        // END-EXAMPLE: layout.layoutfeatures.layoutclick
        
        return clickLayout;
    }
}
