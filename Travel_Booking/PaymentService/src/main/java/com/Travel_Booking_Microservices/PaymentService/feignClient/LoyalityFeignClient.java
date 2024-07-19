package com.Travel_Booking_Microservices.PaymentService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.PaymentService.Model.UserLoyaltyModel;

@FeignClient(name = "LoyalityServiceFeignClient", url = "http://localhost:8086/loyalty")
public interface LoyalityFeignClient {

    @GetMapping("/getUserLoyalty")
    public UserLoyaltyModel getUserLoyalty(@RequestParam("userId") Long userId);

    @PostMapping("/update")
    public UserLoyaltyModel addPoints(@RequestParam("userId") Long userId, @RequestParam("points") int points);

    @PutMapping("/tier/update")
    public UserLoyaltyModel updateTier(@RequestParam("userId") Long userId, @RequestParam("tier") String tier);
    @PostMapping("/redeem")
    public UserLoyaltyModel redeemPoints(@RequestParam Long userId, @RequestParam int points);
}

