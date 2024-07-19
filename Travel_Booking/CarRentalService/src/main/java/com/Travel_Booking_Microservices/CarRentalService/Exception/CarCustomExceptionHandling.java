package com.Travel_Booking_Microservices.CarRentalService.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CarCustomExceptionHandling {

    @ExceptionHandler(CarCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheCarService(CarCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
