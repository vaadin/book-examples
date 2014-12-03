package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ShortcutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 5045754729412607385L;

    public void init(String context) {
        if ("defaultbutton".equals(context))
            defaultButton();
        else if ("focus".equals(context))
            focusShortcut();
        else if ("modifier".equals(context))
            modifierKeys();
        else
            setCompositionRoot(new Label("Invalid Context"));
    }
    
    void defaultButton() {
        VerticalLayout layout = new VerticalLayout();
        
        TextField tf = new TextField("Name of the Field Agent");
        layout.addComponent(tf);
        
        HorizontalLayout buttons = new HorizontalLayout();
        layout.addComponent(buttons);
        
        // BEGIN-EXAMPLE: advanced.shortcut.defaultbutton
        // Have an OK button and set it as the default button
        Button ok = new Button("OK");
        ok.setClickShortcut(KeyCode.ENTER);
        ok.addStyleName(Reindeer.BUTTON_DEFAULT);

        // Handle the clicks for the default button 
        ok.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 6792499498807650826L;

            public void buttonClick(ClickEvent event) {
                Notification.show("OK!");
            }
        });
        buttons.addComponent(ok);

        // An ordinary button
        Button cancel = new Button("Cancel");
        buttons.addComponent(cancel);
        // END-EXAMPLE: advanced.shortcut.defaultbutton
        
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);
        
        layout.setSizeUndefined();
        setSizeUndefined();
        
        setCompositionRoot(layout);
    }

    void focusShortcut() {
        VerticalLayout layout = new VerticalLayout();
        
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
        
        setCompositionRoot(layout);
    }

    void modifierKeys() {
        VerticalLayout layout = new VerticalLayout();
        
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
}
