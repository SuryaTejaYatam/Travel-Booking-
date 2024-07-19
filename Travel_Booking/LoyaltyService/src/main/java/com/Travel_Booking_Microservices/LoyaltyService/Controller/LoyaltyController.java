package com.Travel_Booking_Microservices.LoyaltyService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Travel_Booking_Microservices.LoyaltyService.Entity.UserLoyalty;
import com.Travel_Booking_Microservices.LoyaltyService.Service.LoyaltyService;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyController {

    @Autowired
    private LoyaltyService loyaltyService;

    @GetMapping("/getUserLoyalty")
    public UserLoyalty getUserLoyalty(@RequestParam Long userId) {
        return loyaltyService.getUserLoyaltyDetails(userId);
    }

    @PostMapping("/update")
    public UserLoyalty addPoints(@RequestParam Long userId, @RequestParam int points) {
        return loyaltyService.updateUserLoyalty(userId, points);
    }

    @PostMapping("/redeem")
    public UserLoyalty redeemPoints(@RequestParam Long userId, @RequestParam int points) {
        return loyaltyService.redeemPoints(userId, points);
    }



    @GetMapping("/history")
    public String getPointHistory(@RequestParam Long userId) {
        return loyaltyService.getPointsHistory(userId);
    }

    @PutMapping("/tier/update")
    public UserLoyalty updateTier(@RequestParam Long userId, @RequestParam String tier) {
        return loyaltyService.updateTier(userId, tier);
    }

    @PostMapping("/addLoyality")
    public void addLoyality(@RequestBody UserLoyalty userLoyalty) {
         loyaltyService.addLoyalty(userLoyalty);
    }
    
}
