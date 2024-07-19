package com.Travel_Booking_Microservices.RegistartionTravelBooking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.UserRegistration;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Service.UserService.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/registartion")
@Slf4j
public class UserRegistrartionController {

    @Autowired
    private UserService userService;

    @PostMapping("/userRegistration")
    public UserRegistration userRegistration(@RequestBody UserRegistration userRegistration) {

        log.info("Sending to the service layer");
        return userService.userRegistration(userRegistration);
    }

    @GetMapping("/userLogin/{email}/{password}")
    public UserRegistration userLogin(@PathVariable String email, @PathVariable String password) {

        log.info("Sending to the service layer");
        UserRegistration user = userService.userLogin(email, password);

        if (user != null) {

            return user;
        }
        return null;
    }
}
