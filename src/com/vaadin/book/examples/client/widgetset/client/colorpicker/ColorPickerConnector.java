/* 
@ITMillApache2LicenseForJavaFiles@
 */

package com.vaadin.book.examples.client.widgetset.client.colorpicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.book.examples.client.ColorPicker;
import com.vaadin.client.ui.AbstractFieldConnector;
import com.vaadin.shared.ui.Connect;

@Connect(ColorPicker.class)
public class ColorPickerConnector extends AbstractFieldConnector {
    private static final long serialVersionUID = 7157710461018167631L;

    public ColorPickerConnector() {
    }

    @Override
    protected Widget createWidget() {
        return GWT.create(GwtColorPicker.class);
    }
}
