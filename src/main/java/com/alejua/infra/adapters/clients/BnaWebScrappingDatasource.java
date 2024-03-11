package com.alejua.infra.adapters.clients;

import com.alejua.app.exceptions.FeignClientException;
import com.alejua.infra.adapters.clients.usecases.ArsToUsdDatasource;
import com.alejua.infra.adapters.clients.usecases.UsdToArsDatasource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class BnaWebScrappingDatasource implements ArsToUsdDatasource, UsdToArsDatasource {

    public Double getRateArsToUsd() throws FeignClientException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.bna.com.ar/Personas"))
                .GET()
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            String arsToUsdString = body.split("Dolar U.S.A</td>")[1].split("<td>")[1].split("</td>")[0];
            return Double.valueOf(arsToUsdString.split(",")[0] + "." + arsToUsdString.split(",")[1]);
        } catch (IOException | InterruptedException e) {
            throw new FeignClientException("Error on BNA page scrapping of USD");
        }
    }

    @Override
    public Double getRateUsdToArs() throws FeignClientException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.bna.com.ar/Personas"))
                .GET()
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            String arsToUsdString = body.split("Dolar U.S.A</td>")[1].split("</td>")[1].split("<td>")[1].split("</td>")[0];
            return Double.valueOf(arsToUsdString.split(",")[0] + "." + arsToUsdString.split(",")[1]);
        } catch (IOException | InterruptedException e) {
            throw new FeignClientException("Error on BNA page scrapping of USD");
        }
    }
}
