package com.example.booking.servicebooking.repository;

import com.example.booking.servicebooking.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    List<ServiceEntity> findByActiveTrue();
}