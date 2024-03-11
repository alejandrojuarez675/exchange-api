package com.alejua.domain;

import java.time.LocalDateTime;

public record ExchangeRate(
        Double value,
        LocalDateTime lastUpdate
) {
}
