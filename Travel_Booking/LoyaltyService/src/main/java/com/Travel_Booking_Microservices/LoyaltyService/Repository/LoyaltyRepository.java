package com.Travel_Booking_Microservices.LoyaltyService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.LoyaltyService.Entity.UserLoyalty;


@Repository
public interface LoyaltyRepository extends JpaRepository<UserLoyalty, Long> {
    Optional<UserLoyalty> findByUserId(Long userId);
}