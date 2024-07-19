package com.Travel_Booking_Microservices.LoyaltyService.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Travel_Booking_Microservices.LoyaltyService.Entity.UserLoyalty;
import com.Travel_Booking_Microservices.LoyaltyService.Exception.LoyalityCustomException;
import com.Travel_Booking_Microservices.LoyaltyService.Repository.LoyaltyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoyaltyServiceImp implements LoyaltyService {

    @Autowired
    private LoyaltyRepository loyaltyRepository;

    public UserLoyalty getUserLoyaltyDetails(Long userId) {

        log.info("Findung the Points by userId");
        return loyaltyRepository.findByUserId(userId)
                .orElseThrow(() -> new LoyalityCustomException("INVALID_USERID", "User not found"));
    }

    public UserLoyalty updateUserLoyalty(Long userId, int points) {

        LocalDate date = LocalDate.now();

        log.info("Findung the Points by userId");
        UserLoyalty loyalty = getUserLoyaltyDetails(userId);
        int currentPoints = loyalty.getPoints() + points;
        loyalty.setPoints(currentPoints);

        loyalty.setLifetimePoints(loyalty.getLifetimePoints() + points);

        loyalty.setLastUpdate(date);

        log.info("saving the deatils...");
        return loyaltyRepository.save(loyalty);
    }

    public UserLoyalty redeemPoints(Long userId, int points) {

        LocalDate date = LocalDate.now();

        log.info("Findung the Points by userId");
        UserLoyalty loyalty = getUserLoyaltyDetails(userId);

        log.info("if condition in the redeem points method");
        if (loyalty.getPoints() >= points) {
            loyalty.setPoints(loyalty.getPoints() - points);

            loyalty.setLastUpdate(date);
            log.info("saving details redeem points method");
            return loyaltyRepository.save(loyalty);
        } else {
            throw new LoyalityCustomException("LESS_POINTS", "Not enough points");
        }
    }

    public UserLoyalty updateTier(Long userId, String tier) {

        log.info("Finding the Points by userId");
        UserLoyalty loyalty = getUserLoyaltyDetails(userId);
        loyalty.setTier(tier);

        log.info("saving details");
        return loyaltyRepository.save(loyalty);
    }

    public String getPointsHistory(Long userId) {

        log.info("Finding the Points by userId");
        UserLoyalty loyalty = getUserLoyaltyDetails(userId);

        log.info("user Id" + userId);
        return "Total points: " + loyalty.getLifetimePoints() +
                ", Current points: " + loyalty.getPoints() +
                ", Tier: " + loyalty.getTier();
    }

    @Override
    public void addLoyalty(UserLoyalty userLoyalty) {

        log.info("Saving the details");
        loyaltyRepository.save(userLoyalty);

    }

}
