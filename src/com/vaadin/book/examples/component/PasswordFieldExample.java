package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;

public class PasswordFieldExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4454143876393393750L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("passwordfieldexample");

        if ("basic".equals(context))
            basic(layout);
        else if ("css".equals(context))
            css (layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.passwordfield.basic
        // Create a text field
        PasswordField tf = new PasswordField("Keep it secret");
        
        Button ok = new Button("OK", event -> // Java 8
            layout.addComponent(new Label("Secret: " + tf.getValue())));
        // END-EXAMPLE: component.passwordfield.basic

        layout.addComponents(tf, ok);
        layout.setSpacing(true);
    }
    
    public static String cssDescription =
        "<h1>Styling PasswordField with CSS</h1>" +
        "<p>PasswordField uses the same <tt>v-textfield</tt> style as <b>TextField</b>.</p>";

    void css(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.passwordfield.css
        PasswordField tf = new PasswordField("Fence around the secret field");
        tf.addStyleName("dashing");
        // END-EXAMPLE: component.passwordfield.css
        
        layout.addComponent(tf);
    }    
}
