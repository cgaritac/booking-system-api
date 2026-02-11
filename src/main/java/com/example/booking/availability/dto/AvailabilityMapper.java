package com.example.booking.availability.dto;

import org.springframework.lang.NonNull;

import com.example.booking.availability.entity.AvailabilityEntity;

public class AvailabilityMapper {

    @NonNull
    public static AvailabilityResponse toResponse(@NonNull AvailabilityEntity entity) {
        return new AvailabilityResponse(
                entity.getId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime());
    }
}
