package com.alejua.domain;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class AllowedConversionRate {
    public static final Map<Pair<Money, Money>, String> MAP = Map.of(
            new ImmutablePair<>(Money.ARS, Money.USD), "ARS/USD",
            new ImmutablePair<>(Money.USD, Money.ARS), "USD/ARS",
            new ImmutablePair<>(Money.ARS, Money.EUR), "ARS/EUR",
            new ImmutablePair<>(Money.EUR, Money.ARS), "EUR/ARS",
            new ImmutablePair<>(Money.ARS, Money.BRL), "ARS/BRL",
            new ImmutablePair<>(Money.BRL, Money.ARS), "BRL/ARS"
    );
}
