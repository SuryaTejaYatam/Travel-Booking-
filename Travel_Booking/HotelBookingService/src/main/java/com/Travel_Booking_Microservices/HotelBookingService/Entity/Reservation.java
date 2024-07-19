package com.Travel_Booking_Microservices.HotelBookingService.Entity;

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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
