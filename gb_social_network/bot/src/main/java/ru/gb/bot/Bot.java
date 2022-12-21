package ru.gb.bot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.gb.bot.config.Config;
import ru.gb.bot.entities.Notification;
import ru.gb.bot.handlers.CommandHandler;

import java.util.ArrayList;
import java.util.List;

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
        List<BotCommand> commandList = new ArrayList<>();

        commandList.add(new BotCommand("/help", "Список всех команд"));
        commandList.add(new BotCommand("/start", "Начало работы с ботом"));
        commandList.add(new BotCommand("/subscribe", "Подписка на уведоиления"));
        commandList.add(new BotCommand("/unsubscribe", "Отписка от уведоилений"));

        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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

    public void executeNotification(SendMessage sendMessage) {
        try {
            execute(sendMessage);
            log.info("Notification was sent successfully!");
        } catch (TelegramApiException e) {
            log.error("Exception during sending notification message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
