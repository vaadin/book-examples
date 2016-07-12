package com.vaadin.book.examples.component;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MyCalendarEvent {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    
    String caption;
    String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date   startDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date   endDate;
    String styleName;
    
    public MyCalendarEvent() {
    }
    
    public MyCalendarEvent(String caption, String description,
        Date startDate, Date endDate, String styleName) {
        this.caption = caption;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.styleName = styleName;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getStyleName() {
        return styleName;
    }
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
