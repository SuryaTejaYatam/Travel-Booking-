package com.Travel_Booking_Microservices.FlightBookingService.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Flight {

 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightNumber;

    private String origin;
    private String destination;

    @ElementCollection
    @CollectionTable(name = "seat_prices", joinColumns = @JoinColumn(name = "flight_number"))
    @MapKeyColumn(name = "seat_class")
    @Column(name = "price")
    private Map<String, Double> seatPrices = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "seat_availability", joinColumns = @JoinColumn(name = "flight_number"))
    @MapKeyColumn(name = "seat_class")
    @Column(name = "available_seats")
    private Map<String, Integer> seatAvailability = new HashMap<>();

    private LocalDate date;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String airLine;
    private String aircraftType;

}
