package com.Travel_Booking_Microservices.ItineraryManagementService.Service;

import com.Travel_Booking_Microservices.ItineraryManagementService.Entity.Itinerary;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.BookingCarModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.UserFlightBookingDetailsModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.ReservationModel;

public interface ItineraryService {

    void addingBookedFlightDetails(Long userId, UserFlightBookingDetailsModel user, String startingPlace,
            String endingPlace);

    void cancelingTheBookingFlight(Long flightBookingId);

    void reservation(Long userId, ReservationModel reservationModel, String startingPlace,
            String endingPlace);

    void updateReservation(Long userId, Long reservationId2, ReservationModel reservationModel);

    void cancelingReservation(Long userId, Long reservationId);

    void bookingCar(Long userId, BookingCarModel bookingCarModel, String startingPlace, String endingPlace);

    void cancelingTheCarBooking(Long userId, Long carBookingId);

    Itinerary getDetailsByItineraryId(Long itineraryId);

    void addingFlightPaymentDetails(Long flightBookingId, Long paymentId);

    void addingHotelPaymentDetails(Long userId, Long resevationId, Long paymentId);

void addingCarPaymentDetails(Long userId, Long carBookingId, Long paymentId);

}
