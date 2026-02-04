package com.example.booking.reservation.dto;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

public record CreateReservationRequest(
    @NonNull UUID cliendId,
    @NonNull UUID staffId,
    @NonNull UUID serviceId,
    
    @NonNull
    @Future
    LocalDateTime startTime
) {
}
