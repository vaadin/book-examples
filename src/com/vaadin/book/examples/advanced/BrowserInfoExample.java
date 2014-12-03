package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class BrowserInfoExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic();
        else
            setCompositionRoot(new Label("Invalid Context"));
    }
    
    void basic () {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: advanced.browserinfo.basic
        // Get the browser info object
        WebBrowser browser = Page.getCurrent().getWebBrowser();
        
        FormLayout items = new FormLayout();
        
        // Detect browser type
        String browserName = "unknown";
        if (browser.isOpera())
            browserName = "Opera";
        else if (browser.isSafari())
            browserName = "Apple Safari";
        else if (browser.isChrome())
            browserName = "Google Chrome";
        else if (browser.isIE())
            browserName = "Internet Explorer";
        else if (browser.isFirefox())
            browserName = "Mozilla Firefox";
        Label browserField = new Label(browserName);
        browserField.setCaption("Your browser:");
        items.addComponent(browserField);

        Label major = new Label(String.valueOf(browser.getBrowserMajorVersion()));
        major.setCaption("Major Version:");
        items.addComponent(major);
        
        Label minor = new Label(String.valueOf(browser.getBrowserMinorVersion()));
        minor.setCaption("Minor Version:");
        items.addComponent(minor);
        
        // Detect operating system
        String osName = "unknown";
        if (browser.isLinux())
            osName = "Linux";
        else if (browser.isWindows())
            osName = "Windows";
        else if (browser.isMacOSX())
            osName = "Mac OS X";
        Label os = new Label(osName);
        os.setCaption("Operating System:");
        items.addComponent(os);
        
        // Screen size
        Label screenSize = new Label(browser.getScreenWidth() + "�" +
                                     browser.getScreenHeight());
        screenSize.setCaption("Screen Size:");
        items.addComponent(screenSize);
        
        // IP address
        Label ip = new Label(browser.getAddress());
        ip.setCaption("IP Address:");
        items.addComponent(ip);
        
        // Get raw user-agent info string
        Label userAgent = new Label(browser.getBrowserApplication());
        userAgent.setCaption("User-Agent:");
        items.addComponent(userAgent);
        // END-EXAMPLE: advanced.browserinfo.basic
        
        layout.addComponent(items);
        layout.setSpacing(false);
        
        setCompositionRoot(layout);
    }

    void deploymentconfiguration (VerticalLayout layout) {
        // VaadinSession.getCurrent().getConfiguration()
    }
}
