package com.alejua.app.services.impl;

import com.alejua.app.exceptions.CustomException;
import com.alejua.app.exceptions.NotAllowedExchangeRateException;
import com.alejua.app.exceptions.NotAllowedMoneyCode;
import com.alejua.app.ports.MoneyCache;
import com.alejua.app.ports.MoneyDatasource;
import com.alejua.app.services.MoneyService;
import com.alejua.domain.AllowedConversionRate;
import com.alejua.domain.Cache;
import com.alejua.domain.ExchangeRate;
import com.alejua.domain.ExchangeRateData;
import com.alejua.domain.Money;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
    public Flux<ExchangeRateData> getDashboard() {
        return Flux.fromIterable(AllowedConversionRate.MAP.keySet())
                .flatMap(this::getExchangeRate);
    }

    private Mono<ExchangeRateData> getExchangeRate(Pair<Money, Money> entry) {
        return getExchangeRate(entry.getLeft().toString(), entry.getRight().toString())
                .map(x -> new ExchangeRateData(
                        entry.getLeft().toString(),
                        entry.getRight().toString(),
                        x.value(),
                        x.lastUpdate()
                ));
    }

    @Override
    public Mono<ExchangeRate> getExchangeRate(String from, String to) throws CustomException {
        Money fromMoney = getAndValidateMoneyCode(from);
        Money toMoney = getAndValidateMoneyCode(to);
        validateAllowedRate(fromMoney, toMoney);

        return moneyCache.getExchangeRate(fromMoney, toMoney)
                .filter(Cache::isAlive)
                .map(cache -> new ExchangeRate(cache.getData(), cache.getLastUpdate()))
                .switchIfEmpty(getAndSaveNewExchangeRate(fromMoney, toMoney));
    }

    private Money getAndValidateMoneyCode(String moneyCode) throws NotAllowedMoneyCode {
        try {
            return Money.valueOf(moneyCode);
        } catch (IllegalArgumentException e) {
            throw new NotAllowedMoneyCode(moneyCode);
        }
    }

    private void validateAllowedRate(Money fromMoney, Money toMoney) throws NotAllowedExchangeRateException {
        String conversionRateWay = AllowedConversionRate.MAP.get(new ImmutablePair<>(fromMoney, toMoney));
        if (conversionRateWay == null) {
            throw new NotAllowedExchangeRateException("Exchange rate not implemented yet");
        }
    }

    private Mono<ExchangeRate> getAndSaveNewExchangeRate(Money from, Money to) throws CustomException {
        return moneyDatasource.getExchangeRate(from, to)
                .doOnNext(exchangeRate -> moneyCache.saveExchangeRate(from, to, exchangeRate.value()));
    }
}
