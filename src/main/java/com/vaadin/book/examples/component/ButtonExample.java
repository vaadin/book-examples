package com.vaadin.book.examples.component;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ButtonExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.basic
        // BOOK: components.button
        Button button = new Button("Do not press this button");

        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059414138237L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Do not press this button again");
            }
        });
        
        layout.addComponent(button);
        // END-EXAMPLE: component.button.basic
    }

    public void html(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.html
        Button button = new Button("This button has <b>HTML</b>");
        button.setHtmlContentAllowed(true);
        layout.addComponents(button);
        // END-EXAMPLE: component.button.html
    }

    public void link(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.link
        // Create a button
        Button button = new Button("Click Me!");
        
        // Make it look like a hyperlink
        button.addStyleName(ValoTheme.BUTTON_LINK);
        
        // Handle clicks
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059414138237L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Thank You!");
            }
        });
        layout.addComponent(button);
        // END-EXAMPLE: component.button.link
    }

    public void styles(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.button.styles
        Button tiny = new Button("Tiny");
        tiny.addStyleName(ValoTheme.BUTTON_TINY);

        Button small = new Button("Small");
        small.addStyleName(ValoTheme.BUTTON_SMALL);

        Button normal = new Button("(Normal)");

        Button large = new Button("Large");
        large.addStyleName(ValoTheme.BUTTON_LARGE);

        Button huge = new Button("Huge");
        huge.addStyleName(ValoTheme.BUTTON_HUGE);

        Button borderless = new Button("Borderless");
        borderless.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        Button borderlessColored = new Button("Borderless Colored");
        borderlessColored.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        Button link = new Button("Link");
        link.addStyleName(ValoTheme.BUTTON_LINK);

        Button quiet = new Button("Quiet");
        quiet.addStyleName(ValoTheme.BUTTON_QUIET);

        Button danger = new Button("Danger");
        danger.addStyleName(ValoTheme.BUTTON_DANGER);

        Button friendly = new Button("Friendly");
        friendly.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button primary = new Button("Primary");
        primary.addStyleName(ValoTheme.BUTTON_PRIMARY);

        Button iconAlignRight = new Button("Icon Align Right");
        iconAlignRight.setIcon(FontAwesome.SUN_O);
        iconAlignRight.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);

        Button iconAlignTop = new Button("Icon Align Top");
        iconAlignTop.setIcon(FontAwesome.MOON_O);
        iconAlignTop.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);

        Button iconOnly = new Button("Icon Only");
        iconOnly.setIcon(FontAwesome.STAR_O);
        iconOnly.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        // END-EXAMPLE: component.button.styles

        CssLayout buttons1 = new CssLayout();
        buttons1.addStyleName("spacing");
        buttons1.setWidth("640px");
        buttons1.addComponents(tiny, small, normal, large, huge);

        CssLayout buttons2 = new CssLayout();
        buttons2.addStyleName("spacing");
        buttons2.setWidth("640px");
        buttons2.addComponents(borderless, borderlessColored, link, quiet);

        CssLayout buttons3 = new CssLayout();
        buttons3.addStyleName("spacing");
        buttons3.setWidth("640px");
        buttons3.addComponents(danger, friendly, primary);

        CssLayout buttons4 = new CssLayout();
        buttons4.addStyleName("spacing");
        buttons4.setWidth("640px");
        buttons4.addComponents(iconAlignRight, iconAlignTop, iconOnly);

        layout.addComponents(buttons1, buttons2, buttons3, buttons4);
        layout.setSpacing(true);
    }

}
