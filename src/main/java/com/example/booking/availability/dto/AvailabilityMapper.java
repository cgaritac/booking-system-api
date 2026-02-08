package com.example.booking.availability.dto;

import com.example.booking.availability.entity.AvailabilityEntity;

public class AvailabilityMapper {

    public static AvailabiityResponse toResponse(AvailabilityEntity entity) {
        return new AvailabiityResponse(
                entity.getId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime());
    }
}
