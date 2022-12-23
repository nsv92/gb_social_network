package ru.gb.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.gb.bot.Bot;
import ru.gb.bot.entities.BotUser;
import ru.gb.bot.entities.Notification;

import javax.persistence.EntityNotFoundException;

@Service
public class NotificationService {

    private final Bot bot;

    private final BotUserService userService;

    @Autowired
    public NotificationService(Bot bot, BotUserService userService) {
        this.bot = bot;
        this.userService = userService;
    }

    public void sendNotification(Notification notification) {
        try {
            BotUser botUser = userService.getBotUserByNickName(notification.getNickName());
            SendMessage sendMessage = new SendMessage(botUser.getChatId().toString(), notification.getMessage());
            bot.executeNotification(sendMessage);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
