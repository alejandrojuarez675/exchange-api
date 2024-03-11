package com.alejua.app.services.impl;

import com.alejua.app.exceptions.CustomException;
import com.alejua.app.exceptions.NotAllowedMoneyCode;
import com.alejua.app.ports.MoneyCache;
import com.alejua.app.ports.MoneyDatasource;
import com.alejua.app.services.MoneyService;
import com.alejua.domain.Cache;
import com.alejua.domain.ExchangeRate;
import com.alejua.domain.Money;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MoneyServiceImpl implements MoneyService {

    private final MoneyCache moneyCache;
    private final MoneyDatasource moneyDatasource;

    public MoneyServiceImpl(MoneyCache moneyCache, MoneyDatasource moneyDatasource) {
        this.moneyCache = moneyCache;
        this.moneyDatasource = moneyDatasource;
    }

    @Override
    public Mono<ExchangeRate> getExchangeRate(String from, String to) throws CustomException {
        Money fromMoney = getAndValidateMoneyCode(from);
        Money toMoney = getAndValidateMoneyCode(to);

        return moneyCache.getExchangeRate(fromMoney, toMoney)
                .filter(Cache::isAlive)
                .map(cache -> new ExchangeRate(cache.getData(), cache.getLastUpdate()))
                .switchIfEmpty(getAndSaveNewExchangeRate(fromMoney, toMoney));
    }

    private Mono<ExchangeRate> getAndSaveNewExchangeRate(Money from, Money to) throws CustomException {
        return moneyDatasource.getExchangeRate(from, to)
                .doOnNext(exchangeRate -> moneyCache.saveExchangeRate(from, to, exchangeRate.value()));
    }

    private Money getAndValidateMoneyCode(String moneyCode) throws NotAllowedMoneyCode {
        try {
            return Money.valueOf(moneyCode);
        } catch (IllegalArgumentException e) {
            throw new NotAllowedMoneyCode(moneyCode);
        }
    }
}
