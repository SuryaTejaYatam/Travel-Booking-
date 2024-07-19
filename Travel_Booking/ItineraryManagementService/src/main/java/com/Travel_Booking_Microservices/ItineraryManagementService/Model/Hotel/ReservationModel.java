package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel;

import java.time.LocalDate;

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
public class ReservationModel {

    private Long reservationId;

    private Long userId;
    private Long hotelId;
    private String hotelName;
    private String guestName;
    private LocalDate checkin;
    private LocalDate checkout;
    private String roomType;
    private Double roomPrices;
    private Integer numberOfGuests;
    private String location;

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
