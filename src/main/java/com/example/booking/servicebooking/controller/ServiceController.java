package com.example.booking.servicebooking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking.servicebooking.dto.ServiceMapper;
import com.example.booking.servicebooking.dto.ServiceResponse;
import com.example.booking.servicebooking.service.ServiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Operation(summary = "Get all services")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Services retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceResponse> getAllServices() {
        return serviceService.getAllServices().stream()
                .map(ServiceMapper::toResponse)
                .toList();
    }
}
