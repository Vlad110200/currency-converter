package com.project.currency_converter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ConversionRequestDto(

        @NotBlank
        @Size(min = 3, max = 3, message = "The parameter must consist of exactly 3 characters.")
        String fromCurrency,

        @NotBlank
        @Size(min = 3, max = 3, message = "The parameter must consist of exactly 3 characters.")
        String toCurrency,

        @Positive(message = "Conversion amount must be positive number")
        BigDecimal amount
) {
}
