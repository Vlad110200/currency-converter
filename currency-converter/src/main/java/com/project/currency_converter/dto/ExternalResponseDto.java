package com.project.currency_converter.dto;

import java.util.Map;

public record ExternalResponseDto (
        String base_code,
        Map<String, Double> conversion_rates
) {
}
