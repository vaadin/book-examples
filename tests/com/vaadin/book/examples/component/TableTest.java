package com.vaadin.book.examples.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.Description;
import com.vaadin.testbench.elements.TableElement;

// @ForExample(com.vaadin.book.examples.component.TableExample.class)
public class TableTest extends BookExampleTestcase {
    @Test
    public void contextmenu() throws Exception {
        startTest("component.table.contextmenu");

        // Add item
        {
            // Get an element in a table row
            TableElement table = inExample(TableElement.class).first();
            WebElement cell = table.getCell(3, 0); // A cell in the row
            
            // Open the context menu at the slot
            Actions actions = new Actions(getDriver());
            actions.contextClick(cell);
            actions.build().perform();
            
            // Select the item in the context menu
            WebElement menu = findElement(By.className("v-contextmenu"));
            WebElement menuitem = menu.findElement(
                By.xpath("//*[text() = 'Add After']"));
            menuitem.click();
    
            // Check that the item was created properly
            WebElement newcell1 = table.getCell(4, 0);
            assertEquals("Someone", newcell1.getText());
        }

        // Delete the item
        {
            // Get an element in a table row
            TableElement table = inExample(TableElement.class).first();
            WebElement cell = table.getCell(3, 0); // A cell in the row
            
            // Open the context menu at the row
            new Actions(getDriver()).contextClick(cell).perform();
            
            // Select an item in the context menu
            WebElement menu = findElement(By.className("v-contextmenu"));
            WebElement menuitem = menu.findElement(
                By.xpath("//*[text() = 'Delete Item']"));
            menuitem.click();
    
            // Check that the calendar event was created properly
            WebElement newcell1 = table.getCell(3, 0);
            assertEquals("Someone", newcell1.getText());
        }
    }
}
