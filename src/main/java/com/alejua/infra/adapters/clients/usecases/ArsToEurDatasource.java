package com.alejua.infra.adapters.clients.usecases;

import com.alejua.app.exceptions.FeignClientException;
import reactor.core.publisher.Mono;

public interface ArsToEurDatasource {
    Mono<Double> getRateArsToEur
            () throws FeignClientException;

}
