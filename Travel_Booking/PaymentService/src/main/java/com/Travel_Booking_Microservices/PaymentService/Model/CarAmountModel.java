package com.Travel_Booking_Microservices.PaymentService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarAmountModel {
    
    private Long userId;
    private int pointsUsed;
    private Long amount;
    private Boolean success;
    private Long carBookingId;
}
