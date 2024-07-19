package com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment.CarPaymentModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment.FlightPaymentModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment.HotelPaymentModel;
import com.stripe.exception.StripeException;

@FeignClient(name = "PaymentService-FeignClinet", url = "http://localhost:8085/stripe")
public interface PaymentFeignClient {

    @PostMapping("/flightPayment")
    public FlightPaymentModel flightPayment(@RequestBody FlightPaymentModel amountModel) throws StripeException;

    @PostMapping("/hotelPayment")
    public HotelPaymentModel HotelPayment(@RequestBody HotelPaymentModel amountModel) throws StripeException;

    
    @PostMapping("/carPayment")
    public CarPaymentModel carPayment(@RequestBody CarPaymentModel amountModel) throws StripeException;
}
