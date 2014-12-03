package com.vaadin.book.examples.addons.jpacontainer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// BEGIN-EXAMPLE: jpacontainer.hierarchical
@Entity
public class CelestialBody implements Serializable {
    private static final long serialVersionUID = -6425366814448671541L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    
    private String  name;

    @ManyToOne
    private CelestialBody parent;
    
    /** Default constructor required for JPA. */
    public CelestialBody() {
    }
    
    public CelestialBody(String name, CelestialBody parent) {
        this.name   = name;
        this.parent = parent;
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
    public CelestialBody getParent() {
        return parent;
    }
    public void setParent(CelestialBody parent) {
        this.parent = parent;
    }
}
// END-EXAMPLE: jpacontainer.hierarchical
