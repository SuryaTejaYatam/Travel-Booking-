package com.Travel_Booking_Microservices.FlightBookingService.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FlightCustomExceptionHandling {

    @ExceptionHandler(FlightCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheCarService(FlightCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
