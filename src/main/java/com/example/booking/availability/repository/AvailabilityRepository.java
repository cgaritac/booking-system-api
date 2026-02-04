package com.example.booking.availability.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking.availability.entity.AvailabilityEntity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, UUID> {
    List<AvailabilityEntity> findByStaffIdAndDayOfWeek(UUID staffId, DayOfWeek dayOfWeek);
}