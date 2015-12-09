package com.vaadin.book.examples.client.widgetset.client.renderer;

import com.vaadin.client.connectors.AbstractRendererConnector;
import com.vaadin.shared.ui.Connect;

/**
 * A connector for {@link TextFieldRenderer}.
 * 
 * @since 7.4
 * @author Vaadin Ltd
 */
@Connect(com.vaadin.book.examples.component.grid.TextFieldRenderer.class)
public class TextFieldRendererConnector extends AbstractRendererConnector<String> {

    private static final long serialVersionUID = 6138259415131488971L;

    @Override
    public MyButtonRenderer getRenderer() {
        return (MyButtonRenderer) super.getRenderer();
    }
}
