package com.example.booking.servicebooking.entity;

import com.example.booking.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "services")
public class ServiceEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean active = true;
}