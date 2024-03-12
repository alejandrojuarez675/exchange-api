package com.alejua.app.services;

import com.alejua.app.exceptions.CustomException;
import com.alejua.domain.ExchangeRate;
import com.alejua.infra.controllers.dto.MoneyExchangeRateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MoneyService {
    Mono<ExchangeRate> getExchangeRate(String from, String to) throws CustomException;

    Flux<MoneyExchangeRateDTO> getDashboard();
}
