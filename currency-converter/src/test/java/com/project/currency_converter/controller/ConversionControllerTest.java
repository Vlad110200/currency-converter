package com.project.currency_converter.controller;

import com.project.currency_converter.dto.ConversionRequestDto;
import com.project.currency_converter.dto.ConversionResponseDto;
import com.project.currency_converter.service.ConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConversionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConversionService conversionService;

    @MockitoBean
    private com.project.currency_converter.repository.UserRepository userRepository;

    private ConversionRequestDto requestDto;
    private ConversionResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new ConversionRequestDto("USD",
                "EUR",
                BigDecimal.valueOf(100));
        responseDto = new ConversionResponseDto("USD",
                "EUR",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(85),
                BigDecimal.valueOf(0.85));
    }

    @Test
    void convertCurrency_WithValidData_ShouldReturnConversionResult() throws Exception {
        String testApiKey = "test-api-key-123";

        Mockito.when(conversionService.convert(any(ConversionRequestDto.class), eq(testApiKey)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/convert")
                .header("X-API-KEY", testApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency").value("USD"))
                .andExpect(jsonPath("$.convertedAmount").value(85));
    }

    @Test
    void convertCurrency_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        String testApiKey = "test-api-key-123";

        ConversionRequestDto invalidRequest = new ConversionRequestDto("USD",
                "EUR",
                BigDecimal.valueOf(-50));

        mockMvc.perform(post("/api/convert")
                        .header("X-API-KEY", testApiKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                        .andExpect(status().isBadRequest());
    }
}
