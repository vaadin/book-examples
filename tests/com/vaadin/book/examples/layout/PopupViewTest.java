package com.vaadin.book.examples.layout;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.ForExample;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PopupViewElement;
import com.vaadin.testbench.elements.TableElement;
import com.vaadin.testbench.elements.WindowElement;

@ForExample(com.vaadin.book.examples.layout.PopupViewExample.class)
public class PopupViewTest extends BookExampleTestcase {
    @Test
    public void basic() throws Exception {
        startTest("layout.popupview.basic");

        // Open the pop-up view by clicking the "small" representation
        PopupViewElement small = $(PopupViewElement.class).first();
        small.click();

        PopupViewElement popupview = $(PopupViewElement.class).first();
        assertTrue(popupview.$(ButtonElement.class).exists());
    }

    @Test
    public void programmatic() throws Exception {
        startTest("layout.popupview.programmatic");

        // Open the pop-up view by clicking the button component
        ButtonElement openButton = inExample(ButtonElement.class).caption("Show table").first();
        openButton.click();

        PopupViewElement popupview = $(PopupViewElement.class).first();
        TableElement table = popupview.$(TableElement.class).first();
        assertTrue(table.isDisplayed());
    }

    @Test
    public void visibilitylistener() throws Exception {
        startTest("layout.popupview.visibilitylistener");

        // Open the pop-up view by clicking the "small" representation
        PopupViewElement small = $(PopupViewElement.class).first();
        small.click();

        PopupViewElement popupview = $(PopupViewElement.class).first();
        TableElement table = popupview.$(TableElement.class).first();
        assertTrue(table.isDisplayed());
    }

    @Test
    public void subwindow() throws Exception {
        startTest("layout.popupview.subwindow");

        // Open the sub-window
        ButtonElement openButton = inExample(ButtonElement.class).caption("Open the sub-window").first();
        openButton.click();

        // Open the pop-up view by clicking the "small" representation
        WindowElement subwindow = $(WindowElement.class).caption("This is a sub-window").first();
        
        // Get the PopupView opener
        WebElement small = subwindow.findElement(By.className("v-popupview"));
        small.click();

        // Check that the PopupView contents are OK
        PopupViewElement popupview = $(PopupViewElement.class).first();
        ButtonElement button = popupview.$(ButtonElement.class).first();
        button.click();
    }
}
