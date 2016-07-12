package com.vaadin.book.examples.advanced;

import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class I18NExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;

    String context;
    
    public void init(String context) {
        this.context = context;

        setCompositionRoot(new Label("Bad context"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        
        if ("bundles".equals(context))
            bundles(layout);
        else if ("rtl".equals(context))
            rightToLeft(layout);
        else
            layout.addComponent(new Label("Invalid Context"));

        setCompositionRoot(layout);
    }

    void bundles(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.i18n.bundles
        // EXAMPLE-REF:   com.vaadin.book.examples.advanced.MyAppCaptions
        // EXAMPLE-REF:   com.vaadin.book.examples.advanced.MyAppCaptions_fi
        // Captions are stored in MyAppCaptions resource bundle
        // and the application object is known in this context.
        ResourceBundle bundle =
            ResourceBundle.getBundle(MyAppCaptions.class.getName(),
                                     VaadinSession.getCurrent().getLocale());
        
        // Get a localized resource from the bundle
        Button cancel =
            new Button(bundle.getString(MyAppCaptions.CancelKey));
        layout.addComponent(cancel);
        // END-EXAMPLE: advanced.i18n.bundles
    }
    
    void rightToLeft (VerticalLayout layout) {
        layout.setSpacing(true);

        // BEGIN-EXAMPLE: advanced.i18n.rtl
        // A helper class for managing both LTR and RTL layouts
        class RTLManager {
            boolean rightToLeft;
            
            public RTLManager(Locale locale) {
                // The Java locale data doesn't know the direction,
                // so we must deduce it here
                if ("ar".equals(locale.getLanguage()))
                    this.rightToLeft = true; 
                else
                    this.rightToLeft = false;
                
            }
            
            public void addComponent(HorizontalLayout layout, Component c) {
                if (rightToLeft) {
                    layout.addComponentAsFirst(c);
                    layout.setComponentAlignment(c, Alignment.TOP_RIGHT);
                    
                    // To allow further customization in a theme
                    c.addStyleName("right-to-left");
                } else
                    layout.addComponent(c);
            }
        }

        // Same in both English and Arabic
        for (String language: new String[] {"en", "ar"}) {
            // Set the application locale
            Locale locale = new Locale(language);
            
            RTLManager rtlManager = new RTLManager(locale);
            
            // Get some strings for the language
            ResourceBundle bundle = ResourceBundle.getBundle(
                    RightToLeftStrings.class.getName(),
                    locale);
            
            // Adding components to this layout needs to be managed
            // with the RTL manager
            HorizontalLayout horlayout = new HorizontalLayout();
            horlayout.setSpacing(true);
            
            InlineDateField df = new InlineDateField(bundle.getString("Date"));
            df.setLocale(locale);
            df.setResolution(Resolution.DAY);
            rtlManager.addComponent(horlayout, df);
            
            TextField tf = new TextField(bundle.getString("Name"));
            tf.setValue(bundle.getString("ShortIpsum"));
            rtlManager.addComponent(horlayout, tf);

            Label ipsum = new Label(bundle.getString("Ipsum"));
            rtlManager.addComponent(horlayout, ipsum);
            
            Button ok = new Button(bundle.getString("OkKey"));
            rtlManager.addComponent(horlayout, ok);
            
            layout.addComponent(horlayout);
        }
        // END-EXAMPLE: advanced.i18n.rtl
    }
}
