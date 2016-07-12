package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class IconExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -2576571397568672542L;

    public void basic (VerticalLayout vlayout) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        
        // BEGIN-EXAMPLE: component.features.icon.basic
        // Component with an icon from a custom theme
        TextField name = new TextField("Name");
        name.setIcon(new ThemeResource("icons/user.png"));
        layout.addComponent(name);
        
        // Component with an icon from another theme ('runo')
        Button ok = new Button("OK");
        ok.setIcon(new ThemeResource("../runo/icons/16/ok.png"));
        layout.addComponent(ok);
        // END-EXAMPLE: component.features.icon.basic

        layout.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
        vlayout.addComponent(layout);
    }
}
