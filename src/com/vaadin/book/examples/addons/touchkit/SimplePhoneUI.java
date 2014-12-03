package com.vaadin.book.examples.addons.touchkit;

// BEGIN-EXAMPLE: mobile.overview.phone
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@Theme("mobiletheme")
@Widgetset("com.vaadin.book.examples.addons.touchkit.MyMobileWidgetSet")
@Title("My Mobile App")
public class SimplePhoneUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;

    @Override
    protected void init(VaadinRequest request) {
        // Define a navigation view
        class MyView extends NavigationView {
            private static final long serialVersionUID = 7240906902780527375L;

            public MyView() {
                super("Planet Details");

                CssLayout content = new CssLayout();
                setContent(content);

                VerticalComponentGroup group =
                        new VerticalComponentGroup();
                content.addComponent(group);

                group.addComponent(new TextField("Planet"));
                group.addComponent(new NumberField("Found"));
                group.addComponent(new Switch("Probed"));

                setRightComponent(new Button("OK"));
            }
        }
        
        // Use it as the content root
        setContent(new MyView());
    }
    
    @SuppressWarnings("unchecked")
    Container planetData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        
        String names[] = {"Mercury", "Venus", "Earth", "Mars",
                          "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String name: names)
            container.getContainerProperty(container.addItem(), "name").setValue(name);
        return container;
    }
}
// END-EXAMPLE: mobile.overview.phone
