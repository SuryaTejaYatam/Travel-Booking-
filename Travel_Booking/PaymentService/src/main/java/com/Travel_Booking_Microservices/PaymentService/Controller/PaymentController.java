package com.Travel_Booking_Microservices.PaymentService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Travel_Booking_Microservices.PaymentService.Entity.CarPayment;
import com.Travel_Booking_Microservices.PaymentService.Entity.FlightPayment;
import com.Travel_Booking_Microservices.PaymentService.Entity.HotelPayment;
import com.Travel_Booking_Microservices.PaymentService.Model.CarAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.FlightAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.HotelAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Service.PaymentService;
import com.stripe.exception.StripeException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stripe")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/flightPayment")
    public FlightPayment flightPayment(@RequestBody FlightAmountModel amountModel) throws StripeException {

        log.info("Sending to the service layer");
        return paymentService.flightPayment(amountModel);
    }

    @PostMapping("/hotelPayment")
    public HotelPayment HotelPayment(@RequestBody HotelAmountModel amountModel) throws StripeException {

        log.info("Sending to the service layer");
        return paymentService.HotelPayment(amountModel);
    }

    @PostMapping("/carPayment")
    public CarPayment carPayment(@RequestBody CarAmountModel amountModel) throws StripeException {

        log.info("Sending to the service layer");
        return paymentService.carPayment(amountModel);
    }
}
