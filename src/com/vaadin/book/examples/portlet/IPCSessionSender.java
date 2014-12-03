package com.vaadin.book.examples.portlet;

import javax.portlet.PortletSession;

import com.vaadin.addon.ipcforliferay.LiferayIPC;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedPortletSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class IPCSessionSender extends UI {
    private static final long serialVersionUID = 6878322382574777744L;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Have some input data to send over
		final Person person = new Person();
		BeanItem<Person> item = new BeanItem<Person>(person);
		BeanFieldGroup<Person> fieldgroup =
		        new BeanFieldGroup<Person>(Person.class);
        FormLayout form = new FormLayout();
		for (Object pid: item.getItemPropertyIds())
		    form.addComponent(fieldgroup.buildAndBind(pid));
		content.addComponent(form);
		
		final LiferayIPC liferayipc = new LiferayIPC();
		liferayipc.extend(this);
		
		Button send = new Button("Send", new ClickListener() {
            private static final long serialVersionUID = -6960227803436709237L;

            @Override
            public void buttonClick(ClickEvent event) {
                PortletSession session = ((WrappedPortletSession)
                        VaadinSession.getCurrent().getSession()).
                        getPortletSession();

                // Share the data on the server-side
                String key = "IPCDEMO_person";
                session.setAttribute(key, person,
                                     PortletSession.APPLICATION_SCOPE); 
                
                // Notify that it's available
                liferayipc.sendEvent("ipc_demodata_available", key);
            }
		});
        content.addComponent(send);
	}
}
