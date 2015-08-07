package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.Description;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ShortcutExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 5045754729412607385L;

    public void defaultbutton(VerticalLayout layout) {
        TextField tf = new TextField("Name of the Field Agent");
        layout.addComponent(tf);
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        layout.addComponent(buttons);
        layout.setMargin(true);
        
        // BEGIN-EXAMPLE: advanced.shortcut.defaultbutton
        // Have an OK button and set it as the default button
        Button ok = new Button("OK", click ->
            Notification.show("Clicked OK!"));
        ok.setClickShortcut(KeyCode.ENTER);
        ok.addStyleName(Reindeer.BUTTON_DEFAULT);
        buttons.addComponent(ok);

        // An ordinary button
        buttons.addComponent(new Button("Cancel"));
        // END-EXAMPLE: advanced.shortcut.defaultbutton
        
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);
        
        layout.setSizeUndefined();
    }

    public void focus(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.shortcut.focus
        // A field with Alt+N bound to it
        TextField name = new TextField("Name (Alt+N)");
        name.addShortcutListener(
                new AbstractField.FocusShortcut(name, KeyCode.N,
                                                ModifierKey.ALT));
        layout.addComponent(name);

        // A field with Alt+A bound to it, using shorthand notation
        TextField address = new TextField("Address (Alt+A)");
        address.addShortcutListener(
                new AbstractField.FocusShortcut(address, "&Address"));
        layout.addComponent(address);
        // END-EXAMPLE: advanced.shortcut.focus
    }

    public void modifier(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.shortcut.modifier
        // Focus shortcut with Ctrl+Shift+N key combination
        TextField name = new TextField("Name (Ctrl+Shift+N)");
        name.addShortcutListener(
                new AbstractField.FocusShortcut(name, KeyCode.N,
                                                ModifierKey.CTRL,
                                                ModifierKey.SHIFT));
        // END-EXAMPLE: advanced.shortcut.modifier
        
        setCompositionRoot(layout);
    }

    @Description("Click Enter key in any of the fields to launch the shortcut action")
    public void scope(VerticalLayout container) {
        HorizontalLayout layout = new HorizontalLayout();
        container.addComponent(layout);

        // BEGIN-EXAMPLE: advanced.shortcut.scope
        Panel panel1 = new Panel("Scope 1");
        VerticalLayout panel1Content = new VerticalLayout();
        panel1Content.addComponent(new TextField("Field 1 in Panel 1"));
        panel1Content.addComponent(new TextField("Field 2 in Panel 1"));
        panel1.setContent(panel1Content);
        layout.addComponent(panel1);
        
        Panel panel2 = new Panel("Scope 2");
        VerticalLayout panel2Content = new VerticalLayout();
        panel2Content.addComponent(new TextField("Field 1 in Panel 2"));
        panel2Content.addComponent(new TextField("Field 2 in Panel 2"));
        panel2.setContent(panel2Content);
        layout.addComponent(panel2);

        ShortcutListener shortcutListener =
            new ShortcutListener("enter", ShortcutAction.KeyCode.ENTER, null) {
            private static final long serialVersionUID = -5973218015801197294L;

            @Override
            public void handleAction(Object sender, Object target) {
                String senderName = sender.getClass().getSimpleName();
                if (sender instanceof Component)
                    senderName += " «" + ((Component) sender).getCaption() + "»";
                String description = "Action from " + senderName;
                
                String targetName = target.getClass().getSimpleName();
                if (target instanceof TextField)
                    targetName += " «" + ((TextField) target).getCaption() + "»";
                description += " to " + targetName;

                AbstractLayout content = (AbstractLayout) ((Panel) sender).getContent();
                content.addComponent(new Label(description));
            }
        };
        
        // Add it to BOTH panels
        panel1.addShortcutListener(shortcutListener);
        panel2.addShortcutListener(shortcutListener);
        // END-EXAMPLE: advanced.shortcut.scope

        panel1.setWidthUndefined();
        panel1Content.setWidthUndefined();
        panel1Content.setMargin(true);
        panel2.setWidthUndefined();
        panel2Content.setWidthUndefined();
        panel2Content.setMargin(true);
    }
}
