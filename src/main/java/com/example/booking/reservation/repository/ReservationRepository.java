package com.example.booking.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.booking.reservation.entity.ReservationEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByStaffIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
        UUID staffId,
        List<String> statuses,
        LocalDateTime end,
        LocalDateTime start
    );
}
