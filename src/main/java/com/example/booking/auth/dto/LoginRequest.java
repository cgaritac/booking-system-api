package com.example.booking.auth.dto;

public record LoginRequest(
    String email,
    String password
) {
}
