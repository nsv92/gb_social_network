package ru.gb.bot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.gb.bot.dto.JwtRequest;

@Service
@AllArgsConstructor
public class AuthService {

    private final WebClient webClient = WebClient.create();

    public Mono<String> authResult(JwtRequest request) throws WebClientResponseException {
        return webClient
                .post()
                .uri("http://localhost:8080/gb_social_network/auth")
                .body(Mono.just(request), JwtRequest.class)
                .retrieve()
                .bodyToMono(String.class);
    }

}
