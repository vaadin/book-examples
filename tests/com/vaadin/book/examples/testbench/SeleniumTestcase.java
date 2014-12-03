package com.vaadin.book.examples.testbench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.By;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.VerticalLayoutElement;

public class SeleniumTestcase extends TestBenchTestCase {
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Rule
	public ScreenshotOnFailureRule screenshotOnFailureRule = new ScreenshotOnFailureRule(this, true);

	@Before
	public void setUp() throws Exception {
		setDriver(TestBench.createDriver(new FirefoxDriver()));
		baseUrl = "http://localhost:8080/";
	}

    @Test
    public void basic() throws Exception {
        driver.get(concatUrl(baseUrl, "/book-examples-vaadin7/tobetested"));
        
        // Click the button
        ButtonElement button = $(ButtonElement.class).id("content.button");
        button.click();

        // Check that the label text is correct
        LabelElement label = $(LabelElement.class).first();
        assertEquals("Thanks!", label.getText());
    }
    
    @Test
    public void advanced() throws Exception {
        driver.get(concatUrl(baseUrl, "/book-examples-vaadin7/tobetested"));
        
        // Click all the buttons in the UI
        List<ButtonElement> buttons = $(ButtonElement.class).all();
        for (ButtonElement b: buttons)
            b.click();

        // Check that the label text is correct
        LabelElement label = $(VerticalLayoutElement.class).id("content").$(LabelElement.class).first();
        assertEquals("Thanks!", label.getText());
    }
    
    @Test
    public void selectById() throws Exception {
        driver.get(concatUrl(baseUrl, "/book-examples-vaadin7/tobetested"));
        
        // Click the button
        findElement(By.id("content.button")).click();

        // Check the first label under the content layout
        WebElement label = 
            findElement(By.id("content")).
            findElement(By.className("v-label"));

        assertEquals("Thanks!", label.getText());
    }
    
    @Test
    public void testCaptionLocator() throws Exception {
        driver.get(concatUrl(baseUrl, "/book-examples-vaadin7/tobetested"));
        
        // TODO FIX
        /*
        // Click the button
        findElement(By.)getElementByCaption(com.vaadin.ui.Button.class, "Push Me!").click();

        // Check the first label under the content layout
        WebElement content = getElementById("content");
        WebElement label = 
            getElementByCaption(com.vaadin.ui.Label.class,
                                "Thafnks!", content);
        assertEquals("Thanks!", label.getText());
        */
    }
    
    @Test
    public void testXPathLocator() throws Exception {
        driver.get(concatUrl(baseUrl, "/book-examples-vaadin7/tobetested"));

        // Click the button
        getDriver().findElement(By.id("content.button")).click();

        // Check the label content
        WebElement content = getDriver().findElement(By.id("content"));
        WebElement label   = content.findElement(By.xpath("//div[contains(@class, 'v-label')]"));
        assertEquals("Thanks!", label.getText());
	}

	@After
	public void tearDown() throws Exception {
	    // TODO Can't call quit anymore because it causes odd exceptions
	    // driver.quit();
	    
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
