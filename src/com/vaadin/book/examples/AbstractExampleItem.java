package com.vaadin.book.examples;

import java.io.Serializable;

public class AbstractExampleItem implements Serializable {
    private static final long serialVersionUID = -6281280822803513663L;

    protected String exampleId;
    protected String context;
    private String parentId;
    private boolean collapsed;

    public AbstractExampleItem(String itemid) {
        if ("-".equals(itemid.substring(itemid.length()-1))) {
            this.collapsed = true;
            itemid = itemid.substring(0, itemid.length()-1);
        } else
            collapsed = false;
        
        this.exampleId    = itemid;

        // Determine parent node and context
        int lastdot = itemid.lastIndexOf(".");
        if (lastdot != -1) {
            parentId = itemid.substring(0, lastdot);
            context = itemid.substring(lastdot+1);
        } else
            parentId = null;
    }
    
    public String getExampleId() {
        return exampleId;
    }
    
    public String getParentId() {
        return parentId;
    }
    
    public boolean isCollapsed() {
        return collapsed;
    }
}