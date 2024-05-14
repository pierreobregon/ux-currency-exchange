package com.pchnch.uxcurrencyexchange.expose.web;

import com.pchnch.uxcurrencyexchange.service.UserService;
import com.pchnch.uxcurrencyexchange.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;



    @PostMapping("/authenticate")
    public Mono<ResponseEntity<Map<String, String>>> authenticate(@RequestHeader("userApp") String userApp) {
        return userService.validateUser(userApp)
                .map(name -> {
                    String token = JwtUtil.generateToken(userApp);
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .onErrorResume(ex -> {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("codigoError", "401");
                    errorResponse.put("descripcionError", "Usuario No autorizado");
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse));
                });
    }
}