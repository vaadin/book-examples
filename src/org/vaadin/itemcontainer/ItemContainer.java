package org.vaadin.itemcontainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class ItemContainer implements Container, Indexed {
    private static final long serialVersionUID = -7798698253022442051L;
    
    ArrayList<Item> items;
    Class<Item> itemType;
    
    class ContainerProperty {
        Object propertyId;
        Class<?> type;
        Object defaultValue;
        
        public Object getPropertyId() {
            return propertyId;
        }
        public void setPropertyId(Object propertyId) {
            this.propertyId = propertyId;
        }
        public Class<?> getType() {
            return type;
        }
        public void setType(Class<?> type) {
            this.type = type;
        }
        public Object getDefaultValue() {
            return defaultValue;
        }
        public void setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
        }
    }
    
    public ItemContainer() {
        this.items = new ArrayList<Item>();
    }
    
    public void addItem(Item item) {
        items.add(item);
    }

    public Object nextItemId(Object itemId) {
        // Auto-generated method stub
        return null;
    }

    public Object prevItemId(Object itemId) {
        // Auto-generated method stub
        return null;
    }

    public Object firstItemId() {
        // Auto-generated method stub
        return null;
    }

    public Object lastItemId() {
        // Auto-generated method stub
        return null;
    }

    public boolean isFirstId(Object itemId) {
        // Auto-generated method stub
        return false;
    }

    public boolean isLastId(Object itemId) {
        // Auto-generated method stub
        return false;
    }

    public Object addItemAfter(Object previousItemId)
            throws UnsupportedOperationException {
        // Auto-generated method stub
        return null;
    }

    public Item addItemAfter(Object previousItemId, Object newItemId)
            throws UnsupportedOperationException {
        // Auto-generated method stub
        return null;
    }

    public int indexOfId(Object itemId) {
        // Auto-generated method stub
        return 0;
    }

    public Object getIdByIndex(int index) {
        // Auto-generated method stub
        return null;
    }

    @Deprecated
    public Object addItemAt(int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public Item addItemAt(int index, Object newItemId)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public Item getItem(Object itemId) {
        // Auto-generated method stub
        return null;
    }

    public Collection<?> getContainerPropertyIds() {
        // Auto-generated method stub
        return null;
    }

    public Collection<?> getItemIds() {
        // Auto-generated method stub
        return null;
    }

    public Property<?> getContainerProperty(Object itemId, Object propertyId) {
        // Auto-generated method stub
        return null;
    }

    public Class<?> getType(Object propertyId) {
        // Auto-generated method stub
        return null;
    }

    public int size() {
        // Auto-generated method stub
        return 0;
    }

    public boolean containsId(Object itemId) {
        // Auto-generated method stub
        return false;
    }

    @Deprecated
    public Item addItem(Object itemId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public Object addItem() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public boolean removeItem(Object itemId)
            throws UnsupportedOperationException {
        // Auto-generated method stub
        return false;
    }

    public boolean addContainerProperty(Object propertyId, Class<?> type,
            Object defaultValue) throws UnsupportedOperationException {
        return false;
    }

    public boolean removeContainerProperty(Object propertyId)
            throws UnsupportedOperationException {
        // Auto-generated method stub
        return false;
    }

    public boolean removeAllItems() throws UnsupportedOperationException {
        // Auto-generated method stub
        return false;
    }

    @Override
    public List<?> getItemIds(int startIndex, int numberOfItems) {
        // TODO Auto-generated method stub
        return null;
    }

}
