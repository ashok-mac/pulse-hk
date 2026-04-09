package org.dev.pulsehk.service;

import org.dev.pulsehk.model.Product;
import org.dev.pulsehk.model.ProductOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferService.class);

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://online-price-watch.consumer.org.hk/opw/opendata/pricewatch.json")
            .build();;

    public Map<String, List<String>> getOfferDetails(String brandName) {
        List<Product> apiResponse = restClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        assert apiResponse != null;
        Map<String, List<String>> brandOfferResponse = apiResponse.stream()
                .filter(p -> p.brand().en().contains(brandName))
                .flatMap(p -> p.offers().stream()
                        .map(o -> new ProductOffer(p.name().en(), o)))
                .collect(Collectors.groupingBy(
                        po -> po.offer().supermarketCode(),
                        Collectors.mapping(
                                po -> po.productName() + ": " + po.offer().en(),
                                Collectors.toList()
                        )
                ));

        log.info("Response from PriceWatcher for the brand is {}" , brandOfferResponse);
        return brandOfferResponse;
    }
}
