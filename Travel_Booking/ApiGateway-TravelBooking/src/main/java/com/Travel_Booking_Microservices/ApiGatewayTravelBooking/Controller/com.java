package com.Travel_Booking_Microservices.ApiGatewayTravelBooking.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class com {

    @GetMapping("/get")
    public String con()
    {
        return "HEllo";
    }
    
}
