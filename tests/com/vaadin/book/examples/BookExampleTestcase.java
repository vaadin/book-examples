package com.vaadin.book.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elementsbase.AbstractElement;

// @RunOnHub("localhost")
public class BookExampleTestcase extends TestBenchTestCase {
    protected String baseUrl;

    @Rule
    public ScreenshotOnFailureRule screenshotOnFailureRule = new ScreenshotOnFailureRule(this, true);

    @Before
    public void setUp() throws Exception {
        setDriver(TestBench.createDriver(new FirefoxDriver()));
        baseUrl = "http://localhost:8080/book-examples-vaadin7/book";
    }
    
    public void startTest(String urifragment) {
        getDriver().get(concatUrl(baseUrl, "#" + urifragment));
    }

    public <T extends AbstractElement> ElementQuery<T> inExample(Class<T> clazz) {
        WebElement example = findElement(By.className("bookexample"));
                
        return super.$(clazz).context(example);
    }
    
    @After
    public void tearDown() throws Exception {
        // TODO Can't call quit anymore because it causes odd exceptions
        // driver.quit();
    }

    /*
    @BrowserConfiguration
    public List<DesiredCapabilities> getBrowserConfiguration() {
        List<DesiredCapabilities> browsers =
            new ArrayList<DesiredCapabilities>();
        
        // Add all the browsers you want to test
        browsers.add(BrowserUtil.firefox());
        browsers.add(BrowserUtil.chrome());
        
        return browsers;
    } */   
}
