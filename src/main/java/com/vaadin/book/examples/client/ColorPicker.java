/* 
@ITMillApache2LicenseForJavaFiles@
 */

package com.vaadin.book.examples.client;

import com.vaadin.ui.AbstractField;

/**
 * Color picker for selecting a color from a palette.
 * 
 * @author magi
 */
public class ColorPicker extends AbstractField<String> {
    private static final long serialVersionUID = -49625621494L;

    public ColorPicker() {
        super();
        setValue(new String("white"));
    }

    /** The property value of the field is a String. */
    @Override
    public Class<String> getType() {
        return String.class;
    }

    /** Set the currently selected color. */
    public void setColor(String newcolor) {
        // Sets the color name as the property of the component.
        // Setting the property will automatically cause
        // repainting of the component with paintContent().
        setValue(newcolor);
    }

    /** Retrieve the currently selected color. */
    public String getColor() {
        return (String) getValue();
    }
}
