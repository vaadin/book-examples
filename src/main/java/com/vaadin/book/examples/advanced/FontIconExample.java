package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FontIconExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -2576571397568672542L;

    public void basic(VerticalLayout layout) {
        layout.addStyleName("fonticonexample-basic");
        layout.setSpacing(true);

        // BEGIN-EXAMPLE: advanced.fonticon.basic
        TextField name = new TextField("Name");
        name.setIcon(FontAwesome.USER);
        layout.addComponent(name);
        
        Button ok = new Button("OK", FontAwesome.CHECK);
        layout.addComponent(ok);
        // END-EXAMPLE: advanced.fonticon.basic
    }
    
    public void html(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.fonticon.html
        Label label = new Label("I " +
            FontAwesome.HEART.getHtml() + " Vaadin",
            ContentMode.HTML);
        label.addStyleName("redicon");
        layout.addComponent(label);
        // END-EXAMPLE: advanced.fonticon.html

        layout.setSpacing(true);
    }

    public void all(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.fonticon.all
        StringBuilder builder = new StringBuilder();
        for (FontAwesome icon: FontAwesome.values())
            builder.append(icon.getHtml());
        layout.addComponent(new Label(builder.toString(),
                                      ContentMode.HTML));
        // END-EXAMPLE: advanced.fonticon.all
    }

    public void intext(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.fonticon.intext
        // Use in any non-HTML captions as well
        TextField amount = new TextField("Amount (in " +
           new String(Character.toChars(FontAwesome.BTC.getCodepoint())) +
           ")");
        amount.addStyleName("awesomecaption");
        layout.addComponent(amount);
        // END-EXAMPLE: advanced.fonticon.intext
    }

    // BEGIN-EXAMPLE: advanced.fonticon.custom
    // Font icon definition with a single symbol
    public enum MyFontIcon implements FontIcon {
        EURO(0x20AC);
        
        private int codepoint;
    
        MyFontIcon(int codepoint) {
            this.codepoint = codepoint;
        }
    
        @Override
        public String getMIMEType() {
            throw new UnsupportedOperationException(
                FontIcon.class.getSimpleName()
                + " should not be used where a MIME type is needed.");
        }
    
        @Override
        public String getFontFamily() {
            return "sans-serif";
        }
    
        @Override
        public int getCodepoint() {
            return codepoint;
        }
    
        @Override
        public String getHtml() {
            return "<span class=\"v-icon\" style=\"font-family: " +
                    getFontFamily() + ";\">&#x" +
                    Integer.toHexString(codepoint) + ";</span>";
        }
    }

    public void custom(VerticalLayout layout) {
        // Then use it
        TextField name = new TextField("Amount");
        name.setIcon(MyFontIcon.EURO);
        name.addStyleName("blueicon");
        layout.addComponent(name);
        // END-EXAMPLE: advanced.fonticon.custom
    }

}
