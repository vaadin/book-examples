package com.vaadin.book.examples.advanced;

// BEGIN-EXAMPLE: advanced.i18n.bundles
/** Finnish captions. */
public class MyAppCaptions_fi extends MyAppCaptions {
    private static final long serialVersionUID = -5034352379726640524L;

    static final Object[][] contents_fi = {
        {OkKey, "OK"},
        {CancelKey, "Peruuta"},
    };

    /* Must override again to get the localized strings */ 
    @Override
    public Object[][] getContents() {
        return contents_fi;
    }
}
// END-EXAMPLE: advanced.i18n.bundles
