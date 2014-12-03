package com.vaadin.book.examples.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.testbench.elements.CalendarElement;

// TODO This fails for some reason
// // @ForExample(com.vaadin.book.examples.component.CalendarExample.class)
public class CalendarTest extends BookExampleTestcase {
    @Test
    public void basic() throws Exception {
        startTest("component.calendar.basic");

        CalendarElement calendar = inExample(CalendarElement.class).caption("My Calendar").first();
        WebElement week = calendar.findElement(By.className("v-calendar-week-wrapper"));
        WebElement event = week.findElement(By.className("v-calendar-event"));
        WebElement eventCaption = event.findElement(By.className("v-calendar-event-caption"));
        WebElement eventTime = eventCaption.findElement(By.xpath("span"));
        assertEquals("1:00 PM", eventTime.getText());
    }

    @Test
    public void rangeselect() throws Exception {
        startTest("component.calendar.rangeselect");

        // Locate a day element in the Calendar
        CalendarElement cal = inExample(CalendarElement.class).first();
        WebElement day = cal.findElements(
                         By.className("v-calendar-day-times")).
                         get(2); // Wednesday

        // Drag a range from one time slot to another
        Actions actions = new Actions(getDriver());
        actions.clickAndHold(day.findElement(By.xpath("div[5]")));
        actions.release(day.findElement(By.xpath("div[8]")));
        actions.build().perform();

        // Check that the calendar event was created properly
        WebElement event = cal.findElement(By.className("v-calendar-event"));
        assertTrue(event.isDisplayed());
        WebElement eventCaption = event.findElement(By.className("v-calendar-event-caption"));
        WebElement eventTime = eventCaption.findElement(By.xpath("span"));
        assertEquals("11:00 AM", eventTime.getText());
    }

    @Test
    public void contextmenu() throws Exception {
        startTest("component.calendar.contextmenu");

        CalendarElement cal =
            inExample(CalendarElement.class).first();
        WebElement day = cal.findElements(
            By.className("v-calendar-day-times")).get(1); // Tuesday
        
        WebElement timeslot = day.findElement(By.xpath("div[5]"));

        // Open the context menu at the slot
        Actions actions = new Actions(getDriver());
        actions.contextClick(timeslot);
        actions.build().perform();
        
        // Select the item in the context menu
        WebElement menu = findElement(By.className("v-contextmenu"));
        WebElement menuitem = menu.findElements(
            By.className("gwt-MenuItem")).get(0); // First menu item
        WebElement itemcaption = menuitem.findElement(By.xpath("div"));
        assertEquals("Add Event", itemcaption.getText());
        menuitem.click();

        // Check that the calendar event was created properly
        WebElement event = inExample(CalendarElement.class).first().
            findElement(By.className("v-calendar-event"));
        assertTrue(event.isDisplayed());
        WebElement eventCaption = event.findElement(By.className("v-calendar-event-caption"));
        WebElement eventTime = eventCaption.findElement(By.xpath("span"));
        assertEquals("2:00 AM", eventTime.getText());
    }
}
