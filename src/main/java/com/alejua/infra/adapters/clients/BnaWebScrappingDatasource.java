package com.alejua.infra.adapters.clients;

import com.alejua.app.exceptions.FeignClientException;
import com.alejua.infra.adapters.clients.usecases.ArsToBrlDatasource;
import com.alejua.infra.adapters.clients.usecases.ArsToEurDatasource;
import com.alejua.infra.adapters.clients.usecases.ArsToUsdDatasource;
import com.alejua.infra.adapters.clients.usecases.BrlToArsDatasource;
import com.alejua.infra.adapters.clients.usecases.EurToArsDatasource;
import com.alejua.infra.adapters.clients.usecases.UsdToArsDatasource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.function.Function;

@Component
public class BnaWebScrappingDatasource implements ArsToUsdDatasource, UsdToArsDatasource, ArsToBrlDatasource,
        BrlToArsDatasource, ArsToEurDatasource, EurToArsDatasource {

    private static final Map<String, Function<String, String>> searcherFuncMap = Map.of(
            "ARS/USD", s -> s.split("Dolar U.S.A</td>")[1].split("<td>")[1].split("</td>")[0],
            "USD/ARS", s -> s.split("Dolar U.S.A</td>")[1].split("</td>")[1].split("<td>")[1].split("</td>")[0],
            "ARS/BRL", s -> s.split("Real \\*</td>")[1].split("<td>")[1].split("</td>")[0],
            "BRL/ARS", s -> s.split("Real \\*</td>")[1].split("</td>")[1].split("<td>")[1].split("</td>")[0],
            "ARS/EUR", s -> s.split("Euro</td>")[1].split("<td>")[1].split("</td>")[0],
            "EUR/ARS", s -> s.split("Euro</td>")[1].split("</td>")[1].split("<td>")[1].split("</td>")[0]
    );

    public Mono<Double> getRateArsToUsd() throws FeignClientException {
        return getConversionRateFromBna(searcherFuncMap.get("ARS/USD"));
    }

    @Override
    public Mono<Double> getRateUsdToArs() throws FeignClientException {
        return getConversionRateFromBna(searcherFuncMap.get("USD/ARS"));
    }

    @Override
    public Mono<Double> getRateArsToEur() throws FeignClientException {
        return getConversionRateFromBna(searcherFuncMap.get("ARS/EUR"));
    }

    @Override
    public Mono<Double> getRateEurToArs() throws FeignClientException {
        return getConversionRateFromBna(searcherFuncMap.get("EUR/ARS"));
    }

    @Override
    public Mono<Double> getRateArsToBrl() throws FeignClientException {
        return getConversionRateFromBna(searcherFuncMap.get("ARS/BRL")).map(x -> x/100);
    }

    @Override
    public Mono<Double> getRateBrlToArs() throws FeignClientException {
        return getConversionRateFromBna(searcherFuncMap.get("BRL/ARS")).map(x -> x/100);
    }

    public Mono<Double> getConversionRateFromBna(Function<String, String> searcherFunc) throws FeignClientException {
        return getHomeBnaPageBody()
                .map(searcherFunc)
                .map(this::getDoubleFromFormattedString);
    }

    private Mono<String> getHomeBnaPageBody() throws FeignClientException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.bna.com.ar/Personas"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Mono.just(response.body());
        } catch (IOException | InterruptedException e) {
            throw new FeignClientException("Error on BNA page scrapping of USD");
        }
    }

    private Double getDoubleFromFormattedString(String number) {
        return Double.valueOf(number.split(",")[0] + "." + number.split(",")[1]);
    }
}
