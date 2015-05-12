package com.vaadin.book.examples.component.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.Description;
import com.vaadin.testbench.elements.ButtonElement;

// @ForExample(com.vaadin.book.examples.component.properties.DescriptionExample.class)
public class DescriptionTest extends BookExampleTestcase {
    @Test
    public void plain() throws Exception {
        startTest("component.features.description.plain");

        // This button has a tooltip
        ButtonElement button = $(ButtonElement.class).caption("A Button").first();
        
        // Show it
        button.showTooltip();
        
        // It's a floating element, so need to search from entire page
        WebElement ttip = findElement(By.className("v-tooltip"));
        assertEquals(ttip.getText(), "This is the tooltip");
    }
}
