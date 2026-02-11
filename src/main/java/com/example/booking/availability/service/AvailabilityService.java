package com.example.booking.availability.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;
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

    @NonNull
    public List<AvailabilityEntity> getAllAvailabilities() {
        return Objects.requireNonNull(availabilityRepository.findAll());
    }

    @Transactional
    @NonNull
    public AvailabilityEntity createAvailability(@NonNull CreateAvailabilityRequest request) {
        AvailabilityEntity availability = new AvailabilityEntity();
        availability.setStaffId(request.staffId());
        availability.setDayOfWeek(request.dayOfWeek());
        availability.setStartTime(request.startTime());
        availability.setEndTime(request.endTime());

        return Objects.requireNonNull(availabilityRepository.save(availability));
    }

    @Transactional
    public void deleteAvailability(@NonNull UUID id) {
        AvailabilityEntity availability = Objects.requireNonNull(availabilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Availability not found with id: " + id)));
        availabilityRepository.delete(availability);
    }
}
