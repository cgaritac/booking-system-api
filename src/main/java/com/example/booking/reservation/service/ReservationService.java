package com.example.booking.reservation.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.booking.availability.entity.AvailabilityEntity;
import com.example.booking.availability.repository.AvailabilityRepository;
import com.example.booking.servicebooking.repository.ServiceRepository;
import com.example.booking.reservation.entity.ReservationEntity;
import com.example.booking.reservation.entity.ReservationStatus;
import com.example.booking.reservation.repository.ReservationRepository;
import com.example.booking.servicebooking.entity.ServiceEntity;
import jakarta.transaction.Transactional;

import java.util.UUID;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            AvailabilityRepository availabilityRepository,
            ServiceRepository serviceRepository) {
        this.reservationRepository = reservationRepository;
        this.availabilityRepository = availabilityRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public ReservationEntity createReservation(
            @NonNull UUID clientId,
            @NonNull UUID staffId,
            @NonNull UUID serviceId,
            @NonNull LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation cannot be in the past");
        }

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        LocalDateTime endTime = startTime.plusMinutes(service.getDurationMinutes());

        validateAvailability(staffId, startTime, endTime);

        validateCollision(staffId, startTime, endTime);

        ReservationEntity reservation = new ReservationEntity();
        reservation.setClientId(clientId);
        reservation.setStaffId(staffId);
        reservation.setServiceId(serviceId);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        return reservationRepository.save(reservation);
    }

    private void validateAvailability(UUID staffId, LocalDateTime start, LocalDateTime end) {
        DayOfWeek dayOfWeek = start.getDayOfWeek();

        List<AvailabilityEntity> availabilities = availabilityRepository.findByStaffIdAndDayOfWeek(staffId, dayOfWeek);

        boolean available = availabilities.stream().anyMatch(a -> !start.toLocalTime().isBefore(a.getStartTime()) &&
                !end.toLocalTime().isAfter(a.getEndTime()));

        if (!available) {
            throw new IllegalArgumentException("Staff not available at this time");
        }
    }

    private void validateCollision(UUID staffId, LocalDateTime start, LocalDateTime end) {
        List<ReservationEntity> collisions = reservationRepository
                .findByStaffIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
                        staffId,
                        List.of(
                                ReservationStatus.CONFIRMED,
                                ReservationStatus.PENDING),
                        end,
                        start);

        if (!collisions.isEmpty()) {
            throw new IllegalArgumentException("Reservation time slot is already taken");
        }
    }
}
