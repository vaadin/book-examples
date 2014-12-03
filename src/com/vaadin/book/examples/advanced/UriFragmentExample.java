package com.vaadin.book.examples.advanced;

import com.vaadin.annotations.Theme;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UriFragmentExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3150712558665196340L;
    
    @Theme("book-examples")
    // BEGIN-EXAMPLE: advanced.urifragment.basic
    public static class MyUI extends UI {
        private static final long serialVersionUID = -7387993823853781193L;

        ListSelect menu = new ListSelect("Select a URI Fragment");
        Panel      panel = new Panel("Fragment-related Content");

        @Override
        protected void init(VaadinRequest request) {
            HorizontalLayout content = new HorizontalLayout();
            content.setSpacing(true);
            setContent(content);
            
            // Application state menu
            menu.addItem("mercury");
            menu.addItem("venus");
            menu.addItem("earth");
            menu.addItem("mars");
            menu.setRows(4);
            menu.setNullSelectionAllowed(false);
            menu.setImmediate(true);
            content.addComponent(menu);
            
            content.addComponent(panel);

            // Set the URI Fragment when menu selection changes
            menu.addValueChangeListener(new Property.ValueChangeListener() {
                private static final long serialVersionUID = 6380648224897936536L;

                public void valueChange(ValueChangeEvent event) {
                    String itemid = (String) event.getProperty().getValue();
                    getPage().setUriFragment("!" + itemid);
                }
            });

            // When the URI fragment is given, use it to set menu selection 
            getPage().addUriFragmentChangedListener(new UriFragmentChangedListener() {
                private static final long serialVersionUID = -6588416218607827834L;

                public void uriFragmentChanged(UriFragmentChangedEvent source) {
                    enter(source.getUriFragment());
                }
            });        

            // Read the initial URI fragment to create the UI
            String fragment = getPage().getUriFragment();
            enter(fragment);
        }
        
        void enter(String fragment) {
            if (fragment == null) {
                panel.setContent(new Label("No fragment given"));
                return;
            }
            
            // Strip off a possible "!" prefix
            if (fragment.startsWith("!"))
                fragment = fragment.substring(1);

            // Do some stuff with the fragment
            getPage().setTitle(fragment);
            
            menu.setValue(fragment);

            // Have some UI content related to the fragment value
            VerticalLayout content = new VerticalLayout();
            panel.setContent(content);
            
            Label label = new Label("Here's info about " + fragment);
            content.addComponent(label);
            String imagename = fragment.substring(0,1).toUpperCase()+fragment.substring(1)+".jpg";
            Image image = new Image(null, new ThemeResource(
                "img/planets/" + imagename));
            content.addComponent(image);
        }
    }

    // Code to open the UI and the crawlable page
    public void init(final String context) {
        final VerticalLayout layout = new VerticalLayout();
        
        final CheckBox enableBorders = new CheckBox("Enable Window Menus and Toolbars");
        enableBorders.setImmediate(true);
        layout.addComponent(enableBorders);
        
        final VerticalLayout buttons = new VerticalLayout();
        layout.addComponent(buttons);

        enableBorders.setValue(true);
        enableBorders.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = -4326295259605926108L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                String features = "width=640,height=480,location";
                boolean value = (Boolean)event.getProperty().getValue();
                if (value)
                    features += ",toolbar,directories,menubar";
                
                buttons.removeAllComponents();
                
                Button open = new Button("Click to Open Uri Fragment Managed Window");
                BrowserWindowOpener opener1 = new BrowserWindowOpener(
                    VaadinServlet.getCurrent().getServletContext().getRealPath(
                            "/urifragex"));
                opener1.setFeatures(features);
                opener1.setUriFragment("!mars");
                opener1.extend(open);
                buttons.addComponent(open);

                Button crawler = new Button("Click to Open Crawler Content Window");
                BrowserWindowOpener opener2 = new BrowserWindowOpener(
                    VaadinServlet.getCurrent().getServletContext().getRealPath(
                            "/urifragex"));
                opener2.setFeatures(features);
                opener2.setParameter("_escaped_fragment_", "mars");
                opener2.extend(crawler);
                buttons.addComponent(crawler);
            }
        });
        enableBorders.setValue(false);

        setCompositionRoot(layout);
    }
    // END-EXAMPLE: advanced.urifragment.basic

    // EXAMPLE-REF: advanced.urifragment.basic com.vaadin.book.MyCustomServlet advanced.urifragment.basic
}
