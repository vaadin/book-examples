package com.vaadin.book.examples.advanced.cdi;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

// BEGIN-EXAMPLE: advanced.cdi.navigation
/**
 * A stateful bean.
 */
@SessionScoped
public class User implements Serializable {
    private static final long serialVersionUID = -3963802566252092423L;

    private String name;
    
    public User() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
// END-EXAMPLE: advanced.cdi.navigation
