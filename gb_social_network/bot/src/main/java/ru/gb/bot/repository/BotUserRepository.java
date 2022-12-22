package ru.gb.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.bot.entities.BotUser;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {

    public BotUser getBotUserByAppNickname(String nickName);
}
