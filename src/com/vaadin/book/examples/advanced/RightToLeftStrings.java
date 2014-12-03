package com.vaadin.book.examples.advanced;

import java.util.ListResourceBundle;

/** Default captions. */
public class RightToLeftStrings extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        return contents;
    }
    static final Object[][] contents = {
        {"OkKey", "OK"},
        {"Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit"},
        {"ShortIpsum", "Lorem ipsum dolor sit"},
        {"Date", "Date"},
        {"Name", "Name"},
    };
}
