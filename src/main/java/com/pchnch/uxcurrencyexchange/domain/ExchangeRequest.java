package com.pchnch.uxcurrencyexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequest {
    private String monedaOrigen;
    private double montoMonedaOrigen;
    private String monedaDestino;
    private double tipoCambioMonedaDestino;
}

