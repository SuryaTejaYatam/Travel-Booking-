package com.Travel_Booking_Microservices.FlightBookingService.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserFlightBookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightBookingId;

    private Long userId;

    private int flightNumber;
    private String origin;
    private String destination;
    private LocalDate date;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String airLine;
    private String aircraftType;

    private Integer numberOfSeats;
    private Double seatPrice;
    private String seatType;

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private Status paymentStatus;
    
    @Enumerated(EnumType.STRING)
    private Status status;



}
