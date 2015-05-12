package com.vaadin.book.examples.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vaadin.book.examples.BookExampleTestcase;
import com.vaadin.book.examples.Description;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.PasswordFieldElement;

// @ForExample(com.vaadin.book.examples.component.PasswordFieldExample.class)
public class PasswordFieldTest extends BookExampleTestcase {
    @Test
    public void basic() throws Exception {
        startTest("component.passwordfield.basic");

        PasswordFieldElement pwf = inExample(PasswordFieldElement.class).caption("Keep it secret").first();
        pwf.sendKeys("mypassword123");
        assertEquals("mypassword123", pwf.getAttribute("value"));

        ButtonElement okbutton = inExample(ButtonElement.class).caption("OK").first();
        okbutton.click();

        // Verify the UI changes after button action finishes
        LabelElement output = inExample(LabelElement.class).first();
        assertEquals("Secret: mypassword123", output.getText());
   }
}
