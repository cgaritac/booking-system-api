package com.example.booking.servicebooking.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    private UUID id;
    private String name;
    private int durationMinutes;
    private double price;
}
