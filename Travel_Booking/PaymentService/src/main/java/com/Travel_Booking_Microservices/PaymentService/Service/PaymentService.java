package com.Travel_Booking_Microservices.PaymentService.Service;

import com.Travel_Booking_Microservices.PaymentService.Entity.CarPayment;
import com.Travel_Booking_Microservices.PaymentService.Entity.FlightPayment;
import com.Travel_Booking_Microservices.PaymentService.Entity.HotelPayment;
import com.Travel_Booking_Microservices.PaymentService.Model.CarAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.FlightAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.HotelAmountModel;
import com.stripe.exception.StripeException;

public interface PaymentService {

    FlightPayment flightPayment(FlightAmountModel amountModel) throws StripeException ;

    HotelPayment HotelPayment(HotelAmountModel amountModel) throws StripeException;

    CarPayment carPayment(CarAmountModel amountModel) throws StripeException ;
    
}
