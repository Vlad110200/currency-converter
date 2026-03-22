package com.project.currency_converter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    @CacheEvict(value = "rates",allEntries = true)
    @Scheduled(fixedRate = 360000)
    public void clearRatesCache () {
        log.info("Clear cache for ExchangeRateService, getRate method");
    }
}
