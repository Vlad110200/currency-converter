package com.project.currency_converter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryResponseDto (
        Long id,
        String fromCurrency,
        String toCurrency,
        BigDecimal amount,
        BigDecimal convertedAmount,
        LocalDateTime createdTimestamp
) {
}
