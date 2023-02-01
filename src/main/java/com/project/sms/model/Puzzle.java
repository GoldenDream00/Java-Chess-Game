package com.project.sms.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document("Puzzle")
public class Puzzle {
    @Id
    private String id;
    private String title;
    private String description;
    private String author;
    @CreatedDate
    private Date dataPublished;
    private String pictureurl;
    private String fen;
    private String pgn;
    private int ranking;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDataPublished() {
        return dataPublished;
    }

    public void setDataPublished(Date dataPublished) {
        this.dataPublished = dataPublished;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRanking() {
        return ranking;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public String getFen() {
        return fen;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

    public String getPgn() {
        return pgn;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Puzzle other = (Puzzle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
