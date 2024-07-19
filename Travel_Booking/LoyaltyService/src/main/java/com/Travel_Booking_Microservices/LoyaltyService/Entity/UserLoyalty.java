package com.Travel_Booking_Microservices.LoyaltyService.Entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoyalty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private int points;
    private String tier; // Added to manage loyalty tiers (e.g., Gold, Silver, Bronze)
    private int lifetimePoints; // Track total points a user has earned

    private LocalDate lastUpdate;
}
