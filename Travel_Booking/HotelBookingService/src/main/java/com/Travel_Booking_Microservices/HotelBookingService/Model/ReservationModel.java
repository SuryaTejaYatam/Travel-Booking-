package com.Travel_Booking_Microservices.HotelBookingService.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReservationModel {

    private String hotelName;

    private String guestName;

    private LocalDate checkin;

    private LocalDate checkout;

    private String roomType;
;
    private Integer numberOfGuests;

}
