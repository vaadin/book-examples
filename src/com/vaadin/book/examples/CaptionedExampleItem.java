package com.vaadin.book.examples;

/** Example category. */
abstract public class CaptionedExampleItem extends AbstractExampleItem {
    private static final long serialVersionUID = 418811233718496311L;
    
    /** A short display name of the example for the menu */
    protected String shortName;
    
    /** Create a new redirection from an obsolete example ID to a new one. */
    public CaptionedExampleItem(String itemid, String shortName) {
        super(itemid);
        
        this.shortName = shortName; 
    }
    
    public String getShortName() {
        return shortName;
    }
}