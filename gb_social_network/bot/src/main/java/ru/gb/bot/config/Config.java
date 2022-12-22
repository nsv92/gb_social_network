package ru.gb.bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Getter
@Setter
public class Config {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String token;

}
