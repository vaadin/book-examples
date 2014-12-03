package com.vaadin.book.applications;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PortletCustomModeExample extends UI
    implements PortletListener {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Window                 mainWindow;
    ObjectProperty<String> data;
    VerticalLayout         viewContent   = new VerticalLayout();
    VerticalLayout         editContent   = new VerticalLayout();
    VerticalLayout         helpContent   = new VerticalLayout();
    VerticalLayout         customContent = new VerticalLayout();
    PortletMode            customMode;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Myportlet Application");

		// Data model
		data = new ObjectProperty<String>(
		        "<h1>Heading</h1> <p>Some example content</p>");

		// View mode content
		Label viewText = new Label(data, ContentMode.HTML);
		viewContent.addComponent(viewText);
		
		// Edit mode content
		RichTextArea editText = new RichTextArea();
		editText.setCaption("Edit the value:");
		editText.setPropertyDataSource(data);
		editContent.addComponent(editText);

		// Help mode content
		Label helpText = new Label("<h1>Help</h1>" +
		                           "<p>This helps you.</p>",
		                           ContentMode.HTML);
		helpContent.addComponent(helpText);
		
		// Custom mode content
		Label customText = new Label("This is the custom mode");
		customContent.addComponent(customText);
		
		// Start in the view mode
		mainWindow.setContent(viewContent);
		
		// Define the custom mode and a button to switch to it
		customMode = new PortletMode("config");
		viewContent.addComponent(new Button("Custom Mode",
            new Button.ClickListener() {
                private static final long serialVersionUID = 7260317716523284153L;
                public void buttonClick(ClickEvent event) {
                    if (VaadinSession.getCurrent() instanceof VaadinPortletSession) {
                        VaadinPortletSession portletsession =
                                (VaadinPortletSession) VaadinSession.getCurrent();
                        try {
                            portletsession.setPortletMode(getUI(), customMode);
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (PortletModeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));

		// Check that we are running as a portlet.
        if (VaadinSession.getCurrent() instanceof VaadinPortletSession) {
            VaadinPortletSession portletsession =
                    (VaadinPortletSession) VaadinSession.getCurrent();

            // Add a custom listener to handle action and
            // render requests.
            portletsession.addPortletListener(this);
		} else {
		}		
	}

    // Dummy implementations
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
        if (request.getPortletMode() == PortletMode.EDIT)
            mainWindow.setContent(editContent);
        else if (request.getPortletMode() == PortletMode.VIEW)
            mainWindow.setContent(viewContent);
        else if (request.getPortletMode() == PortletMode.HELP)
            mainWindow.setContent(helpContent);
        else if (request.getPortletMode() == customMode)
            mainWindow.setContent(customContent);
    }
}
