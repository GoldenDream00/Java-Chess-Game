package com.project.sms.socket.controllers;

import com.project.sms.model.Game;
import com.project.sms.model.Student;
import com.project.sms.service.GameService;
import com.project.sms.service.StudentService;
import com.project.sms.socket.model.ApplicationStates;
import com.project.sms.socket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Random;

@Controller
public class MessageController {


    @Autowired
    ApplicationStates states;
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private GameService gameService;

    @Autowired
    private StudentService studentService;
    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        states.addUser(chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    }
    @MessageMapping("/chat.randomUser")
    public void chooseRandomUser(ChatMessage chatMessage) {
        List<Student> students = studentService.findAllWithoutGuardian();
        Random r = new Random();
        int random = r.nextInt(students.size());
        String randomStudentNickName = students.get(random).getNickname();
        chatMessage.setType("RANDOM_CHOOSE");
        if(randomStudentNickName.matches(chatMessage.getSender())) {
            chatMessage.setContent("false");
        }
        else {
            chatMessage.setContent("true");
            chatMessage.setReceiver(randomStudentNickName);
        }

        template.convertAndSendToUser(chatMessage.getSender(), "/queue/connection", chatMessage);
    }
    @MessageMapping("/chat.checkUser")
    public void checkUser(ChatMessage chatMessage) {
        String inviteName = chatMessage.getReceiver();
        ChatMessage message = new ChatMessage();
        message.setType("CHECK_USER");
        message.setReceiver(chatMessage.getSender());
        message.setContent("DEAD");
        if (states.getUsers().contains(inviteName)) {
            message.setContent("ALIVE");
        }
        template.convertAndSendToUser(chatMessage.getSender(), "/queue/connection", message);
    }


    @MessageMapping("/chat.sendRequest")
    public void SendRequest(ChatMessage chatMessage) {
        chatMessage.setType("REQUEST_ACTION");
        chatMessage.setContent("INVITE");
        template.convertAndSendToUser(chatMessage.getReceiver(), "/queue/connection", chatMessage);

    }
    @MessageMapping("/chat.RequestAction")
    public void RequestAction(ChatMessage chatMessage) {
        ChatMessage message = new ChatMessage();
        message.setType("REQUEST_ACTION");
        message.setSender(chatMessage.getSender());
        message.setReceiver(chatMessage.getReceiver());
        if(chatMessage.getContent().matches("ACCEPT")) {
            message.setContent("ACCEPT");
        }
        else {
            message.setContent("CANCEL");
        }
        template.convertAndSendToUser(chatMessage.getSender(), "/queue/connection", message);
        if(chatMessage.getContent().matches("ACCEPT")) {
            message.setType("GAME_START");

            String ID = gameService.CREATE_GAME(chatMessage.getReceiver(), chatMessage.getSender());
            message.setContent(ID);
            template.convertAndSendToUser(chatMessage.getReceiver(), "/queue/connection", message);
            template.convertAndSendToUser(chatMessage.getSender(), "/queue/connection", message);
        }
    }
    @MessageMapping("/game.move")
    public void movePiece(ChatMessage chatMessage) {
        ChatMessage message = new ChatMessage();
        message.setType("MOVE_PIECE");
        message.setContent(chatMessage.getContent());
        message.setSender(chatMessage.getSender());
        message.setReceiver(chatMessage.getReceiver());
        template.convertAndSendToUser(chatMessage.getReceiver(), "/queue/connection", message);
    }

    @MessageMapping("/game.finish")
    public void finishGame(ChatMessage chatMessage) {
        ChatMessage message = new ChatMessage();
        String Winner = chatMessage.getType();
        String fen = chatMessage.getContent().split("@@")[0];
        String pgn = chatMessage.getContent().split("@@")[1];
        String gameID = chatMessage.getSender();
        Game updatedGame = gameService.updateGame(gameID, fen, pgn, Winner);
    }
}
