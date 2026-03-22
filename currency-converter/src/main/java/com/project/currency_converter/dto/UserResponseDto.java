package com.project.currency_converter.dto;

import java.util.List;

public record UserResponseDto (
        Long id,
        String username,
        String apiKey,
        List<HistoryResponseDto> historyList
) {
}
