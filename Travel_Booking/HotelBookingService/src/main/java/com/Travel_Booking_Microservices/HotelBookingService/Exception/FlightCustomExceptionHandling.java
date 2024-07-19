package com.Travel_Booking_Microservices.HotelBookingService.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FlightCustomExceptionHandling {

    @ExceptionHandler(HotelCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheCarService(HotelCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
