package com.Travel_Booking_Microservices.HotelBookingService.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.HotelBookingService.Entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByHotelNameAndGuestNameAndCheckinAndCheckoutAndRoomType(
        String hotelName, String guestName, LocalDate checkin, LocalDate checkout, String roomType);

    Reservation findByHotelNameAndGuestName(String hotelName, String guestName);

    Reservation findByReservationId(Long reservationId);

    Reservation findByHotelNameAndGuestNameAndCheckinAndCheckoutAndRoomTypeAndUserId(String hotelName, String guestName,
            LocalDate checkin, LocalDate checkout, String roomType, Long userId);
}
