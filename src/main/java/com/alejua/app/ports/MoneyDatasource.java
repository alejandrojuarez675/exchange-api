package com.alejua.app.ports;

import com.alejua.app.exceptions.CustomException;
import com.alejua.domain.ExchangeRate;
import com.alejua.domain.Money;
import reactor.core.publisher.Mono;

public interface MoneyDatasource {

    Mono<ExchangeRate> getExchangeRate(Money from, Money to) throws CustomException;
}
