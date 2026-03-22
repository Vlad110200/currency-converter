package com.project.currency_converter.exception;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException (String toCurrency) {
        super ("Currency: " + toCurrency + " not found");
    }
}
