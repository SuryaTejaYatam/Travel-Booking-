package com.Travel_Booking_Microservices.LoyaltyService.Exception;

import lombok.Data;

@Data
public class LoyalityCustomException extends RuntimeException {

    private String errorCode;

    public LoyalityCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
