package com.vaadin.book.examples.portlet;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = -4100598417936367677L;

    String name;
    int    age;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}