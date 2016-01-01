package com.vaadin.book;

import java.io.File;
import java.util.logging.Logger;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.book.examples.BookExampleLibrary;
import com.vaadin.book.ui.AbstractExampleMenu;
import com.vaadin.book.ui.TreeMenu;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.Responsive;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

// BEGIN-EXAMPLE: themes.misc.webfonts

// Referencing another domain causes SecurityError if used with
// Responsive layout in the same UI. #16249
// Therefore, can't use web fonts from another domain together
// with responsive.
// @StyleSheet({ "http://fonts.googleapis.com/css?family=Cabin+Sketch" })

@Theme("book-examples")
@Title("Book of Vaadin Examples - Vaadin 7")
@Push
public class BookExamplesUI extends UI {
    // END-EXAMPLE: themes.misc.webfonts
    private static final long serialVersionUID = 5548861727207728718L;

    private static final transient Logger logger = Logger.getLogger(BookExamplesUI.class.getName());

    public static Logger getLogger() {
        return BookExamplesUI.logger;
    }

    public static final String APPCONTEXT = "/book-examples-vaadin7";

    AbstractExampleMenu menu;

    @Override
    protected void init(VaadinRequest request) {
        // Just a test
        // VaadinSession.getCurrent().getSession().setMaxInactiveInterval(20);
        getLogger().info(
            "MaxInactiveInterval " + VaadinSession.getCurrent().getSession().getMaxInactiveInterval());

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        setContent(mainLayout);
        
        Responsive.makeResponsive(mainLayout);

        VerticalLayout viewArea = new VerticalLayout();
        viewArea.setSizeFull();
        
        // Title bar
        HorizontalLayout titlebar = new HorizontalLayout();
        titlebar.addStyleName("titlebar");
        titlebar.setWidth("100%");
        Label title = new Label("Book of Vaadin Examples");
        title.addStyleName("title");
        title.setWidthUndefined();
        titlebar.addComponent(title);
        Label exampleTitle = new Label();
        exampleTitle.setWidthUndefined();
        titlebar.addComponent(exampleTitle);
        titlebar.setComponentAlignment(exampleTitle, Alignment.MIDDLE_RIGHT);
        viewArea.addComponent(titlebar);

        Panel examplePanel = new Panel();
        examplePanel.addStyleName("viewpanel");
        examplePanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        examplePanel.setSizeFull();
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.addStyleName("viewlayout");
        viewLayout.setSpacing(true);
        viewLayout.setMargin(true);
        examplePanel.setContent(viewLayout);
        viewArea.addComponent(examplePanel);
        viewArea.setExpandRatio(examplePanel, 1.0f);

        menu = new TreeMenu(viewLayout, exampleTitle);
        mainLayout.addComponent(menu);

        mainLayout.addComponent(viewArea);
        mainLayout.setExpandRatio(viewArea, 1.0f);

        File baseDir = VaadinService.getCurrent().getBaseDirectory();
        BookExampleLibrary library = BookExampleLibrary.getInstance(baseDir);

        menu.buildMenu(library);

        VaadinService.getCurrent().setSystemMessagesProvider(
            new SystemMessagesProvider() {
                private static final long serialVersionUID = -9118140641761605204L;

                @Override
                public SystemMessages getSystemMessages(
                    SystemMessagesInfo systemMessagesInfo) {
                    CustomizedSystemMessages messages = new CustomizedSystemMessages();
                    messages.setCommunicationErrorCaption("Comm Err");
                    messages.setCommunicationErrorMessage("This is bad.");
                    messages.setCommunicationErrorNotificationEnabled(true);
                    messages.setCommunicationErrorURL("http://vaadin.com/");
                    return messages;
                }
            });
    }
    
    // BEGIN-EXAMPLE: advanced.servletrequestlistener.introduction
    // In the sending application class we define:
    int clicks = 0;

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getClicks() {
        return clicks;
    }

    // END-EXAMPLE: advanced.servletrequestlistener.introduction

    // Override the default implementation
    public static SystemMessages getSystemMessages() {
        CustomizedSystemMessages messages = new CustomizedSystemMessages();
        messages.setCommunicationErrorCaption("Comm Err");
        messages.setCommunicationErrorMessage("This is really bad.");
        messages.setCommunicationErrorNotificationEnabled(false);
        messages.setCommunicationErrorURL("http://vaadin.com");
        return messages;
    }

}
