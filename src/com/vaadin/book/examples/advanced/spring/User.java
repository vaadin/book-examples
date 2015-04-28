package com.vaadin.book.examples.advanced.spring;

import java.io.Serializable;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

/**
 * A stateful bean.
 */
@SpringComponent
@VaadinSessionScope
public class User implements Serializable {
    private static final long serialVersionUID = -5560593616074492199L;

    private String name;
    
    public User() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
