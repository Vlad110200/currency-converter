package com.project.currency_converter.dto;

import com.project.currency_converter.entity.User;

import java.util.List;

public class UserMapper {

    public static UserResponseDto toDto (User user) {
        List<HistoryResponseDto> historyResponseDto = user.getHistory().stream()
                .map(history -> new HistoryResponseDto(
                        history.getId(),
                        history.getFromCurrency(),
                        history.getToCurrency(),
                        history.getAmount(),
                        history.getConvertedAmount(),
                        history.getCreatedTimestamp()
                )).toList();
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getApiKey(),
                historyResponseDto
        );
    }
}
