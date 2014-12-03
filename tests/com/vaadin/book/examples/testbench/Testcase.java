package com.vaadin.book.examples.testbench;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.VerticalLayoutElement;

public class Testcase extends TestBenchTestCase {
	private final String appUrl =
	    "http://localhost:8080/book-examples-vaadin7/tobetested";
	
	@Rule
	public ScreenshotOnFailureRule screenshotOnFailureRule = new ScreenshotOnFailureRule(this, true);

	@Before
	public void setUp() throws Exception {
		setDriver(TestBench.createDriver(new FirefoxDriver()));
	}

    @Test
    public void basic() throws Exception {
        driver.get(appUrl);
        
        // Click the button
        ButtonElement button = $(ButtonElement.class).id("content.button");
        button.click();

        // Check that the label text is correct
        LabelElement label = $(LabelElement.class).first();
        assertEquals("Thanks!", label.getText());
    }

    @Test
    public void advanced() throws Exception {
        driver.get(concatUrl(appUrl, "/book-examples-vaadin7/tobetested"));
        
        // Click all the buttons in the UI
        List<ButtonElement> buttons = $(ButtonElement.class).all();
        for (ButtonElement b: buttons)
            b.click();

        // Check that the label text is correct
        LabelElement label = $(VerticalLayoutElement.class).id("content").$(LabelElement.class).first();
        assertEquals("Thanks!", label.getText());
    }

	@After
	public void tearDown() throws Exception {
	    // Disabled because taking screenshots on failure
	    // driver.quit();
	}
}
