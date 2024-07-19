package com.Travel_Booking_Microservices.PaymentService.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PaymentCustomExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PaymentCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheDoctorService(PaymentCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
