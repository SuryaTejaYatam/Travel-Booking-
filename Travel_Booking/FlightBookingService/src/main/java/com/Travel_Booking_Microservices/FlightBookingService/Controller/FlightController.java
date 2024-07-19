package com.Travel_Booking_Microservices.FlightBookingService.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Travel_Booking_Microservices.FlightBookingService.Entity.Flight;
import com.Travel_Booking_Microservices.FlightBookingService.Entity.UserFlightBookingDetails;
import com.Travel_Booking_Microservices.FlightBookingService.Service.FlightService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/flight")
@Slf4j
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Getting the flight info by id
    @GetMapping("/getFlightByFlightNumber/{flightNumber}")
    public Flight getFlightByFlightNumber(@PathVariable("flightNumber") int flightNumber) {

        log.info("Sending to the Flight Service");
        Flight flight = flightService.getFlightByFlightNumber(flightNumber);

        log.info("Checking the is present or not");
        if (flight != null) {
            return flight;
        } else {
            return null;
        }
    }

    // getting the list of flight by origin,destination by the date
    @GetMapping("/getFlightByDate")
    public List<Flight> getFlightByDate(@RequestParam String origin, @RequestParam String destination,
            @RequestParam LocalDate date) {

        log.info("Sending to the Flight Service");
        List<Flight> flights = flightService.getFlightByDate(origin, destination, date);

        log.info("Checking the is present or not");
        if (flights != null) {

            log.info("Returning flights:");
            for (Flight flight : flights) {
                log.info(flight.toString());
            }
            return flights;
        } else {
            log.info("No flights found");
            return null;
        }

    }

    // getting the list of flight by origin,destination by the Todaydate
    @GetMapping("/getFlightByTodayDate")
    public List<Flight> getFlightByTodayDate(@RequestParam String origin,
            @RequestParam String destination) {

        log.info("Sending to the Flight Service");
        List<Flight> flights = flightService.getFlightByTodayDate(origin, destination);

        log.info("Checking the is present or not");
        if (flights != null) {
            return flights;
        } else {
            return null;
        }

    }

    //admin
    @PostMapping("/addingNewFlightInfo")
    public ResponseEntity<String> addingNewFlightInfo(@RequestBody Flight flight) {

        log.info("Sending to the Flight Service");
        flightService.addingNewFlightInfo(flight);
        return ResponseEntity.ok("New Flight Details Are Saved");

    }

    //used by the admin
    @PutMapping("/updatingFlightInfo/{flightNumber}")
    public ResponseEntity<String> updatingFlightInfo(@RequestBody Flight flight, @PathVariable int flightNumber) {

        log.info("Sending to the Flight Service");
        flightService.updatingFlightInfo(flight, flightNumber);
        return ResponseEntity.ok("New Flight Details Are Saved");

    }

    @PostMapping("/bookFlight/{flightNumber}/{seatType}/{numberOfSeats}")
    public UserFlightBookingDetails bookFlight(@PathVariable int flightNumber,
            @PathVariable String seatType, @PathVariable Integer numberOfSeats, @RequestParam Long userId) {

        log.info("Sending to the Flight Service");
        UserFlightBookingDetails user = flightService.bookFlight(flightNumber, seatType, numberOfSeats, userId);

        log.info("returing");

        return user;

    }

    @PutMapping("/paymentSuccess")
    public void paymentSuccess(@RequestParam Long flightBookingId, @RequestParam Long paymentId) {

        log.info("Sending to the Flight Service");
        flightService.paymentSuccess(flightBookingId, paymentId);

    }

    @GetMapping("/getingBookingFlightDetails/{flightBookingId}")
    public UserFlightBookingDetails getingBookingFlightDetails(@PathVariable Long flightBookingId) {

        log.info("Sending to the Flight Service");
        UserFlightBookingDetails user = flightService.getingBookingFlightDetails(flightBookingId);

        log.info("returing");

        return user;
    }

    @GetMapping("/getAllBookedFlights")
    public List<UserFlightBookingDetails> getAllBookedFlights(@RequestParam Long userId) {

        log.info("Sending to the Flight Service");
        List<UserFlightBookingDetails> user = flightService.getAllBookedFlights(userId);

        log.info("If COndition in the Controller");

        return user;
    }

    @PutMapping("/cancelingTheBookingFlight")
    public UserFlightBookingDetails cancelingTheBookingFlight(@RequestParam Long flightBookingId) {

        log.info("Sending to the Flight Service");
        UserFlightBookingDetails user = flightService.cancelingTheBookingFlight(flightBookingId);

        return user;
    }

}
