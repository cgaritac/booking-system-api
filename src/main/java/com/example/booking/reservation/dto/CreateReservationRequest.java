package com.example.booking.reservation.dto;

import com.example.booking.common.jackson.IsoOffsetLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

public record CreateReservationRequest(
    @NonNull UUID clientId,
    @NonNull UUID staffId,
    @NonNull UUID serviceId,

    @NonNull
    @Future
    @JsonDeserialize(using = IsoOffsetLocalDateTimeDeserializer.class)
    LocalDateTime startTime
) {
}
