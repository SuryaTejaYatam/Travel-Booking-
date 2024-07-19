package com.Travel_Booking_Microservices.PaymentService.Exception;

import lombok.Data;

@Data
public class PaymentCustomException extends RuntimeException {

    private String errorCode;

    public PaymentCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
