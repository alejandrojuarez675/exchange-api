package com.alejua.infra.adapters.clients;

import com.alejua.app.exceptions.CustomException;
import com.alejua.app.exceptions.NotAllowedExchangeRateException;
import com.alejua.app.ports.MoneyDatasource;
import com.alejua.domain.ExchangeRate;
import com.alejua.domain.Money;
import com.alejua.infra.adapters.clients.usecases.ArsToBrlDatasource;
import com.alejua.infra.adapters.clients.usecases.ArsToEurDatasource;
import com.alejua.infra.adapters.clients.usecases.ArsToUsdDatasource;
import com.alejua.infra.adapters.clients.usecases.BrlToArsDatasource;
import com.alejua.infra.adapters.clients.usecases.EurToArsDatasource;
import com.alejua.infra.adapters.clients.usecases.UsdToArsDatasource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class MoneyWebScrapingDatasource implements MoneyDatasource {

    private final ArsToUsdDatasource arsToUsdDatasource;
    private final UsdToArsDatasource usdToArsDatasource;
    private final ArsToBrlDatasource arsToBrlDatasource;
    private final BrlToArsDatasource brlToArsDatasource;
    private final ArsToEurDatasource arsToEurDatasource;
    private final EurToArsDatasource eurToArsDatasource;

    public MoneyWebScrapingDatasource(
            ArsToUsdDatasource arsToUsdDatasource,
            UsdToArsDatasource usdToArsDatasource,
            ArsToBrlDatasource arsToBrlDatasource,
            BrlToArsDatasource brlToArsDatasource,
            ArsToEurDatasource arsToEurDatasource,
            EurToArsDatasource eurToArsDatasource
    ) {
        this.arsToUsdDatasource = arsToUsdDatasource;
        this.usdToArsDatasource = usdToArsDatasource;
        this.arsToBrlDatasource = arsToBrlDatasource;
        this.brlToArsDatasource = brlToArsDatasource;
        this.arsToEurDatasource = arsToEurDatasource;
        this.eurToArsDatasource = eurToArsDatasource;
    }

    @Override
    public Mono<ExchangeRate> getExchangeRate(Money from, Money to) throws CustomException {
        // TODO REPLACE THIS CODE
        Mono<Double> value;
        if (from == Money.ARS && to == Money.USD) {
            value = arsToUsdDatasource.getRateArsToUsd();
        } else if (from == Money.USD && to == Money.ARS) {
            value = usdToArsDatasource.getRateUsdToArs();
        } else if (from == Money.ARS && to == Money.BRL) {
            value = arsToBrlDatasource.getRateArsToBrl();
        } else if (from == Money.BRL && to == Money.ARS) {
            value = brlToArsDatasource.getRateBrlToArs();
        } else if (from == Money.ARS && to == Money.EUR) {
            value = arsToEurDatasource.getRateArsToEur();
        } else if (from == Money.EUR && to == Money.ARS) {
            value = eurToArsDatasource.getRateEurToArs();
        } else {
            throw new NotAllowedExchangeRateException("Exchange rate not implemented yet");
        }

        return value.map(x -> new ExchangeRate(x, LocalDateTime.now()));
    }
}
