package ru.gb.bot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.gb.bot.config.Config;
import ru.gb.bot.handlers.CommandHandler;

import static ru.gb.bot.Utils.getUserNameFromUpdate;

@Component
@Log4j2
public class Bot extends TelegramLongPollingBot {

    private final Config config;

    private final CommandHandler commandHandler;

    @Autowired
    public Bot(Config config, CommandHandler commandHandler) {
        this.config = config;
        this.commandHandler = commandHandler;
    }

    @Override
    public String getBotUsername() {
       return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            log.info("New update received from: {}, message: {}",
                    getUserNameFromUpdate(update), update.getMessage().getText());
            if (update.getMessage().hasText()) {
                try {
                    execute(commandHandler.handleTextMessage(update));
                } catch (TelegramApiException e) {
                    log.error("Exception during sending message: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            } else log.error("Null message from user: {}", getUserNameFromUpdate(update));
        }
    }
}
