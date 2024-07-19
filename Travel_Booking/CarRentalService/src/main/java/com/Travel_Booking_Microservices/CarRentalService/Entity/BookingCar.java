package com.Travel_Booking_Microservices.CarRentalService.Entity;

import java.time.LocalDate;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carBookingId;

    private Long carId;
    private Long userId;
    private String name;
    private LocalDate startingDate;
    private LocalDate endingDate;

    private double totalPrice;

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
    private Status paymentStatus;
    
    @Enumerated(EnumType.STRING)
    private Status carStatus;
}
