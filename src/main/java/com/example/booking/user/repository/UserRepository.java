package com.example.booking.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.booking.user.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
