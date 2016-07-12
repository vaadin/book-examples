package com.vaadin.book.examples.client.widgetset.client.renderer;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.connectors.ButtonRendererConnector;
import com.vaadin.client.renderers.ButtonRenderer;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.shared.ui.Connect;

import elemental.json.JsonObject;

/**
 * A connector for {@link ButtonRenderer}.
 * 
 * @since 7.4
 * @author Vaadin Ltd
 */
@Connect(com.vaadin.book.examples.component.grid.MyButtonRenderer.class)
public class MyButtonRendererConnector extends ButtonRendererConnector {

    private static final long serialVersionUID = 6138259415131488971L;

    @Override
    public MyButtonRenderer getRenderer() {
        return (MyButtonRenderer) super.getRenderer();
    }

    @Override
    protected HandlerRegistration addClickHandler(
            RendererClickHandler<JsonObject> handler) {
        return getRenderer().addClickHandler(handler);
    }
}
