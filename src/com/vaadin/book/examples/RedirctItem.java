package com.vaadin.book.examples;

/** For redirecting obsolete example IDs. */
public class RedirctItem extends AbstractExampleItem {
    private static final long serialVersionUID = 418811233718496311L;
    public String redirectid;

    /** Create a new redirection from an obsolete example ID to a new one. */
    public RedirctItem(String itemid, String redirectid) {
        super(itemid);
        
        this.redirectid = redirectid;
    }
}