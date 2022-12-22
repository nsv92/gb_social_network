package ru.gb.bot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "bot_users", schema = "bot_db")
@NoArgsConstructor
@AllArgsConstructor
public class BotUser {

    @Id
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "tg_username", nullable = false, unique = true)
    private String tgUserName;

    @Column(name = "app_nickname", nullable = false, unique = true)
    private String appNickname;

    @Column(name = "subscription")
    private boolean subscription;

}
