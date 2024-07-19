package com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.Travel_Booking_Microservices.ItineraryManagementService.Model.UserRegistrationModel;


@FeignClient(name = "userLoginClient", url = "http://localhost:8080/registartion")
public interface UserRegistrationFeignClient {

    @PostMapping("/userRegistration")
    public UserRegistrationModel userRegistration(@RequestBody UserRegistrationModel userRegistration);

    @GetMapping("/userLogin/{email}/{password}")
    public UserRegistrationModel userLogin(@PathVariable String email, @PathVariable String password);




}
