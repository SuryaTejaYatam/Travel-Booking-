package com.Travel_Booking_Microservices.CarRentalService.Exception;

import lombok.Data;

@Data
public class CarCustomException extends RuntimeException {

    private String errorCode;

    public CarCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
