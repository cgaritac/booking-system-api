package com.example.booking.availability.entity;

import com.example.booking.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "availabilities")
public class AvailabilityEntity extends BaseEntity {
    @Column(nullable = false)
    private UUID staffId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
}