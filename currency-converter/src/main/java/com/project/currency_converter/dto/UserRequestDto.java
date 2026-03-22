package com.project.currency_converter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotBlank(message = "Username cannot be blank")
        @Size(min = 1, max = 50, message = "Username must contain from 1 to 50 characters")
        String username
) {
}
