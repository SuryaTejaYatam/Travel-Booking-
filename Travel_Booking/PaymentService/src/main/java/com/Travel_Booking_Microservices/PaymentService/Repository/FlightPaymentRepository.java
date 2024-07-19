package com.Travel_Booking_Microservices.PaymentService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.PaymentService.Entity.FlightPayment;



@Repository
public interface FlightPaymentRepository extends JpaRepository<FlightPayment, Long> {
    

}
