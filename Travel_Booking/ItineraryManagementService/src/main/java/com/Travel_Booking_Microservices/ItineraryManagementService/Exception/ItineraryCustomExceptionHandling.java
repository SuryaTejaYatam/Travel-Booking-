package com.Travel_Booking_Microservices.ItineraryManagementService.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ItineraryCustomExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ItineraryCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheDoctorService(ItineraryCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
