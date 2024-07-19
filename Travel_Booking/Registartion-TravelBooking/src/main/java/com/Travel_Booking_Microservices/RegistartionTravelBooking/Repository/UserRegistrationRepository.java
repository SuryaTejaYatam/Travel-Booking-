package com.Travel_Booking_Microservices.RegistartionTravelBooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.UserRegistration;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Long>{

    UserRegistration findByEmail(String email);
    
}
