package com.alejua.app.services;

import com.alejua.app.exceptions.CustomException;
import com.alejua.domain.ExchangeRate;
import reactor.core.publisher.Mono;

public interface MoneyService {
    Mono<ExchangeRate> getExchangeRate(String from, String to) throws CustomException;
}
