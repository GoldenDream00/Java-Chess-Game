package com.project.sms.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("Register")
public class Register {
    @Id
    private String id;
    @CreatedDate
    private Date registrationDate;
    @DBRef
    private Student student;
    @DBRef
    private Event event;

    public Event getEvent() {
        return event;
    }

    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


}
