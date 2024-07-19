package com.Travel_Booking_Microservices.ItineraryManagementService.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.CarStatus;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.Status;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.ReservationStatus;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itineraryId;

    private Long userId;
    private String guestName;

    private String startingPlace;
    private String endingPlace;

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

    private Long paymentIdForFlight;

    @Enumerated(EnumType.STRING)
    private Status paymentStatusForFlight;

    @Enumerated(EnumType.STRING)
    private Status flightstatus;

    private Long reservationId;
    private Long hotelId;
    private String hotelName;
    private LocalDate checkin;
    private LocalDate checkout;
    private String roomType;
    private Double roomPrices;
    private Integer numberOfGuests;
    private String hotelLocation;

    private Long paymentIdForHotel;

    @Enumerated(EnumType.STRING)
    private ReservationStatus paymentStatusForHotel;

    @Enumerated(EnumType.STRING)
    private ReservationStatus hotelstatus;

    private Long carBookingId;
    private Long carId;
    // private String name;
    private LocalDate startingDate;
    private LocalDate endingDate;

    private String make;
    private String model;
    private int year;
    private String carLocation;
    private double pricePerDay;
    private String fuelType;
    private String color;
    private String transmission;
    private int mileage;
    private String numberPlate;

    private Long paymentIdForCar;

    @Enumerated(EnumType.STRING)
    private CarStatus paymentStatusForCar;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
}
