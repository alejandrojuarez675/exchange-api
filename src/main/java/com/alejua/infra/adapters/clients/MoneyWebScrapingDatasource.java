package com.alejua.infra.adapters.clients;

import com.alejua.app.ports.MoneyDatasource;
import com.alejua.domain.ExchangeRate;
import com.alejua.domain.Money;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class MoneyWebScrapingDatasource implements MoneyDatasource {
    @Override
    public Mono<ExchangeRate> getExchangeRate(Money from, Money to) {
        return Mono.just(new ExchangeRate(
                new Random().nextDouble(950.0, 1300),  // TODO replace this hardcode for web scrapping
                LocalDateTime.now()
        ));
    }
}
