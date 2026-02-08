package com.example.booking.availability.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.booking.availability.entity.AvailabilityEntity;
import com.example.booking.availability.repository.AvailabilityRepository;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public List<AvailabilityEntity> getAllAvailabilities() {
        return availabilityRepository.findAll();
    }
}
