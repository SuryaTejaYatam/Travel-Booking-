package com.Travel_Booking_Microservices.HotelBookingService.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.HotelBookingService.Repository.HotelRepository;
import com.Travel_Booking_Microservices.HotelBookingService.Entity.Hotels;
import com.Travel_Booking_Microservices.HotelBookingService.Entity.Reservation;
import com.Travel_Booking_Microservices.HotelBookingService.Entity.ReservationStatus;
import com.Travel_Booking_Microservices.HotelBookingService.Exception.HotelCustomException;
import com.Travel_Booking_Microservices.HotelBookingService.Model.ReservationModel;
import com.Travel_Booking_Microservices.HotelBookingService.Repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationServiceImp implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotels> gettingHotelsByLocation(String location) {

        log.info("Finding the hotels");
        List<Hotels> hotels = hotelRepository.findByLocation(location);

        if (Objects.nonNull(hotels)) {
            return hotels;
        }

        throw new HotelCustomException("NO_HOTELS", "NO HOTELS ARE PRESENT WITH THE LOCATION");

    }

    @Override
    public List<Hotels> gettingHotelsDetailsByHotelName(String hotelName) {

        log.info("Finding the hotels");
        List<Hotels> hotels = hotelRepository.findByHotelName(hotelName);

        if (Objects.nonNull(hotels)) {
            return hotels;
        }

        throw new HotelCustomException("NO_HOTELS", "NO HOTELS ARE PRESENT WITH THE HOTELNAME");
    }

    @Override
    public Hotels addingHotel(Hotels hotels) {

        Hotels existingHotel = hotelRepository.findByHotelNameAndLocation(hotels.getHotelName(), hotels.getLocation());
        if (existingHotel == null) {
            hotelRepository.save(hotels);

            log.info("Hotel is added");
            return hotels;
        }
        else{
        throw new HotelCustomException("HOTEL_PRSENET", " HOTELS IS ALREADY PRESENT WITH THE HOTELNAME");
        }
     
    }

    @Override
    public Reservation reservation(Reservation reservation, Long userId, String firstName) {

        log.info("finding the hotel");
        Hotels hotels = hotelRepository.findByHotelId(reservation.getHotelId());

        if (hotels != null) {

            log.info("Finding the existReservation");
            Reservation existReservation1 = reservationRepository
                    .findByHotelNameAndGuestNameAndCheckinAndCheckoutAndRoomTypeAndUserId(
                            hotels.getHotelName(), firstName, reservation.getCheckin(),
                            reservation.getCheckout(),
                            reservation.getRoomType(), userId);

            log.info("Printing the existReservation1 details" + existReservation1);

            log.info("if condition");
            if (existReservation1 == null) {
                Double roomPrice = hotels.getRoomPrices().get(reservation.getRoomType());

                log.info("room price in if condition");
                if (roomPrice == null) {
                    throw new IllegalArgumentException(
                            "Room type '" + reservation.getRoomType() + "' not found in room prices map");
                }
                double totalAmount = roomPrice * reservation.getNumberOfGuests();

                Reservation savedReservation = reservationRepository.save(Reservation.builder()
                        .userId(userId)
                        .hotelId(reservation.getHotelId())
                        .hotelName(hotels.getHotelName())
                        .guestName(firstName)
                        .checkin(reservation.getCheckin())
                        .checkout(reservation.getCheckout())
                        .roomType(reservation.getRoomType())
                        .numberOfGuests(reservation.getNumberOfGuests())
                        .location(hotels.getLocation())
                        .roomPrices(totalAmount)
                        .paymentId(null)
                        .build());

                savedReservation.setPaymentStatus(ReservationStatus.PENDING);
                savedReservation.setStatus(ReservationStatus.PENDING); // Store status in the database

                reservationRepository.save(savedReservation);
                return savedReservation;
            }
            throw new HotelCustomException("RESEVERD_ALREADY", "RESERVATION IS ALREADY DONE");

        }
        throw new HotelCustomException("NO_HOTELS", "NO HOTELS ARE PRESENT WITH THE HOTELID");
    }

    @Override
    public void paymentSuccess(Long reservationId,@RequestParam Long paymentId) {

        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        if (reservation != null) {
            reservation.setPaymentId(paymentId);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setPaymentStatus(ReservationStatus.CONFIRMED); // Store status in the database
            reservationRepository.save(reservation);
        }
    }

    @Override
    public Reservation gettingTheResevationDetails(Long reservationId) {

        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        if (reservation != null) {
            return reservation;
        }

        throw new HotelCustomException("NO_RESEVATION_IS_PRSENT_WITH_RESEVATIONID",
                "RESEVATION IS NOT COMPETED BY THE ID");

    }

    @Override
    public Reservation cancelingReservation(Long reservationId) {

        Reservation existReservation1 = reservationRepository.findByReservationId(reservationId);

        if (existReservation1 != null) {

            existReservation1.setPaymentId(null);
            existReservation1.setPaymentStatus(null);
            existReservation1.setStatus(ReservationStatus.CANCELLED); // Store status in the database
            reservationRepository.save(existReservation1);
            return existReservation1;
        }
        throw new HotelCustomException("NO_RESEVATION_IS_PRSENT_WITH_RESEVATIONID",
                "RESEVATION IS NOT COMPETED BY THE ID");
    }

    @Override
    public Reservation updateReservation(Long reservationId, ReservationModel reservationModel) {

        Reservation existReservation1 = reservationRepository.findByReservationId(reservationId);
        if (existReservation1 != null) {

            Hotels hotels = hotelRepository.findByHotelId(existReservation1.getHotelId());

            if (hotels != null) {

                Double roomPrice = hotels.getRoomPrices().get(existReservation1.getRoomType());

                double totalAmount = roomPrice * existReservation1.getNumberOfGuests();

                Double price = existReservation1.getRoomPrices();
                if (reservationModel.getCheckin() != null) {
                    existReservation1.setCheckin(reservationModel.getCheckin());
                }
                if (reservationModel.getCheckout() != null) {
                    existReservation1.setCheckout(reservationModel.getCheckout());
                }
                if (reservationModel.getRoomType() != null) {
                    existReservation1.setRoomType(reservationModel.getRoomType());
                }
                if (reservationModel.getNumberOfGuests() != null) {
                    existReservation1.setNumberOfGuests(reservationModel.getNumberOfGuests());
                }

                if (totalAmount != 0.0) {
                    existReservation1.setRoomPrices(totalAmount);
                }
                if (price != null && price <= totalAmount) {
                    existReservation1.setStatus(ReservationStatus.PENDING);
                } // Store status in the database
                reservationRepository.save(existReservation1);
                return existReservation1;
            }

            throw new HotelCustomException("NO_HOTEL",
                    "HOTEL DOESNOT PRESENT WITH THE HOTEL ID");
        }
        throw new HotelCustomException("NO_RESEVATION_IS_PRSENT_WITH_RESEVATIONID",
                "RESEVATION IS NOT COMPETED BY THE ID");

    }

    @Override
    public Reservation findBookedHotel(Long reservationId) {

        Reservation existReservation1 = reservationRepository.findByReservationId(reservationId);
        if (existReservation1 != null && existReservation1.getStatus().equals(ReservationStatus.CONFIRMED)) {

            return existReservation1;
        }
        throw new HotelCustomException("NO_RESEVATION_IS_PRSENT_WITH_RESEVATIONID",
                "RESEVATION IS NOT COMPETED BY THE ID");

    }

}