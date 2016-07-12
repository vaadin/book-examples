package com.vaadin.book.examples.client.widgetset.client.colorpicker;

import com.vaadin.shared.AbstractFieldState;

public class ColorPickerState extends AbstractFieldState {
    private static final long serialVersionUID = -2527367189687383833L;
    
    private String colorName;
    
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
