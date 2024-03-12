package com.alejua.infra.adapters.clients.usecases;

import com.alejua.app.exceptions.FeignClientException;
import reactor.core.publisher.Mono;

public interface ArsToBrlDatasource {
    Mono<Double> getRateArsToBrl() throws FeignClientException;

}
