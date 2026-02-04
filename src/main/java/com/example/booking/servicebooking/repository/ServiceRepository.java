package com.example.booking.servicebooking.repository;

import com.example.booking.servicebooking.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
}