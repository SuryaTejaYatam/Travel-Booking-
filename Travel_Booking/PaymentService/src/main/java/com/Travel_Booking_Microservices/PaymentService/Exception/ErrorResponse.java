package com.Travel_Booking_Microservices.PaymentService.Exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    

    private String errorMessage;
    private String errorCode;
}