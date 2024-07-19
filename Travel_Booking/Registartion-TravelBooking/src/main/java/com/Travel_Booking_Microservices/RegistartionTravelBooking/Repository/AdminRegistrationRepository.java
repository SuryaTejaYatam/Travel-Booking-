package com.Travel_Booking_Microservices.RegistartionTravelBooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.AdminRegistration;

public interface AdminRegistrationRepository extends JpaRepository<AdminRegistration,Long> {

    AdminRegistration findByEmail(String email);

    
    
}
