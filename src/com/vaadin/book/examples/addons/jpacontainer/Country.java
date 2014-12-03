package com.vaadin.book.examples.addons.jpacontainer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// BEGIN-EXAMPLE: jpacontainer.basic
@Entity
public class Country implements Serializable {
    private static final long serialVersionUID = 508426585088564210L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    
    private String  name;

    @OneToMany(mappedBy = "country")
    private Set<Person> people;
    
    /** Default constructor is required by JPA */
    public Country() {
        people = new HashSet<Person>();
    }
    
    public Country(String name) {
        this.name = name;
        this.people = new HashSet<Person>();
    }
    
    /** Adds a person to the country. */
    public void addPerson(Person person) {
        people.add(person);
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
    public Set<Person> getPeople() {
        return people;
    }
    public void setPeople(Set<Person> people) {
        this.people = people;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
// END-EXAMPLE: jpacontainer.basic
