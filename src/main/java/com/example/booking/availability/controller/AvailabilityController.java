package com.example.booking.availability.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.example.booking.availability.dto.AvailabilityMapper;
import com.example.booking.availability.dto.CreateAvailabilityRequest;
import com.example.booking.availability.dto.AvailabilityResponse;
import com.example.booking.availability.entity.AvailabilityEntity;
import com.example.booking.availability.service.AvailabilityService;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Operation(summary = "Create a new availability")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @NonNull
    public AvailabilityResponse createAvailability(@RequestBody @Valid @NonNull CreateAvailabilityRequest request) {
        AvailabilityEntity availability = availabilityService.createAvailability(request);

        return AvailabilityMapper.toResponse(availability);
    }

    @Operation(summary = "Get all availabilities")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availabilities retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @NonNull
    public List<AvailabilityResponse> getAllAvailabilities() {
        return Objects.requireNonNull(availabilityService.getAllAvailabilities().stream()
                .map(AvailabilityMapper::toResponse)
                .toList());
    }

    @Operation(summary = "Delete an availability")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAvailability(@RequestParam @NonNull UUID id) {
        availabilityService.deleteAvailability(id);
    }
}
