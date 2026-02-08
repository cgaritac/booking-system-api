package com.example.booking.servicebooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.booking.servicebooking.entity.ServiceEntity;
import com.example.booking.servicebooking.repository.ServiceRepository;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findByActiveTrue();
    }
}
