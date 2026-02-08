package com.example.booking.servicebooking.dto;

import io.micrometer.common.lang.NonNull;

public record CreateServiceRequest(
        @NonNull String name,
        @NonNull int durationMinutes,
        @NonNull double price) {
}