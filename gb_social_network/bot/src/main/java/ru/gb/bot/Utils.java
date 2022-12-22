package ru.gb.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class Utils {
    public static String getUserNameFromUpdate(Update update) {
        User user;
        if (update.getMessage() != null) {
            user = update.getMessage().getFrom();
        } else {
            user = update.getCallbackQuery().getFrom();
        }
        return (user.getUserName() == null ? user.getFirstName() : user.getUserName());
    }
}
