package com.Travel_Booking_Microservices.HotelBookingService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.HotelBookingService.Entity.Hotels;

@Repository
public interface HotelRepository extends JpaRepository<Hotels,Long>{

    List<Hotels> findByLocation(String location);

    List<Hotels> findByHotelName(String hotelName);

    Hotels findByHotelId(Long hotelId);

    Hotels findByHotelNameAndLocation(String hotelName, String location);

    

    
}
