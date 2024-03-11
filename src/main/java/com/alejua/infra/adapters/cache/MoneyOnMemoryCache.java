package com.alejua.infra.adapters.cache;

import com.alejua.app.ports.MoneyCache;
import com.alejua.domain.Cache;
import com.alejua.domain.Money;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class MoneyOnMemoryCache implements MoneyCache {

    @Override
    public Mono<Cache<Double>> getExchangeRate(Money from, Money to) {
        return Mono.justOrEmpty(OnMemoryCacheData.data.get(getKey(from, to)));
    }

    @Override
    public void saveExchangeRate(Money from, Money to, Double value) {
        System.out.printf("Adding %s to cache%n", getKey(from, to));
        OnMemoryCacheData.data.put(
                getKey(from, to),
                new Cache<>(value, LocalDateTime.now())
        );
    }

    private String getKey(Money from, Money to) {
        return from.name() + "/" + to.name();
    }
}
