package com.Travel_Booking_Microservices.FlightBookingService.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.FlightBookingService.Entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

        Flight findByFlightNumber(int flightNumber);

        List<Flight> findByOriginAndDestinationAndDate(String origin, String destination,
                        LocalDate date);

        Flight findByDateAndOriginAndDestination(LocalDate date, String origin, String destination);

        Flight findByOriginAndDestinationAndDateAndAirLine(String origin, String destination, LocalDate date,
                        String airline);

       

}
