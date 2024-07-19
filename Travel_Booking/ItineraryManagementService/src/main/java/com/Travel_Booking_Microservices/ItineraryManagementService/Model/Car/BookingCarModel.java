package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car;

import java.time.LocalDate;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingCarModel {

    private Long carBookingId;

    private Long carId;
    private Long userId;
    private String name;
    private LocalDate startingDate;
    private LocalDate endingDate;

    private String make;
    private String model;
    private int year;
    private String location;
    private double pricePerDay;
    private String fuelType;
    private String color;
    private String transmission;
    private int mileage;
    private String numberPlate;

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private CarStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
}
