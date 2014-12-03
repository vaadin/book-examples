package com.vaadin.book.examples.application;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class NotificationExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("normal".equals(context))
            normal(layout);
        if ("shorthand".equals(context))
            shorthand(layout);
        else if ("error".equals(context))
            error(layout);
        else if ("customization".equals(context))
            customization(layout);
        setCompositionRoot(layout);
    }

    void normal(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.notification.normal
        Button button = new Button("Be Bad",
                new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                new Notification("This is a warning",
                    "<br/>This is the <i>last</i> warning",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            }
        });
        // END-EXAMPLE: application.errors.notification.normal

        layout.addComponent(button);
    }

    void shorthand(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.notification.shorthand
        Button button = new Button("Be Bad", event -> // Java 8
            Notification.show("This is the caption",
                              "This is the description",
                              Notification.Type.WARNING_MESSAGE));
        // END-EXAMPLE: application.errors.notification.shorthand

        layout.addComponent(button);
    }

    void error(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.notification.error
        Button button = new Button("Be Bad",
                new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                new Notification("Error!",
                        "<br/>This is a serious error",
                        Notification.Type.ERROR_MESSAGE, true)
                            .show(Page.getCurrent());
            }
        });
        // END-EXAMPLE: application.errors.notification.error

        layout.addComponent(button);
    }

    void customization(VerticalLayout layout) {
        Button button = new Button("Click to Show",
                new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                // BEGIN-EXAMPLE: application.errors.notification.customization
                // Notification with default settings for a warning
                Notification notif = new Notification(
                    "Warning",
                    "<br/>Area of reindeer husbandry",
                    Notification.Type.WARNING_MESSAGE,
                    true); // Contains HTML

                // Customize it
                notif.setDelayMsec(20000);
                notif.setPosition(Position.TOP_CENTER);
                notif.setStyleName("mystyle");
                notif.setIcon(new ThemeResource("img/reindeer-64px.png"));
                
                // Show it in the page
                notif.show(Page.getCurrent());
                // END-EXAMPLE: application.errors.notification.customization
            }
        });

        layout.addComponent(button);
    }
}
