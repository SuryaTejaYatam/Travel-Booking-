package com.Travel_Booking_Microservices.LoyaltyService.Service;

import com.Travel_Booking_Microservices.LoyaltyService.Entity.UserLoyalty;

public interface LoyaltyService {

    UserLoyalty getUserLoyaltyDetails(Long userId);

    UserLoyalty updateUserLoyalty(Long userId, int points);

    UserLoyalty redeemPoints(Long userId, int points);

    String getPointsHistory(Long userId);

    UserLoyalty updateTier(Long userId, String tier);

    void addLoyalty(UserLoyalty userLoyalty);

    
}
