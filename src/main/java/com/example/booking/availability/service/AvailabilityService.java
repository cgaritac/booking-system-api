package com.example.booking.availability.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.booking.availability.dto.CreateAvailabilityRequest;
import com.example.booking.availability.entity.AvailabilityEntity;
import com.example.booking.availability.repository.AvailabilityRepository;

import jakarta.transaction.Transactional;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public List<AvailabilityEntity> getAllAvailabilities() {
        return availabilityRepository.findAll();
    }

    @Transactional
    public AvailabilityEntity createAvailability(CreateAvailabilityRequest request) {
        AvailabilityEntity availability = new AvailabilityEntity();
        availability.setStaffId(request.staffId());
        availability.setDayOfWeek(request.dayOfWeek());
        availability.setStartTime(request.startTime());
        availability.setEndTime(request.endTime());

        return availabilityRepository.save(availability);
    }
}
