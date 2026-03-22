package com.project.currency_converter.service;


import com.project.currency_converter.exception.CurrencyNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ExchangeRateService.class)
@TestPropertySource(properties = "app.external-api-key=test-api-key")
public class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void getRate_ShouldReturnRate_WhenCurrencyExists () {
        String expectedJsonResponse = """
                {
                    "result": "success",
                    "base_code": "USD",
                    "conversion_rates": {
                        "EUR": 0.85,
                        "UAH": 38.5
                    }
                }
                """;
        mockServer.expect(requestTo("https://v6.exchangerate-api.com/v6/test-api-key/latest/USD"))
                .andRespond(withSuccess(expectedJsonResponse, MediaType.APPLICATION_JSON));

        double rate = exchangeRateService.getRate("USD", "UAH");

        assertEquals(38.5, rate);
        mockServer.verify();
    }

    @Test
    void getRate_ShouldThrowException_WhenCurrencyDoesNotExist() {
        String expecterJsonResponse = """
                {
                    "result": "success",
                    "base_code": "USD",
                    "conversion_rates": {
                        "EUR": 0.85
                    }
                }
                """;
        mockServer.expect(requestTo("https://v6.exchangerate-api.com/v6/test-api-key/latest/USD"))
                .andRespond(withSuccess(expecterJsonResponse, MediaType.APPLICATION_JSON));

        assertThrows(CurrencyNotFoundException.class, () -> exchangeRateService.getRate("USD", "XYZ"));

        mockServer.verify();
    }


}
