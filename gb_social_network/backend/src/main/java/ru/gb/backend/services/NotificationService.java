package ru.gb.backend.services;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    //TODO реализовать сервис нотификаций
    private String message = "New post created!";

    public NotificationService() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
