package com.Travel_Booking_Microservices.FlightBookingService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.FlightBookingService.Entity.UserFlightBookingDetails;

@Repository
public interface UserFlightBookingDetailsRepository extends JpaRepository<UserFlightBookingDetails,Long>{

    List<UserFlightBookingDetails> findByUserId(Long userId);

    UserFlightBookingDetails findByFlightBookingId(Long flightBookingId);

    UserFlightBookingDetails findByFlightNumberAndUserId(int flightNumber, Long userId);
    
}
