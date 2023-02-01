package com.project.sms.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("Game")
public class Game {
    @Id
    private String id;
    private String fen;
    private String pgn;
    @DBRef
    private Student player1;
    @DBRef
    private Student player2;
    private Boolean winner;
    @CreatedDate
    private Date gameStarted;
    @LastModifiedDate
    private Date gameFinished;

    public String getId() {
        return id;
    }

    public String getPgn() {
        return pgn;
    }

    public String getFen() {
        return fen;
    }

    public Boolean getWinner() {
        return winner;
    }

    public Date getGameFinished() {
        return gameFinished;
    }

    public Student getPlayer1() {
        return player1;
    }

    public Student getPlayer2() {
        return player2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public void setGameFinished(Date gameFinished) {
        this.gameFinished = gameFinished;
    }

    public void setPlayer1(Student player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Student player2) {
        this.player2 = player2;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
