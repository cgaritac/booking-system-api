package com.example.booking.servicebooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
}
