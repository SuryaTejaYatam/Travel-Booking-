package com.Travel_Booking_Microservices.RegistartionTravelBooking.Service.UserService;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.UserRegistration;

public interface UserService {

    UserRegistration userRegistration(UserRegistration userRegistration);

    UserRegistration userLogin(String email, String password);
    
}
