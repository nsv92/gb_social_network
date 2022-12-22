package ru.gb.bot.handlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.gb.bot.dto.JwtRequest;
import ru.gb.bot.services.AuthService;
import ru.gb.bot.services.BotUserService;

import javax.persistence.EntityNotFoundException;

import static ru.gb.bot.Utils.getUserNameFromUpdate;

@Component
@Log4j2
public class CommandHandler {

    private final AuthService authService;

    private final BotUserService userService;

    @Autowired
    public CommandHandler(AuthService authService, BotUserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    //TODO много сообщений, вывести их в константы
    public SendMessage handleTextMessage(Update update) {
        String userName = getUserNameFromUpdate(update);
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String[] command = text.split(" ");
        switch (command[0]) {
            case "/start":
                log.info("START command from user: {}", userName);
                String str = "Привет " + userName + "!" + "\n" +
                        "Это gb_social_network_bot!" + "\n";
                return new SendMessage(String.valueOf(chatId), str);

            case "/subscribe":
                if (command.length != 3) {
                    return new SendMessage(chatId.toString(), "Формат команды: /subscribe ваш_никнейм ваш_пароль");
                }
                log.info("SUBSCRIBE command from user: {}", userName);
                return authAndSubscribe(chatId, command[1], command[2], userName);

            case "/unsubscribe":
                log.info("UNSUBSCRIBE command from user: {}", userName);
                return unsubscribe(chatId, userName);


            default:
                log.info("UNKNOWN command from user: {}", userName);
                return new SendMessage(String.valueOf(chatId), "Неизвестная команда!");
        }

    }

    private SendMessage authAndSubscribe(Long chatId, String nickName, String password, String userName) {
        JwtRequest request = new JwtRequest(nickName, password);
        try {
            String response = authService.authResult(request).block();
            log.info(response);
        } catch (WebClientResponseException e) {
            log.info("Unsuccessful auth attempt by {}", userName);
            return new SendMessage(chatId.toString(), "Неверный никнейм/пароль");
        }
        log.info("Successful auth attempt by {}", userName);
        userService.subscribe(chatId, nickName, userName);
        return new SendMessage(chatId.toString(), "Вы успешно подписались!");
    }

    private SendMessage unsubscribe(Long chatId, String userName) {
        try {
            userService.unsubscribe(chatId);
        } catch (EntityNotFoundException e) {
            return new SendMessage(chatId.toString(), "Вы не были подписаны на уведомления!");
        }
        log.info("User {} was unsubscribed!", userName);
        return new SendMessage(chatId.toString(), "Вы отписались от уведомлений!");
    }
}
