package com.example.booking.servicebooking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking.servicebooking.dto.CreateServiceRequest;
import com.example.booking.servicebooking.dto.ServiceMapper;
import com.example.booking.servicebooking.dto.ServiceResponse;
import com.example.booking.servicebooking.entity.ServiceEntity;
import com.example.booking.servicebooking.service.ServiceService;

import org.springframework.lang.NonNull;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Operation(summary = "Create a new service")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse createService(@RequestBody @Valid CreateServiceRequest request) {
        ServiceEntity service = serviceService.createServiceEntity(request);

        return ServiceMapper.toResponse(service);

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

    @Operation(summary = "Delete a service")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteService(@RequestParam @NonNull UUID id) {
        serviceService.deleteServiceEntity(id);
    }
}
