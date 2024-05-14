package com.pchnch.uxcurrencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class })
public class UxCurrencyExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UxCurrencyExchangeApplication.class, args);
	}

}
