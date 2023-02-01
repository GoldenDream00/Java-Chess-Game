package com.project.sms.socket.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationStates {
    private List<String> currentUsers;
    private static Object lock = new Object();

    public ApplicationStates() {
        currentUsers = new ArrayList<String>();
    }
    public void addUser(String nickName) {
        synchronized (lock) {
            currentUsers.add(nickName);
        }
    }
    public void removeUser(String nickName) {
        synchronized (lock) {
            currentUsers.remove(currentUsers.indexOf(nickName));
        }
    }

    public List<String> getUsers() {
        synchronized (lock) {
            return currentUsers;
        }
    }
}
