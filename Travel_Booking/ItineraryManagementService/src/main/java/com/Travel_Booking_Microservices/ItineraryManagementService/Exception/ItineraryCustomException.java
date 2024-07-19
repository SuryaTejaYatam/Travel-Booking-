package com.Travel_Booking_Microservices.ItineraryManagementService.Exception;

import lombok.Data;

@Data
public class ItineraryCustomException extends RuntimeException {

    private String errorCode;

    public ItineraryCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
