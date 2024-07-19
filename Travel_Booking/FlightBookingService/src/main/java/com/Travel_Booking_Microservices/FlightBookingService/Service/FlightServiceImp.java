package com.Travel_Booking_Microservices.FlightBookingService.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Travel_Booking_Microservices.FlightBookingService.Entity.Flight;
import com.Travel_Booking_Microservices.FlightBookingService.Entity.Status;
import com.Travel_Booking_Microservices.FlightBookingService.Entity.UserFlightBookingDetails;
import com.Travel_Booking_Microservices.FlightBookingService.Exception.FlightCustomException;
import com.Travel_Booking_Microservices.FlightBookingService.Repository.FlightRepository;
import com.Travel_Booking_Microservices.FlightBookingService.Repository.UserFlightBookingDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightServiceImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserFlightBookingDetailsRepository userFlightBookingDetailsRepository;

    @Override
    public Flight getFlightByFlightNumber(int flightNumber) {

        log.info("Finding the flight");
        Flight optionalFlight = flightRepository.findByFlightNumber(flightNumber);

        log.info("if condition");
        if (Objects.nonNull(optionalFlight)) {
            return optionalFlight;
        } else {
            throw new FlightCustomException("NO_fLIGHT", "Flight with flight number " + flightNumber + " not found.");
        }
    }

    @Override
    public List<Flight> getFlightByDate(String origin, String destination, LocalDate date) {

        // Find flights by origin, destination, and date
        log.info("Returning the flights");
        List<Flight> flights = flightRepository.findByOriginAndDestinationAndDate(origin, destination, date);

        log.info("if codition in thr service layer");
        if (flights != null) {

            log.info("returing the non null flights");
            return flights;
        }

        throw new FlightCustomException("NO_fLIGHT_WITH_DATE", "NO FLIGHTS PRESENT");

    }

    @Override
    public List<Flight> getFlightByTodayDate(String origin, String destination) {
        LocalDate currentDate = LocalDate.now();

        log.info("Finding flights for origin: {}, destination: {}, and current date: {}", origin, destination,
                currentDate);
        List<Flight> flights = flightRepository.findByOriginAndDestinationAndDate(origin, destination, currentDate);

        if (Objects.nonNull(flights)) {
            log.info("Returning the flights for today's date");
            return flights;
        }
        throw new FlightCustomException("NO_fLIGHT_WITH_DATE", "NO FLIGHTS PRESENT");

    }

    @Override
    public void addingNewFlightInfo(Flight flight) {
        log.info("Finding the flight");
        // Check if the flight already exists
        Flight existingFlight = flightRepository.findByOriginAndDestinationAndDateAndAirLine(
                flight.getOrigin(), flight.getDestination(), flight.getDate(), flight.getAirLine());

        log.info("if condition");
        // If the flight doesn't exist, save the details
        if (Objects.isNull(existingFlight)) {
            log.info("Saving the details");
            flightRepository.save(flight);
        } else {
            throw new FlightCustomException("FLIGHT_IS_PRSENT", "FLIGHT IS PRSENT WITH THE DEATILS");
        }
    }

    @Override
    public void updatingFlightInfo(Flight flight, int flightNumber) {

        log.info("Updating flight details for flight number: {}", flightNumber);
        Flight flight2 = flightRepository.findByFlightNumber(flightNumber);

        if (flight2 != null) {
            Map<String, Integer> newSeatAvailability = new HashMap<>(flight2.getSeatAvailability());
            Map<String, Double> newSeatPrices = new HashMap<>(flight2.getSeatPrices());

            // Update seat availability
            for (String seatClass : newSeatAvailability.keySet()) {
                if (flight.getSeatAvailability().containsKey(seatClass)) {
                    int existingSeats = flight2.getSeatAvailability().get(seatClass);
                    int newSeats = flight.getSeatAvailability().get(seatClass);
                    newSeatAvailability.put(seatClass, newSeats);
                } else {
                    throw new FlightCustomException("SEAT_UNAVALIBALE", "NO SEATS ARE AVIALABE");
                }
            }
            flight2.setSeatAvailability(newSeatAvailability);

            // Update seat prices
            for (String seatClass : newSeatPrices.keySet()) {
                if (flight.getSeatPrices().containsKey(seatClass)) {
                    Double existingPrice = flight2.getSeatPrices().get(seatClass);
                    Double newPrice = flight.getSeatPrices().get(seatClass);
                    newSeatPrices.put(seatClass, newPrice);
                } else {
                    throw new FlightCustomException("NO_SEATTYPE_IS_PRSENET", "NO SEAT TYPE" + seatClass);
                }
            }
            flight2.setSeatPrices(newSeatPrices);

            flightRepository.save(flight2);
        } else {
            throw new FlightCustomException("NO_fLIGHT_FLIGHTNUMBER", "NO FLIGHTS PRESENT WITH FLIGHTNUMBER");
        }

    }

    @Override
    public UserFlightBookingDetails bookFlight(int flightNumber, String seatType, Integer numberOfSeats, Long userId) {
        log.info("Finding the flight");
        Flight flight = flightRepository.findByFlightNumber(flightNumber);

        log.info("If Condition");
        if (flight != null) {
            UserFlightBookingDetails existuserFlightBookingDetails = userFlightBookingDetailsRepository
                    .findByFlightNumberAndUserId(flightNumber, userId);
            if (existuserFlightBookingDetails == null) {
                Double seatPrice = flight.getSeatPrices().get(seatType);
                if (seatPrice != null) {
                    double totalAmount = seatPrice * numberOfSeats;

                    Map<String, Integer> seatAvailability = flight.getSeatAvailability();
                    if (seatAvailability.containsKey(seatType) && seatAvailability.get(seatType) >= numberOfSeats) {
                        UserFlightBookingDetails userFlightBookingDetails = UserFlightBookingDetails.builder()
                                .userId(userId)
                                .flightNumber(flightNumber)
                                .origin(flight.getOrigin())
                                .destination(flight.getDestination())
                                .date(flight.getDate())
                                .departureDateTime(flight.getDepartureDateTime())
                                .arrivalDateTime(flight.getArrivalDateTime())
                                .airLine(flight.getAirLine())
                                .seatType(seatType)
                                .aircraftType(flight.getAircraftType())
                                .numberOfSeats(numberOfSeats)
                                .seatPrice(totalAmount)
                                .paymentId(null)
                                .build();

                        seatAvailability.put(seatType, seatAvailability.get(seatType) - numberOfSeats);

                        userFlightBookingDetails.setPaymentStatus(Status.PENDING);
                        userFlightBookingDetails.setStatus(Status.PENDING);
                        userFlightBookingDetailsRepository.save(userFlightBookingDetails);
                        return userFlightBookingDetails;
                    } else {
                        throw new FlightCustomException("NO_SEATS",
                                "Not enough seats available for the selected seat type.");
                    }
                } else {
                    throw new FlightCustomException("NO_SEAT_TYPE", "Invalid seat type.");
                }
            } else {
                throw new FlightCustomException("FLIGHT_IS_ALREADY_BOOKED", "Flight is already booked");
            }
        } else {
            throw new FlightCustomException("NO_FLIGHT", "Flight with flight number " + flightNumber + " not found.");
        }
    }

    @Override
    public void paymentSuccess(Long flightBookingId, Long paymentId) {

        log.info("Finding the booked flight");
        UserFlightBookingDetails userFlightBookingDetails = userFlightBookingDetailsRepository
                .findByFlightBookingId(flightBookingId);

        if (Objects.nonNull(userFlightBookingDetails)) {

            userFlightBookingDetails.setPaymentId(paymentId);
            userFlightBookingDetails.setStatus(Status.CONFIRMED);
            userFlightBookingDetails.setPaymentStatus(Status.CONFIRMED);
            log.info("saving the details");
            userFlightBookingDetailsRepository.save(userFlightBookingDetails);
        }

    }

    @Override
    public UserFlightBookingDetails getingBookingFlightDetails(Long flightBookingId) {

        log.info("Finding the booked flight");
        UserFlightBookingDetails userFlightBookingDetails = userFlightBookingDetailsRepository
                .findByFlightBookingId(flightBookingId);

        if (Objects.nonNull(userFlightBookingDetails)
                && userFlightBookingDetails.getStatus().equals(Status.CONFIRMED)) {

            return userFlightBookingDetails;
        }
        throw new FlightCustomException("NO_fLIGHT_IS_BOOKED_FLIGHTNUMBER",
                "NO FLIGHTS PRESENT IS BOOKED WITH THE BOOKINGID");

    }

    @Override
    public List<UserFlightBookingDetails> getAllBookedFlights(Long userId) {

        log.info("Finding the booked flight");
        List<UserFlightBookingDetails> userFlightBookingDetails = userFlightBookingDetailsRepository
                .findByUserId(userId);
        if (Objects.nonNull(userFlightBookingDetails)) {

            return userFlightBookingDetails;
        }

        throw new FlightCustomException("NO_FLIGHT_IS-BOOKED_BY_USERID",
                "NO FLIGHTS ARE  BOOKED WITH THE USERID");

    }

    @Override
    public UserFlightBookingDetails cancelingTheBookingFlight(Long flightBookingId) {

        log.info("Finding the booked flight");
        UserFlightBookingDetails userFlightBookingDetails = userFlightBookingDetailsRepository
                .findByFlightBookingId(flightBookingId);

        log.info("Finding the flight");
        Flight flight = flightRepository.findByFlightNumber(userFlightBookingDetails.getFlightNumber());

        log.info("Checking the booking flight is exist or not");
        if (userFlightBookingDetails != null && !userFlightBookingDetails.getStatus().equals(Status.CANCELLED)) {

            log.info("Adding the no of seats ");
            Map<String, Integer> seatAvailability = flight.getSeatAvailability();
            seatAvailability.put(userFlightBookingDetails.getSeatType(),
                    seatAvailability.get(userFlightBookingDetails.getSeatType())
                            + userFlightBookingDetails.getNumberOfSeats());
            flight.setSeatAvailability(seatAvailability);

            log.info("Canceleing the request and saving the details");
            userFlightBookingDetails.setStatus(Status.CANCELLED);
            userFlightBookingDetails.setPaymentStatus(null);
            userFlightBookingDetails.setPaymentId(null);
            userFlightBookingDetailsRepository.save(userFlightBookingDetails);

            return userFlightBookingDetails;
        }

        throw new FlightCustomException("NO_fLIGHT_IS_BOOKED_FLIGHTNUMBER",
                "NO FLIGHTS PRESENT IS BOOKED WITH THE BOOKINGID");
    }

}

// Map<String, Integer> seatAvailability = flight.getSeatAvailability();
// if (seatAvailability.containsKey(seatType) && seatAvailability.get(seatType)
// > 0) {
// seatAvailability.put(seatType, seatAvailability.get(seatType) - 1);
// flight.setSeatAvailability(seatAvailability);

// log.info("Saving the seating value after booking");
// flightRepository.save(flight);
// return flight;
