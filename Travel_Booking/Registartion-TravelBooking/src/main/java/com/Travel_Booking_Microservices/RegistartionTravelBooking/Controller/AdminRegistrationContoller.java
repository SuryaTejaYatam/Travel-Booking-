package com.Travel_Booking_Microservices.RegistartionTravelBooking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.AdminRegistration;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Service.AdminService.AdminService;


import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/registartion")
@Slf4j
public class AdminRegistrationContoller {
    
    
    @Autowired
    private AdminService adminService;

    @PostMapping("/adminRegistration")
    public AdminRegistration adminRegistration(@RequestBody AdminRegistration adminRegistration) {

        log.info("Sending to the service layer");
        return adminService.adminRegistration(adminRegistration);
    }

    @GetMapping("/adminLogin/{email}/{password}")
    public String  adminLogin(@PathVariable String email, @PathVariable String password) {

        log.info("Sending to the service layer");
        AdminRegistration admin = adminService.adminLogin(email, password);

        if (admin != null) {

            return "LOGIN_SUCCESS";
        }
        return "LOGIN_FAILED";
    }
}
