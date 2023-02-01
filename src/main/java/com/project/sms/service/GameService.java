package com.project.sms.service;

import com.project.sms.model.Game;
import com.project.sms.model.Student;
import com.project.sms.repository.GameRepository;
import com.project.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    StudentService studentService;
    public String CREATE_GAME(String player1, String player2) {
        Student p1 = studentService.findByName(player1);
        Student p2 = studentService.findByName(player2);
        Game game = new Game();
        game.setPlayer1(p1);
        game.setPlayer2(p2);
        Game newGame = gameRepository.save(game);
        return newGame.getId();
    }
    public Game updateGame(String id, String fen, String pgn, String winner) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        Game game = gameOptional.get();
        game.setFen(fen);
        game.setPgn(pgn);
        game.setWinner(game.getPlayer1().getNickname().matches(winner) ? true:false);
        Game lastGame = gameRepository.save(game);
        return lastGame;

    }
}
