package ru.gb.bot.handlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static ru.gb.bot.Utils.getUserNameFromUpdate;

@Component
@Log4j2
public class CommandHandler {


    public SendMessage handleTextMessage(Update update) {
        String userName = getUserNameFromUpdate(update);
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        switch (text) {
            case "/start":
                log.info("START command from user: {}", userName);
                String str = "Привет " + userName + "!" + "\n" +
                        "Это gb_social_network_bot!" + "\n";
                return new SendMessage(String.valueOf(chatId), str);

            default:
                log.info("UNKNOWN command from user: {}", userName);
                return new SendMessage(String.valueOf(chatId), "Неизвестная команда!");
        }

    }
}
