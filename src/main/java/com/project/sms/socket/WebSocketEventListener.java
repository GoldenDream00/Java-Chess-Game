package com.project.sms.socket;

import com.project.sms.socket.model.ApplicationStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketEventListener {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    ApplicationStates states;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            states.removeUser(username);
        }
//        if(username != null) {
//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType("LEAVE");
//            chatMessage.setSender(username);
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
    }
}
