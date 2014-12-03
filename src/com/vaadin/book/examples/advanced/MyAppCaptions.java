package com.vaadin.book.examples.advanced;

import java.io.Serializable;
import java.util.ListResourceBundle;

// BEGIN-EXAMPLE: advanced.i18n.bundles
/** Caption IDs and default captions. */
public class MyAppCaptions extends ListResourceBundle
        implements Serializable {
    private static final long serialVersionUID = -2318529114498879757L;

    /* Caption ID definitions */
    public static final String OkKey     = generateId(); 
    public static final String CancelKey = generateId();

    /* Captions for the default language */
    static final Object[][] contents = {
        {OkKey, "OK"},
        {CancelKey, "Cancel"},
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }

    /* Generates numeric IDs for the strings */
    public static String generateId() {
        return new Integer(ids++).toString();
    }
    
    static int ids = 0;
}
// END-EXAMPLE: advanced.i18n.bundles
