package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Loyality;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoyaltyModel {

    private Long userId;
    private int points;
    private String tier; // Added to manage loyalty tiers (e.g., Gold, Silver, Bronze)
    private int lifetimePoints; // Track total points a user has earned

    private LocalDate lastUpdate;
}
