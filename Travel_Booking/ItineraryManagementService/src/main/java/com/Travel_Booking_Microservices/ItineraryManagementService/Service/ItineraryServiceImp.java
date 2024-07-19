package com.Travel_Booking_Microservices.ItineraryManagementService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Travel_Booking_Microservices.ItineraryManagementService.Entity.Itinerary;
import com.Travel_Booking_Microservices.ItineraryManagementService.Exception.ItineraryCustomException;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.BookingCarModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.CarStatus;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.Status;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.UserFlightBookingDetailsModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.ReservationModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.ReservationStatus;
import com.Travel_Booking_Microservices.ItineraryManagementService.Repository.ItineraryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItineraryServiceImp implements ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Override
    public void addingBookedFlightDetails(Long userId, UserFlightBookingDetailsModel user, String startingPlace,
            String endingPlace) {

        Itinerary itinerary = itineraryRepository.findByUserIdAndFlightBookingId(userId, user.getFlightBookingId());

        if (itinerary == null) {
            Itinerary newItinerary = Itinerary.builder()
                    .userId(userId)
                    .flightBookingId(user.getFlightBookingId())
                    .flightNumber(user.getFlightNumber())
                    .origin(user.getOrigin())
                    .destination(user.getDestination())
                    .date(user.getDate())
                    .departureDateTime(user.getDepartureDateTime())
                    .arrivalDateTime(user.getArrivalDateTime())
                    .airLine(user.getAirLine())
                    .aircraftType(user.getAircraftType())
                    .numberOfSeats(user.getNumberOfSeats())
                    .seatPrice(user.getSeatPrice())
                    .seatType(user.getSeatType())
                    .flightstatus(user.getStatus())
                    .startingPlace(startingPlace)
                    .endingPlace(endingPlace)
                    .paymentIdForFlight(user.getPaymentId())
                    .paymentStatusForFlight(user.getPaymentStatus())
                    .build();

            itineraryRepository.save(newItinerary);
        }
    }

    @Override
    public void cancelingTheBookingFlight(Long flightBookingId) {
        log.info("Finding the booked flight");
        Itinerary itinerary = itineraryRepository.findByFlightBookingId(flightBookingId);
        if (itinerary != null) {

            itinerary.setPaymentStatusForFlight(null);
            itinerary.setFlightstatus(Status.CANCELLED);
            itineraryRepository.save(itinerary);
        }
    }

    @Override
    public void addingFlightPaymentDetails(Long flightBookingId, Long paymentId) {

        log.info("Finding the booked flight");
        Itinerary itinerary = itineraryRepository.findByFlightBookingId(flightBookingId);
        if (itinerary != null) {

            itinerary.setPaymentStatusForFlight(Status.CONFIRMED);
            itinerary.setPaymentIdForFlight(paymentId);
            itinerary.setFlightstatus(Status.CONFIRMED);
            itineraryRepository.save(itinerary);
        }
    }

    @Override
    public void reservation(Long userId, ReservationModel reservationModel, String startingPlace,
            String endingPlace) {

        log.info("Find bu stating place and ending place ");
        Itinerary existItinerary = itineraryRepository.findByStartingPlaceAndEndingPlaceAndUserId(startingPlace,
                endingPlace, userId);

        log.info("userId: " + userId);

        log.info("itinerary");
        Itinerary itinerary = itineraryRepository.findByUserIdAndReservationId(userId,
                reservationModel.getReservationId());

        log.info("if condition");
        if (itinerary == null && existItinerary != null) {
            existItinerary.setReservationId(reservationModel.getReservationId());
            existItinerary.setHotelId(reservationModel.getHotelId());
            existItinerary.setHotelName(reservationModel.getHotelName());
            existItinerary.setCheckin(reservationModel.getCheckin());
            existItinerary.setCheckout(reservationModel.getCheckout());
            existItinerary.setRoomType(reservationModel.getRoomType());
            existItinerary.setRoomPrices(reservationModel.getRoomPrices());
            existItinerary.setNumberOfGuests(reservationModel.getNumberOfGuests());
            existItinerary.setHotelLocation(reservationModel.getLocation());
            existItinerary.setHotelstatus(reservationModel.getStatus());
            existItinerary.setGuestName(reservationModel.getGuestName());
            existItinerary.setPaymentIdForHotel(reservationModel.getPaymentId());
            existItinerary.setPaymentStatusForHotel(reservationModel.getPaymentStatus());

            log.info("saving the hotel details");
            itineraryRepository.save(existItinerary);
        }
    }

    @Override
    public void updateReservation(Long userId, Long reservationId, ReservationModel reservationModel) {

        Itinerary existItinerary = itineraryRepository.findByUserIdAndReservationId(userId, reservationId);

        if (existItinerary != null) {

            existItinerary.setCheckin(reservationModel.getCheckin());

            existItinerary.setCheckout(reservationModel.getCheckout());

            existItinerary.setRoomType(reservationModel.getRoomType());

            existItinerary.setNumberOfGuests(reservationModel.getNumberOfGuests());

            existItinerary.setRoomPrices(reservationModel.getRoomPrices());

            existItinerary.setHotelstatus(reservationModel.getStatus());

            itineraryRepository.save(existItinerary);
        }
    }

    @Override
    public void cancelingReservation(Long userId, Long reservationId) {

        Itinerary existItinerary = itineraryRepository.findByUserIdAndReservationId(userId, reservationId);

        if (existItinerary != null) {

            existItinerary.setPaymentStatusForHotel(null);
            existItinerary.setHotelstatus(ReservationStatus.CANCELLED);
            itineraryRepository.save(existItinerary);
        }
    }

    @Override
    public void addingHotelPaymentDetails(Long userId, Long reservationId, Long paymentId) {
        Itinerary existItinerary = itineraryRepository.findByUserIdAndReservationId(userId, reservationId);

        if (existItinerary != null) {


            existItinerary.setPaymentIdForHotel(paymentId);
            existItinerary.setPaymentStatusForHotel(ReservationStatus.CONFIRMED);
            existItinerary.setHotelstatus(ReservationStatus.CONFIRMED);
            itineraryRepository.save(existItinerary);
        }
    }

    @Override
    public void bookingCar(Long userId, BookingCarModel bookingCarModel, String startingPlace, String endingPlace) {

        Itinerary existItinerary = itineraryRepository.findByStartingPlaceAndEndingPlaceAndUserId(startingPlace,
                endingPlace, userId);

        Itinerary Itinerary = itineraryRepository.findByUserIdAndCarId(userId, bookingCarModel.getCarId());

        if (Itinerary == null) {

            existItinerary.setCarBookingId(bookingCarModel.getCarBookingId());
            existItinerary.setCarId(bookingCarModel.getCarId());
            existItinerary.setStartingDate(bookingCarModel.getStartingDate());
            existItinerary.setEndingDate(bookingCarModel.getEndingDate());
            existItinerary.setMake(bookingCarModel.getMake());
            existItinerary.setModel(bookingCarModel.getModel());
            existItinerary.setYear(bookingCarModel.getYear());
            existItinerary.setCarLocation(bookingCarModel.getLocation());
            existItinerary.setPricePerDay(bookingCarModel.getPricePerDay());
            existItinerary.setFuelType(bookingCarModel.getFuelType());
            existItinerary.setColor(bookingCarModel.getColor());
            existItinerary.setTransmission(bookingCarModel.getTransmission());
            existItinerary.setMileage(bookingCarModel.getMileage());
            existItinerary.setNumberPlate(bookingCarModel.getNumberPlate());
            existItinerary.setCarStatus(bookingCarModel.getCarStatus());
            existItinerary.setPaymentIdForCar(bookingCarModel.getPaymentId());
            existItinerary.setPaymentStatusForCar(bookingCarModel.getPaymentStatus());

            itineraryRepository.save(existItinerary);

        }

    }

    @Override
    public void cancelingTheCarBooking(Long userId, Long carBookingId) {

        Itinerary Itinerary = itineraryRepository.findByUserIdAndCarBookingId(userId, carBookingId);
        if (Itinerary == null) {

            Itinerary.setPaymentStatusForCar(null);
            Itinerary.setCarStatus(CarStatus.CANCELLED);
            itineraryRepository.save(Itinerary);
        }
    }

    @Override
    public void addingCarPaymentDetails(Long userId, Long carBookingId,Long paymentId) {

        Itinerary Itinerary = itineraryRepository.findByUserIdAndCarBookingId(userId, carBookingId);
        if (Itinerary == null) {

            Itinerary.setPaymentIdForCar(paymentId);
            Itinerary.setPaymentStatusForCar(CarStatus.CONFIRMED);
            Itinerary.setCarStatus(CarStatus.CONFIRMED);
            itineraryRepository.save(Itinerary);
        }
    }

    @Override
    public Itinerary getDetailsByItineraryId(Long itineraryId) {

        log.info("id" + itineraryId);
        Itinerary itinerary = itineraryRepository.findByItineraryId(itineraryId);

        log.info("if condition");
        if (itinerary != null) {
            return itinerary;
        }

        throw new ItineraryCustomException("INVALID_ID", "NO DATA PRSENT WITH THE ID");

    }



}
