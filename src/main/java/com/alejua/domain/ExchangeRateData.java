package com.alejua.domain;

import java.time.LocalDateTime;

public record ExchangeRateData(
        String from,
        String to,
        Double value,
        LocalDateTime lastUpdate
) {
}
