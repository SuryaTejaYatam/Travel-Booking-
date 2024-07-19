package com.Travel_Booking_Microservices.FlightBookingService.Exception;

import lombok.Data;

@Data
public class FlightCustomException extends RuntimeException {

    private String errorCode;

    public FlightCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
