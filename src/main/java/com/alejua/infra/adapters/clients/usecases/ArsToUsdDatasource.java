package com.alejua.infra.adapters.clients.usecases;

import com.alejua.app.exceptions.FeignClientException;

public interface ArsToUsdDatasource {

    Double getRateArsToUsd() throws FeignClientException;
}
