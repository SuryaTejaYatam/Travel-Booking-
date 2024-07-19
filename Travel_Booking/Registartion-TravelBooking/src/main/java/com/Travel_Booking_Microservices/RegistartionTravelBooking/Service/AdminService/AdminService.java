package com.Travel_Booking_Microservices.RegistartionTravelBooking.Service.AdminService;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.AdminRegistration;

public interface AdminService {

    AdminRegistration adminRegistration(AdminRegistration adminRegistration);

    AdminRegistration adminLogin(String email, String password);
    
}
