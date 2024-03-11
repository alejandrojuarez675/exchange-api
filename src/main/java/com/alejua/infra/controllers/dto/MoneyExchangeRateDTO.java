package com.alejua.infra.controllers.dto;

import java.time.LocalDateTime;

public record MoneyExchangeRateDTO(
        String from,
        String to,
        Double value,
        LocalDateTime lastUpdate
) {
}
