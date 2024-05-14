package com.pchnch.uxcurrencyexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponse {
    private String userApp;
    private String monedaOrigen;
    private double montoMonedaOrigen;
    private String monedaDestino;
    private double tipoCambioMonedaDestino;
    private double montoMonedaDestino;
}
