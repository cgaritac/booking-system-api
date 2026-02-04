package com.example.booking.reservation.entity;

import com.example.booking.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "reservations")
public class ReservationEntity extends BaseEntity {
    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private UUID staffId;

    @Column(nullable = false)
    private UUID serviceId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;
    
}
