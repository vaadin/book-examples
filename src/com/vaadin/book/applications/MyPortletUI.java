package com.vaadin.book.applications;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.server.VaadinPortletSession;
import com.vaadin.server.VaadinPortletSession.PortletListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyPortletUI extends UI
       implements PortletListener {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Label label;

    @Override
    protected void init(VaadinRequest request) {
		getPage().setTitle("Myportlet Application");
		
		label = new Label("Initialized()");
        // Create the content root layout for the UI
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        
		layout.addComponent(label);

		// Check that we are running as a portlet.
		if (VaadinSession.getCurrent() instanceof VaadinPortletSession) {
		    VaadinPortletSession portletsession =
		            (VaadinPortletSession) VaadinSession.getCurrent();

		    // Add a custom listener to handle action and
		    // render requests.
		    portletsession.addPortletListener(this);
			label.setValue("Running in a portal");
		} else {
		    Notification.show("Not initialized in a Portal!",
		                      Notification.Type.ERROR_MESSAGE);
			label.setValue("Not running in a portal, but elsewhere");
		}		
	}

    @Override
    public void handleRenderRequest(RenderRequest request,
                                    RenderResponse response, UI root) {
        if (label != null)
            label.setValue("RenderRequest. Portlet mode: " + request.getPortletMode());
        BookExamplesUI.getLogger().info("RenderRequest");
        BookExamplesUI.getLogger().info("Portlet mode: " + request.getPortletMode());
    }

    @Override
    public void handleActionRequest(ActionRequest request,
                                    ActionResponse response, UI root) {
        if (label != null)
            label.setValue("ActionRequest. Portlet mode: " + request.getPortletMode());
        BookExamplesUI.getLogger().info("ActionRequest");
        BookExamplesUI.getLogger().info("Portlet mode: " + request.getPortletMode());
    }

    @Override
    public void handleEventRequest(EventRequest request,
                                   EventResponse response, UI root) {
        if (label != null)
            label.setValue("EventRequest. Portlet mode: " + request.getPortletMode());
        BookExamplesUI.getLogger().info("EventRequest");
        BookExamplesUI.getLogger().info("Portlet mode: " + request.getPortletMode());
    }

    @Override
    public void handleResourceRequest(ResourceRequest request,
                                      ResourceResponse response, UI root) {
        if (label != null)
            label.setValue("ResourceRequest. Portlet mode: "+ request.getPortletMode());
        BookExamplesUI.getLogger().info("ResourceRequest");
        BookExamplesUI.getLogger().info("Portlet mode: " + request.getPortletMode());
    }
}
