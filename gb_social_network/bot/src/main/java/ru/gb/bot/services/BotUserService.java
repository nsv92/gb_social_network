package ru.gb.bot.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.bot.entities.BotUser;
import ru.gb.bot.repository.BotUserRepository;

import javax.persistence.EntityNotFoundException;

@Service
@Log4j2
public class BotUserService {

    private final BotUserRepository botUserRepository;

    @Autowired
    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public void subscribe(Long chatId, String nickName, String userName) {
        BotUser botUser = new BotUser(chatId, userName, nickName, true);
        botUserRepository.save(botUser);
        log.info("User {} with app_nickname {} subscribed successfully.", userName, nickName);
    }

    @Transactional
    public void unsubscribe(Long chatId) throws EntityNotFoundException {
        BotUser botUser = botUserRepository.getReferenceById(chatId);
        botUser.setSubscription(false);
        botUserRepository.save(botUser);
    }
}
