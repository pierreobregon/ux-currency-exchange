package com.pchnch.uxcurrencyexchange.expose.web;

import com.pchnch.uxcurrencyexchange.domain.ExchangeRequest;
import com.pchnch.uxcurrencyexchange.domain.ExchangeResponse;
import com.pchnch.uxcurrencyexchange.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ExchangeController {

    /*@PostMapping("/exchange")
    public Mono<ResponseEntity<ExchangeResponse>> exchange(@RequestHeader("_access_token_") String accessToken,
                                                           @RequestBody ExchangeRequest request) {
        return Mono.just(JwtUtil.validateToken(accessToken))
                .map(claims -> claims.getSubject())
                .map(userApp -> {
                    double montoMonedaDestino = request.getMontoMonedaOrigen() * request.getTipoCambioMonedaDestino();
                    log.info("Usuario {} realizó un cambio de moneda", userApp);
                    return ResponseEntity.ok(new ExchangeResponse(userApp, request.getMonedaOrigen(),
                            request.getMontoMonedaOrigen(), request.getMonedaDestino(),
                            request.getTipoCambioMonedaDestino(), montoMonedaDestino));
                })
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body((ExchangeResponse) Map.of("codigoError", "501", "descripcionError", "Error en el Sistema: " + ex.getMessage()))));
    }*/

    private final WebClient webClient = WebClient.create("http://localhost:8081/api-sp/exchange");

    @PostMapping("/exchange")
    public Mono<ResponseEntity<ExchangeResponse>> exchange(@RequestHeader("_access_token_") String accessToken,
                                                           @RequestBody ExchangeRequest request) {



        return Mono.just(JwtUtil.validateToken(accessToken))
                .map(claims -> claims.getSubject())
                .flatMap(userApp -> {
                    double montoMonedaDestino = request.getMontoMonedaOrigen() * request.getTipoCambioMonedaDestino();
                    log.info("Usuario {} realizó un cambio de moneda", userApp);

                    Map<String, Object> payload = new HashMap<>();
                    payload.put("divisaOrigen", request.getMonedaOrigen());
                    payload.put("divisaDestino", request.getMonedaDestino());
                    payload.put("tipoCambio", request.getTipoCambioMonedaDestino());
                    payload.put("userApp", userApp);

                    return webClient.put()
                            .bodyValue(payload)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .then(Mono.fromSupplier(() -> {

                                return ResponseEntity.ok(new ExchangeResponse(userApp, request.getMonedaOrigen(),
                                        request.getMontoMonedaOrigen(), request.getMonedaDestino(),
                                        request.getTipoCambioMonedaDestino(), montoMonedaDestino));
                            }));

                   /* return ResponseEntity.ok(new ExchangeResponse(userApp, request.getMonedaOrigen(),
                            request.getMontoMonedaOrigen(), request.getMonedaDestino(),
                            request.getTipoCambioMonedaDestino(), montoMonedaDestino));*/

                })
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body((ExchangeResponse) Map.of("codigoError", "501", "descripcionError", "Error en el Sistema: " + ex.getMessage()))));
    }
}
