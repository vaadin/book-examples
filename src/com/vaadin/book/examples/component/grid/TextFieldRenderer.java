package com.vaadin.book.examples.component.grid;

import com.vaadin.book.examples.client.widgetset.client.renderer.CellValueChangeRpc;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.AbstractRenderer;

public class TextFieldRenderer extends AbstractRenderer<String> {
    private static final long serialVersionUID = 1934095081875959877L;

    private CellValueChangeRpc rpc = new CellValueChangeRpc() {
        private static final long serialVersionUID = -3384499731721458101L;

        @Override
        public void changed(String newValue, String rowKey, String columnKey) {
            Object itemId = getItemId(rowKey);
            Object propertyId = getColumn(columnKey).getPropertyId();
            Grid grid = getParentGrid();
            Container container = grid.getContainerDataSource();

            // Change the property value in the container
            @SuppressWarnings("unchecked")
            Property<String> property = container.getContainerProperty(itemId, propertyId);
            property.setValue(newValue);
        }
    };
    
    public TextFieldRenderer() {
        super(String.class, "");
        
        registerRpc(rpc);
    }
}
