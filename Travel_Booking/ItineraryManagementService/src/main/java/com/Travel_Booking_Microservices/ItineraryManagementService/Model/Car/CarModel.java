package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarModel {
    

    private Long carId;

    private String make;
    private String model;
    private int year;
    private String location;
    private double pricePerDay;
    private boolean available;
    private String fuelType;
    private String color;
    private String transmission; 
    private int mileage; 
    private String numberPlate;
}
