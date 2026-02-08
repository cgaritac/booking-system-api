package com.example.booking.servicebooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateServiceRequest(
        @NotBlank String name,
        @NotNull @Positive int durationMinutes,
        @NotNull @Positive double price) {
}