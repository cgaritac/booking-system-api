package com.example.booking.reservation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.booking.availability.entity.AvailabilityEntity;
import com.example.booking.availability.repository.AvailabilityRepository;
import com.example.booking.reservation.entity.ReservationEntity;
import com.example.booking.reservation.entity.ReservationStatus;
import com.example.booking.reservation.repository.ReservationRepository;
import com.example.booking.reservation.service.ReservationService;
import com.example.booking.servicebooking.entity.ServiceEntity;
import com.example.booking.servicebooking.repository.ServiceRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldThrowExceptionWhenReservationOverLaps() {
        UUID clientId = UUID.randomUUID();
        UUID staffId = UUID.randomUUID();
        UUID serviceId = UUID.randomUUID();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(2);

        ServiceEntity service = mock(ServiceEntity.class);
        when(service.getDurationMinutes()).thenReturn(120);
        when(serviceRepository.findById(Objects.requireNonNull(serviceId))).thenReturn(Optional.of(service));

        AvailabilityEntity availability = mock(AvailabilityEntity.class);
        when(availability.getStartTime()).thenReturn(LocalTime.MIN);
        when(availability.getEndTime()).thenReturn(LocalTime.MAX);
        when(availabilityRepository.findByStaffIdAndDayOfWeek(Objects.requireNonNull(staffId),
                startTime.getDayOfWeek()))
                .thenReturn(List.of(availability));

        ReservationEntity existingReservation = mock(ReservationEntity.class);
        when(reservationRepository.findByStaffIdAndStatusInAndStartTimeLessThanAndEndTimeGreaterThan(
                eq(staffId),
                eq(List.of(
                        ReservationStatus.CONFIRMED,
                        ReservationStatus.PENDING)),
                eq(endTime),
                eq(startTime))).thenReturn(List.of(existingReservation));

        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(
                        Objects.requireNonNull(clientId),
                        Objects.requireNonNull(staffId),
                        Objects.requireNonNull(serviceId),
                        Objects.requireNonNull(startTime)));
    }
}
