package com.alejua.infra.controllers;

import com.alejua.app.exceptions.CustomException;
import com.alejua.app.services.MoneyService;
import com.alejua.infra.controllers.dto.MoneyExchangeRateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MoneyController {

    private final MoneyService moneyService;

    public MoneyController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }

    @GetMapping("/api/v1/money/dashboard")
    public Flux<MoneyExchangeRateDTO> getDashboard() {
        return moneyService.getDashboard()
                .map(x -> new MoneyExchangeRateDTO(x.from(), x.to(), x.value(), x.lastUpdate()));
    }

    @GetMapping("/api/v1/money/rate/{from}/{to}")
    public Mono<MoneyExchangeRateDTO> getRate(
            @PathVariable String from,
            @PathVariable String to
    ) throws CustomException {
        return moneyService.getExchangeRate(from, to)
                .map(x -> new MoneyExchangeRateDTO(from, to, x.value(), x.lastUpdate()));
    }
}
