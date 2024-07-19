package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HotelPaymentModel {

    private Long paymentId;
    private Long userId;
    private int pointsUsed;
    private Long amount;
    private Boolean success;
    private LocalDate date;
    private Long reservationId;
    
}
