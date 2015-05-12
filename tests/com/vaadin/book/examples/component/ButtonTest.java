package com.vaadin.book.examples.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.Description;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.NotificationElement;

// @ForExample(com.vaadin.book.examples.component.ButtonExample.class)
public class ButtonTest extends BookExampleTestcase {
    @Test
    public void basic() throws Exception {
        startTest("component.button.basic");

        ButtonElement button = inExample(ButtonElement.class).caption("Do not press this button").first();
        button.click();

        // Verify the notification
        NotificationElement notification = $(NotificationElement.class).first();
        assertEquals("Do not press this button again", notification.getCaption());
        notification.close();
    }
    
    @Test
    public void link() throws Exception {
        startTest("component.button.link");

        ButtonElement button = inExample(ButtonElement.class).caption("Click Me!").first();
        button.click();

        // Verify the notification
        NotificationElement notification = $(NotificationElement.class).first();
        assertEquals("Thank You!", notification.getText());
        notification.close();
    }
    
    @Test
    public void small() throws Exception {
        startTest("component.button.small");

        // Just find the button, clicking doesn't do anything
        ButtonElement button = inExample(ButtonElement.class).caption("Click Me!").first();
        button.click();
    }
}
