package com.project.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Document("Event")
public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    @DBRef
    private Address address;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime  startEvent;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endEvent;

    private float price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getEndEvent() {
        return endEvent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartEvent() {
        return startEvent;
    }

    public void setEndEvent(LocalDateTime endEvent) {
        this.endEvent = endEvent;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setStartEvent(LocalDateTime startEvent) {
        this.startEvent = startEvent;
    }


}
