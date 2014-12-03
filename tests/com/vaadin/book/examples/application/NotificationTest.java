package com.vaadin.book.examples.application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.NotificationElement;

// @ForExample(com.vaadin.book.examples.application.NotificationExample.class)
public class NotificationTest extends BookExampleTestcase {
    @Test
    public void shorthand() throws Exception {
        startTest("application.errors.notification.shorthand");

        inExample(ButtonElement.class).caption("Be Bad").first().click();

        // Verify the notification
        NotificationElement notification = $(NotificationElement.class).first();
        assertEquals("This is the caption", notification.getCaption());
        assertEquals("This is the description", notification.getDescription());
        assertEquals("warning", notification.getType());
        notification.close();
    }
}
