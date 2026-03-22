package com.project.currency_converter.dto;

import java.math.BigDecimal;

public record ConversionResponseDto (
        String fromCurrency,
        String toCurrency,
        BigDecimal amount,
        BigDecimal convertedAmount,
        BigDecimal rate
) {
}
