package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FormLayoutExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 5602882631420102318L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.formlayout.basic
        // BOOK: layout.components.orderedlayout
        FormLayout form = new FormLayout();
        TextField tf1 = new TextField("Name");
        tf1.setIcon(FontAwesome.USER);
        tf1.setRequired(true);
        tf1.addValidator(new NullValidator("Must be given", false));
        form.addComponent(tf1);
        
        TextField tf2 = new TextField("Street address");
        tf2.setIcon(FontAwesome.ROAD);
        form.addComponent(tf2);
        
        TextField tf3 = new TextField("Postal code");
        tf3.setIcon(FontAwesome.ENVELOPE);
        tf3.addValidator(new IntegerRangeValidator("Doh!", 1, 99999));
        form.addComponent(tf3);
        // END-EXAMPLE: layout.formlayout.basic
        layout.addComponent(form);
    }
}
