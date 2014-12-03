package com.vaadin.book.examples.addons.jpacontainer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/*************************************************************************
 *  Basic Example
 *************************************************************************/

// BEGIN-EXAMPLE: jpacontainer.basic
@Entity
public class Person implements Serializable {
    private static final long serialVersionUID = -2740293361580718589L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    
    private String  name;
    private Integer age;

    @ManyToOne
    private Country country;
    
    /** Default constructor is required by JPA */
    public Person() {
    }
    
    public Person(String name, int age) {
        this.name = name;
        this.age  = age;
    }
    
    public Person(String name, int age, Country country) {
        this.name    = name;
        this.age     = age;
        this.country = country;
        country.addPerson(this);
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Country getCountry() {
        return country;
    }
    public void setCountry(Country country) {
        this.country = country;
        country.addPerson(this);
    }
}
// END-EXAMPLE: jpacontainer.basic
