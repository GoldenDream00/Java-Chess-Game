package com.project.sms.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("Homework")
public class Homework {

    @Id
    private String id;
    private String description;
    private String title;
    @CreatedDate
    private Date dataPublished;
    private String pictureUrl;
    private String result;
    @DBRef
    private List<Puzzle> puzzle;
    @DBRef
    private Student student;
    @DBRef
    private Teacher teacher;

    public String getDescription() {
        return description;
    }

    public void setDataPublished(Date dataPublished) {
        this.dataPublished = dataPublished;
    }

    public Date getDataPublished() {
        return dataPublished;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<Puzzle> getPuzzle() {
        return puzzle;
    }

    public String getResult() {
        return result;
    }

    public Student getStudent() {
        return student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setPuzzle(List<Puzzle> puzzle) {
        this.puzzle = puzzle;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
