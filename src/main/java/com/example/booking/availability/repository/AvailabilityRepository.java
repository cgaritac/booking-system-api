package com.example.booking.availability.repository;

import com.example.booking.availability.entty.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, UUID> {
    List<AvailabilityEntity> findByStaffIdAndDayOfWeek(UUID staffId, DayOfWeek dayOfWeek);
}