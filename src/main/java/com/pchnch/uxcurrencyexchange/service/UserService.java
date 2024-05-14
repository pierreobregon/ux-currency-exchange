package com.pchnch.uxcurrencyexchange.service;

import com.pchnch.uxcurrencyexchange.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://gorest.co.in/public/v2").build();
    }

    public Mono<String> validateUser(String userApp) {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class)
                .filter(user -> user.getId().equals(userApp))
                .singleOrEmpty()
                .map(User::getName)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario No autorizado")));
    }
}



