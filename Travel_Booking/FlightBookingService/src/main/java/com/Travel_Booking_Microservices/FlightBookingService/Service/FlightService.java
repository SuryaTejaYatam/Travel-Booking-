package com.Travel_Booking_Microservices.FlightBookingService.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.FlightBookingService.Entity.Flight;
import com.Travel_Booking_Microservices.FlightBookingService.Entity.UserFlightBookingDetails;

public interface FlightService {

    Flight getFlightByFlightNumber(int flightNumber);

    List<Flight> getFlightByDate(String origin, String destination, LocalDate appointmentDate);

    List<Flight> getFlightByTodayDate(String origin, String destination);

    void addingNewFlightInfo(Flight flight);

    void updatingFlightInfo(Flight flight, int flightNumber);


    UserFlightBookingDetails bookFlight(int flightNumber, String seatType, Integer numberOfSeats,Long userId);

    UserFlightBookingDetails cancelingTheBookingFlight(Long flightBookingId);

    UserFlightBookingDetails getingBookingFlightDetails(Long flightBookingId);

    List<UserFlightBookingDetails> getAllBookedFlights(Long userId);

    void paymentSuccess(Long flightBookingId, Long paymentId);

    
}
