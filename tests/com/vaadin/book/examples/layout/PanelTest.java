package com.vaadin.book.examples.layout;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.Description;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.NotificationElement;
import com.vaadin.testbench.elements.PanelElement;

// @ForExample(com.vaadin.book.examples.layout.PanelExample.class)
public class PanelTest extends BookExampleTestcase {
    @Test
    public void scroll() throws Exception {
        startTest("layout.panel.scroll");

        // Scroll to some position
        PanelElement panel = inExample(PanelElement.class)
                .caption("Scrolling Panel").first();
        panel.scroll(123);
        
        // Verify that scrolled to correct position
        inExample(ButtonElement.class).caption("Scroll Position").first().click();
        NotificationElement notification = $(NotificationElement.class).first();
        assertEquals("Scroll position: 123", notification.getCaption());
        notification.close();
        
        // Click the manual scroll button
        inExample(ButtonElement.class).caption("Scroll Down").first().click();

        inExample(ButtonElement.class).caption("Scroll Position").first().click();
        notification = $(NotificationElement.class).first();
        assertEquals("Scroll position: 223", notification.getCaption());
        notification.close();
    }
}
