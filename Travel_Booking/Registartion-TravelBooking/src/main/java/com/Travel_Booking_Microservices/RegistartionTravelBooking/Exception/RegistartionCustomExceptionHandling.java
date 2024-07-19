package com.Travel_Booking_Microservices.RegistartionTravelBooking.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RegistartionCustomExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegistartionCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheDoctorService(RegistartionCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
