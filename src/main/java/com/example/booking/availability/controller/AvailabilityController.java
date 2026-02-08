package com.example.booking.availability.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.example.booking.availability.dto.AvailabilityMapper;
import com.example.booking.availability.dto.AvailabiityResponse;
import com.example.booking.availability.service.AvailabilityService;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Operation(summary = "Get all availabilities")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availabilities retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvailabiityResponse> getAllAvailabilities() {
        return availabilityService.getAllAvailabilities().stream()
                .map(AvailabilityMapper::toResponse)
                .toList();
    }
}
