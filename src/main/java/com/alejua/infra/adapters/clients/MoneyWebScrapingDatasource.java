package com.alejua.infra.adapters.clients;

import com.alejua.app.exceptions.CustomException;
import com.alejua.app.exceptions.NotAllowedExchangeRateException;
import com.alejua.app.ports.MoneyDatasource;
import com.alejua.domain.ExchangeRate;
import com.alejua.domain.Money;
import com.alejua.infra.adapters.clients.usecases.ArsToUsdDatasource;
import com.alejua.infra.adapters.clients.usecases.UsdToArsDatasource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class MoneyWebScrapingDatasource implements MoneyDatasource {

    private final ArsToUsdDatasource arsToUsdDatasource;
    private final UsdToArsDatasource usdToArsDatasource;

    public MoneyWebScrapingDatasource(ArsToUsdDatasource arsToUsdDatasource, UsdToArsDatasource usdToArsDatasource) {
        this.arsToUsdDatasource = arsToUsdDatasource;
        this.usdToArsDatasource = usdToArsDatasource;
    }

    @Override
    public Mono<ExchangeRate> getExchangeRate(Money from, Money to) throws CustomException {

        Double value;
        if (from == Money.ARS && to == Money.USD) {
            value = arsToUsdDatasource.getRateArsToUsd();
        } else if (from == Money.USD && to == Money.ARS) {
            value = usdToArsDatasource.getRateUsdToArs();
        } else {
            throw new NotAllowedExchangeRateException("Exchange rate not implemented yet");
        }

        return Mono.just(new ExchangeRate(
                value,
                LocalDateTime.now()
        ));
    }
}
