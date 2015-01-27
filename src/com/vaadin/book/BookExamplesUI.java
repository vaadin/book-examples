package com.vaadin.book;

import java.io.File;
import java.util.logging.Logger;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.book.examples.BookExampleLibrary;
import com.vaadin.book.ui.AbstractExampleMenu;
import com.vaadin.book.ui.TreeMenu;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@JavaScript({ "js/jquery-2.1.0.min.js" })
// BEGIN-EXAMPLE: themes.misc.webfonts
@StyleSheet({ "http://fonts.googleapis.com/css?family=Cabin+Sketch" })
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

        VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);
        mainLayout.setSizeFull();

        // Title bar
        HorizontalLayout titlebar = new HorizontalLayout();
        titlebar.addStyleName("titlebar");
        titlebar.setWidth("100%");
        VerticalLayout titlebox = new VerticalLayout();
        titlebox.setSizeUndefined();
        titlebox.addStyleName("titlebox");
        Label title = new Label("Book of Vaadin Examples");
        title.addStyleName("title");
        title.setWidth(null);
        titlebox.addComponent(title);
        Label subtitle = new Label("Vaadin 7 Edition");
        subtitle.addStyleName("subtitle");
        subtitle.setWidth(null);
        titlebox.addComponent(subtitle);
        titlebar.addComponent(titlebox);
        titlebar.setComponentAlignment(titlebox, Alignment.TOP_LEFT);

        // Right part of title bar
        VerticalLayout logobox = new VerticalLayout();
        Image logo = new Image(null, new ThemeResource("img/vaadin-logo.png"));
        logobox.addComponent(logo);
        logobox.setComponentAlignment(logo, Alignment.TOP_RIGHT);
        Label broken = new Label("Notice - some examples are broken at the moment");
        broken.setSizeUndefined();
        broken.addStyleName("subtitle");
        logobox.addComponent(broken);
        logobox.setComponentAlignment(broken, Alignment.BOTTOM_RIGHT);
        logobox.setHeight("100%"); // Should take height from the titlebox
        titlebar.addComponent(logobox);

        mainLayout.addComponent(titlebar);

        HorizontalLayout hor = new HorizontalLayout();
        hor.addStyleName("menu-and-view");
        hor.setMargin(true);
        hor.setSpacing(true);
        hor.setSizeFull();
        mainLayout.addComponent(hor);
        mainLayout.setExpandRatio(hor, 1.0f);

        final Panel viewpanel = new Panel("Selected Example");
        viewpanel.addStyleName("viewpanel");
        viewpanel.setSizeFull();
        final VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.addStyleName("viewlayout");
        viewLayout.setSpacing(true);
        viewLayout.setMargin(true);
        viewpanel.setContent(viewLayout);

        menu = new TreeMenu(viewLayout, viewpanel);
        hor.addComponent(menu);

        hor.addComponent(viewpanel);
        hor.setExpandRatio(viewpanel, 1.0f);

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
