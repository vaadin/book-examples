package com.vaadin.book.examples.portlet;

import javax.portlet.PortletSession;

import com.vaadin.addon.ipcforliferay.LiferayIPC;
import com.vaadin.addon.ipcforliferay.event.LiferayIPCEvent;
import com.vaadin.addon.ipcforliferay.event.LiferayIPCEventListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedPortletSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class IPCSessionReceiver extends UI {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Label label;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        
        // Have some input data to send over
        final Person person = new Person();
        final BeanFieldGroup<Person> binder =
                new BeanFieldGroup<Person>(Person.class);
        content.addComponent(createPersonForm(person, binder));
        
        final LiferayIPC liferayipc = new LiferayIPC();
        liferayipc.extend(this);

        liferayipc.addLiferayIPCEventListener("IPCDEMO_person",
            new LiferayIPCEventListener() {
            private static final long serialVersionUID = 5608927339663781021L;

            @Override
            public void eventReceived(LiferayIPCEvent event) {
                PortletSession session = ((WrappedPortletSession)
                        VaadinSession.getCurrent().getSession()).
                        getPortletSession();

                String key = event.getData();
                
                Person person = (Person) session.getAttribute(key);
                BeanItem<Person> item = new BeanItem<Person> (person);
                binder.setItemDataSource(item);
            }
        });
    }

    private Component createPersonForm(Person person, BeanFieldGroup<Person> fieldgroup) {
        BeanItem<Person> item = new BeanItem<Person>(person);

        FormLayout form = new FormLayout();
        for (Object pid: item.getItemPropertyIds())
            form.addComponent(fieldgroup.buildAndBind(pid));

        return form;
    }
}
