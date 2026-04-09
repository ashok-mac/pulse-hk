package org.dev.pulsehk.model;

import java.util.List;

public record Product(
        String code,
        LocalizedText brand,
        LocalizedText name,
        LocalizedText cat1Name,
        LocalizedText cat2Name,
        LocalizedText cat3Name,
        List<Price> prices,
        List<Offer> offers
) {}

