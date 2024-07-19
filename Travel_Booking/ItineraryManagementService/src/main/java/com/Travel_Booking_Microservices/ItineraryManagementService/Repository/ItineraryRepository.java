package com.Travel_Booking_Microservices.ItineraryManagementService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.ItineraryManagementService.Entity.Itinerary;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary,Long>{

    Itinerary findByUserId(Long userId);

    Itinerary findByUserIdAndFlightBookingId(Long userId, Long flightBookingId);

    Itinerary findByFlightBookingId(Long flightBookingId);

    Itinerary findByUserIdAndReservationId(Long userId, Long reservationId);

    Itinerary findByStartingPlaceAndEndingPlace(String startingPlace, String endingPlace);

    Itinerary findByReservationId(Long reservationId);

    Itinerary findByUserIdAndCarId(Long userId, Long carId);

    Itinerary findByStartingPlaceAndEndingPlaceAndUserId(String startingPlace, String endingPlace, Long userId);

    Itinerary findByUserIdAndCarBookingId(Long userId, Long carBookingId);

    Itinerary findByItineraryId(Long itineraryId);
    
}
