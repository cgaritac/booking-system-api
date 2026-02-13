package com.example.booking.servicebooking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import com.example.booking.servicebooking.dto.CreateServiceRequest;
import com.example.booking.servicebooking.entity.ServiceEntity;
import com.example.booking.servicebooking.repository.ServiceRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceEntity> getAllServices() {
        List<ServiceEntity> services = serviceRepository.findByActiveTrue();

        return services;
    }

    @Transactional
    public ServiceEntity createServiceEntity(CreateServiceRequest request) {
        ServiceEntity service = new ServiceEntity();
        service.setName(request.name());
        service.setDurationMinutes(request.durationMinutes());
        service.setPrice(request.price());

        return serviceRepository.save(service);
    }

    @Transactional
    public void deleteServiceEntity(@NonNull UUID id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        if (!service.isActive()) {
            throw new IllegalArgumentException("Service is already deleted");
        }

        service.setActive(false);
        serviceRepository.save(service);
    }
}
