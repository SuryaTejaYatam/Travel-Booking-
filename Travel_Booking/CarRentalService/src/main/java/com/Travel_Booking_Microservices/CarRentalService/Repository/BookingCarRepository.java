package com.Travel_Booking_Microservices.CarRentalService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.CarRentalService.Entity.BookingCar;

@Repository
public interface BookingCarRepository extends JpaRepository<BookingCar,Long>{

    BookingCar findByCarBookingId(Long bookingId);
    
}
