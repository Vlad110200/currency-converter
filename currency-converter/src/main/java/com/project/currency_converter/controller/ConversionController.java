package com.project.currency_converter.controller;

import com.project.currency_converter.dto.ConversionRequestDto;
import com.project.currency_converter.dto.ConversionResponseDto;
import com.project.currency_converter.service.ConversionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/convert")
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService conversionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ConversionResponseDto convertCurrency (
            @RequestHeader("X-API-KEY") String apiKey,
            @Valid @RequestBody ConversionRequestDto request
            ) {
        return conversionService.convert(request, apiKey);
    }
}
