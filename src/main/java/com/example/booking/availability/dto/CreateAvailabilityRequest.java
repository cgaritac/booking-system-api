package com.example.booking.availability.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateAvailabilityRequest(
        @NotNull UUID staffId,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotNull DayOfWeek dayOfWeek) {
}
