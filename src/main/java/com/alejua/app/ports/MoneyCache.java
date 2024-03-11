package com.alejua.app.ports;

import com.alejua.domain.Cache;
import com.alejua.domain.Money;
import reactor.core.publisher.Mono;

public interface MoneyCache {

    Mono<Cache<Double>> getExchangeRate(Money from, Money to);
    void saveExchangeRate(Money from, Money to, Double value);
}
