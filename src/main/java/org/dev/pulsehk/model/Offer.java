package org.dev.pulsehk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Offer(
        String supermarketCode,
        String en,
        @JsonProperty("zh-Hant") String zhHant,
        @JsonProperty("zh-Hans") String zhHans
) {}
