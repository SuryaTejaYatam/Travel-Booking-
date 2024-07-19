package com.Travel_Booking_Microservices.HotelBookingService.Exception;

import lombok.Data;

@Data
public class HotelCustomException extends RuntimeException {

    private String errorCode;

    public HotelCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
