package com.vaadin.book.examples.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.Description;
import com.vaadin.testbench.elements.ComboBoxElement;

// @ForExample(com.vaadin.book.examples.component.ButtonExample.class)
public class ComboBoxTest extends BookExampleTestcase {
    @Test
    public void basic_selectFromMenu() throws Exception {
        startTest("component.select.combobox.basic");
        
        ComboBoxElement cb = inExample(ComboBoxElement.class).caption("Select One").first();
        
        // Select all items from the menu
        String itemCaptions[] = {"GBP", "EUR", "USD"};
        for (int i=0; i < itemCaptions.length; i++) {
            // Open and find the suggestion drop-down pop-up menu
            // - note that it's under the floating popupContent element
            cb.openPopup();
            WebElement menu = findElement(By.className("v-filterselect-suggestmenu"));

            // Find the item
            WebElement item = menu.findElements(By.className("gwt-MenuItem")).get(i);
            assertEquals(item.getText(), itemCaptions[i]);

            // Select the item
            item.click();

            // Check that the value was set as the input value of the CB
            WebElement input = cb.findElement(By.className("v-filterselect-input"));
            assertEquals(itemCaptions[i], input.getAttribute("value"));
        }
    }

    @Test
    public void basic_selectByText() throws Exception {
        startTest("component.select.combobox.basic");
        
        ComboBoxElement cb = inExample(ComboBoxElement.class).caption("Select One").first();
        WebElement input = cb.findElement(By.className("v-filterselect-input"));
        
        // Select all items from the menu
        String itemCaptions[] = {"GBP", "EUR", "USD"};
        for (int i=0; i < itemCaptions.length; i++) {
            input.clear(); // Have to clear it because of bug #14404
            cb.selectByText(itemCaptions[i]);
            
            // Check that the value was selected as the input value of the CB
            assertEquals(itemCaptions[i], input.getAttribute("value"));
        }
    }
}
