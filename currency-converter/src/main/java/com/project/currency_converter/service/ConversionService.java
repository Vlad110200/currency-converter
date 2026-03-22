package com.project.currency_converter.service;

import com.project.currency_converter.dto.ConversionRequestDto;
import com.project.currency_converter.dto.ConversionResponseDto;
import com.project.currency_converter.entity.History;
import com.project.currency_converter.entity.User;
import com.project.currency_converter.exception.UserNotFoundException;
import com.project.currency_converter.repository.ConversionHistoryRepository;
import com.project.currency_converter.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionService {

    private final UserRepository userRepository;
    private final ExchangeRateService exchangeRateService;
    private final ConversionHistoryRepository historyRepository;


    @Transactional
    public ConversionResponseDto convert (ConversionRequestDto request, String apiKey) {

        User user = userRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new UserNotFoundException(apiKey));

        double rateDouble = exchangeRateService
                .getRate(request.fromCurrency(), request.toCurrency());

        BigDecimal rate = BigDecimal.valueOf(rateDouble);
        BigDecimal convertedAmount = request.amount().multiply(rate);

        History history = new History();
        history.setUser(user);
        history.setFromCurrency(request.fromCurrency());
        history.setToCurrency(request.toCurrency());
        history.setAmount(request.amount());
        history.setConvertedAmount(convertedAmount);
        history.setCreatedTimestamp(LocalDateTime.now());

        historyRepository.save(history);

        log.info("Successfully convert fromCurrency:{} to toCurrency:{}, amount:{}",
                request.fromCurrency(),request.toCurrency(),request.amount());
        return new ConversionResponseDto(
                request.fromCurrency(),
                request.toCurrency(),
                request.amount(),
                convertedAmount,
                rate
        );
    }
}
