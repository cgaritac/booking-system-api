package com.example.booking.reservation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking.reservation.entity.ReservationStatus;

public record ReservationResponse(
    UUID id,
    UUID clientId,
    UUID staffId,
    UUID serviceId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    ReservationStatus status) {
}
