package com.Travel_Booking_Microservices.HotelBookingService.Service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.HotelBookingService.Entity.Hotels;
import com.Travel_Booking_Microservices.HotelBookingService.Entity.Reservation;
import com.Travel_Booking_Microservices.HotelBookingService.Model.ReservationModel;

public interface ReservationService {

    List<Hotels> gettingHotelsByLocation(String location);

    List<Hotels> gettingHotelsDetailsByHotelName(String hotelName);

    Reservation reservation(Reservation reservation,Long userId,String firstName);

    Hotels addingHotel(Hotels hotels);

    Reservation cancelingReservation(Long reservationId);

    Reservation updateReservation(Long reservationId,ReservationModel reservationModel);

    Reservation gettingTheResevationDetails(Long reservationId);

    void paymentSuccess(Long reservationId,@RequestParam Long paymentId);

    Reservation findBookedHotel(Long reservationId);
    
}
