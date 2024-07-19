package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FlightModel {
    
    private int flightNumber;
    private String origin;
    private String destination;
    private Map<String, Double> seatPrices = new HashMap<>();
    private Map<String, Integer> seatAvailability = new HashMap<>();
    private LocalDate date;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String airLine;
    private String aircraftType;
   
}
