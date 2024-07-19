package com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Loyality.UserLoyaltyModel;

@FeignClient(name = "Loyality-Service-FeignClient", url = "http://localhost:8086/loyalty")
public interface LoyalityFeignClient {
    @GetMapping("/getUserLoyalty")
    public UserLoyaltyModel getUserLoyalty(@RequestParam Long userId);

    @PostMapping("/redeem")
    public UserLoyaltyModel redeemPoints(@RequestParam Long userId, @RequestParam int points);

    @GetMapping("/history")
    public String getPointHistory(@RequestParam Long userId);
}
