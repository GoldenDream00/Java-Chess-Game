package com.project.sms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("Attendance")
public class Attendance {
    @Id
    private String id;
    private Date date;
    private Boolean paid;
    private Date paidDate;
    @DBRef
    private Event event;
    @DBRef
    private List<Student> student;

    public String getId() {
        return id;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Date getDate() {
        return date;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public Event getEvent() {
        return event;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }
}
