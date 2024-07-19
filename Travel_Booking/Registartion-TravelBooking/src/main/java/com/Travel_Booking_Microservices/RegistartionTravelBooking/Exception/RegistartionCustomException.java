package com.Travel_Booking_Microservices.RegistartionTravelBooking.Exception;

import lombok.Data;

@Data
public class RegistartionCustomException extends RuntimeException {

    private String errorCode;

    public RegistartionCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
