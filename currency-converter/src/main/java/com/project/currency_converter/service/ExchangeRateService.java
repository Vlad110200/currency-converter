package com.project.currency_converter.service;

import com.project.currency_converter.dto.ExternalResponseDto;
import com.project.currency_converter.exception.CurrencyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class ExchangeRateService {

    private final RestClient restClient;
    private final String externalApiKey;

    public ExchangeRateService(RestClient.Builder restClientBuilder,
                               @Value("${app.external-api-key}") String externalApiKey) {
        this.externalApiKey = externalApiKey;
        this.restClient = restClientBuilder
                .baseUrl("https://v6.exchangerate-api.com/v6/")
                .build();
    }

    @Cacheable(value = "rates",key = "#fromCurrency + '-' + #toCurrency")
    public double getRate (String fromCurrency, String toCurrency) {
        ExternalResponseDto response = restClient.get()
                .uri(externalApiKey + "/latest/" + fromCurrency)
                .retrieve()
                .body(ExternalResponseDto.class);

        if (response != null && response.conversion_rates().containsKey(toCurrency)) {
            log.info("Get rate from exchangeRateService for fromCurrency:{}, toCurrency:{}", fromCurrency, toCurrency);
            return response.conversion_rates().get(toCurrency);
        } else {
            throw new CurrencyNotFoundException(toCurrency);
        }

    }

}
