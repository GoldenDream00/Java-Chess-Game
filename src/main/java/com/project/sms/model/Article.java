package com.project.sms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Document("Article")

public class Article {
    @Id
    private String id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Article is required")
    private String article;
    @NotBlank(message = "Author is required")
    private String author;
    @CreatedDate
    private Date dataPublished;
    @URL(message = "Please type correct URL.")
    private String pictureUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDataPublished() {
        return dataPublished;
    }

    public void setDataPublished(Date dataPublished) {
        this.dataPublished = dataPublished;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
