package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.CarStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserFlightBookingDetailsModel {

    private Long flightBookingId;

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
