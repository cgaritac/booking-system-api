package com.example.booking.reservation.dto;

import com.example.booking.reservation.entity.ReservationEntity;

public class ReservationMapper {
    public static ReservationResponse toResponse(ReservationEntity entity){
        return new ReservationResponse(
            entity.getId(),
            entity.getClientId(),
            entity.getStaffId(),
            entity.getServiceId(),
            entity.getStartTime(),
            entity.getEndTime(),
            entity.getStatus()
        );
    }
}
