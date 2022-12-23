package ru.gb.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.bot.entities.Notification;
import ru.gb.bot.services.NotificationService;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * На вход принимает JSON вида:
     * {
     *     "nickName": "some_nickName",
     *     "message": "some_notification"
     * }
     **/
    @PostMapping()
    public Notification friendshipNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(notification);
        return notification;
    }

}
