package com.example.booking.servicebooking.dto;

import com.example.booking.servicebooking.entity.ServiceEntity;

public class ServiceMapper {
    public static ServiceResponse toResponse(ServiceEntity entity) {
        return new ServiceResponse(
                entity.getId(),
                entity.getName(),
                entity.getDurationMinutes(),
                entity.getPrice());
    }
}
