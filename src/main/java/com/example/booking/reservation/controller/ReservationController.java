package com.example.booking.reservation.controller;

import com.example.booking.reservation.dto.*;
import com.example.booking.reservation.entity.ReservationEntity;
import com.example.booking.reservation.service.ReservationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@RequestBody @Valid CreateReservationRequest request) {
        ReservationEntity reservation = reservationService.createReservation(
                request.clientId(),
                request.staffId(),
                request.serviceId(),
                request.startTime());

        return ReservationMapper.toResponse(reservation);
    }

    @Operation(summary = "Get all reservations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservations retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations().stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }
}
