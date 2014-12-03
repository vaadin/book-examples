package com.vaadin.book.applications;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinPortletSession;
import com.vaadin.server.VaadinPortletSession.PortletListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PortletModeExample extends UI
                                implements PortletListener {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Window                 mainWindow;
    ObjectProperty<String> data; // Data to view and edit
    VerticalLayout         viewContent   = new VerticalLayout();
    VerticalLayout         editContent   = new VerticalLayout();
    VerticalLayout         helpContent   = new VerticalLayout();
    
    
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Myportlet Application");

        // Data model
        data = new ObjectProperty<String>(
                "<h1>Heading</h1> <p>Some example content</p>");

        // Prepare views for the three modes (view, edit, help)
        // Prepare View mode content
        Label viewText = new Label(data, ContentMode.HTML);
        viewContent.addComponent(viewText);

        // Prepare Edit mode content
        RichTextArea editText = new RichTextArea();
        editText.setCaption("Edit the value:");
        editText.setPropertyDataSource(data);
        editContent.addComponent(editText);

        // Prepare Help mode content
        Label helpText = new Label("<h1>Help</h1>" +
                                   "<p>This helps you!</p>",
                                   ContentMode.HTML);
        helpContent.addComponent(helpText);

        // Start in the view mode
        mainWindow.setContent(viewContent);

        // Check that we are running as a portlet.
        if (VaadinSession.getCurrent() instanceof VaadinPortletSession) {
            VaadinPortletSession portletsession =
                    (VaadinPortletSession) VaadinSession.getCurrent();

            // Add a custom listener to handle action and
            // render requests.
            portletsession.addPortletListener(this);
        } else {
            Notification.show("Not running in portal",
                              Notification.Type.ERROR_MESSAGE);
        }
    }

    // Dummy implementations for the irrelevant request types
    @Override
    public void handleRenderRequest(RenderRequest request,
                                    RenderResponse response, UI root) {
    }
    @Override
    public void handleActionRequest(ActionRequest request,
                                    ActionResponse response, UI root) {
    }
    @Override
    public void handleEventRequest(EventRequest request,
                                   EventResponse response, UI root) {
    }

    @Override
    public void handleResourceRequest(ResourceRequest request,
                                      ResourceResponse response, UI root) {
        // Switch the view according to the portlet mode
        if (request.getPortletMode() == PortletMode.EDIT)
            mainWindow.setContent(editContent);
        else if (request.getPortletMode() == PortletMode.VIEW)
            mainWindow.setContent(viewContent);
        else if (request.getPortletMode() == PortletMode.HELP)
            mainWindow.setContent(helpContent);
    }
}
