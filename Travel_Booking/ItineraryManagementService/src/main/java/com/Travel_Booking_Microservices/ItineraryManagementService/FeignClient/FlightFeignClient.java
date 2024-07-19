package com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.FlightModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.UserFlightBookingDetailsModel;

@FeignClient(name = "flightFeignClient", url = "http://localhost:8081/flight")
public interface FlightFeignClient {

        @GetMapping("/getFlightByDate")
        public List<FlightModel> getFlightByDate(@RequestParam String origin,
                        @RequestParam String destination,
                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

        @GetMapping("/getFlightByTodayDate")
        public List<FlightModel> getFlightByTodayDate(@RequestParam String origin,
                        @RequestParam String destination);

        @GetMapping("/getFlightByFlightNumber/{flightNumber}")
        public FlightModel getFlightByFlightNumber(@PathVariable("flightNumber") int flightNumber);

        @PostMapping("/bookFlight/{flightNumber}/{seatType}/{numberOfSeats}")
        public UserFlightBookingDetailsModel bookFlight(@PathVariable int flightNumber,
                        @PathVariable String seatType, @PathVariable Integer numberOfSeats, @RequestParam Long userId);

        @PutMapping("/cancelingTheBookingFlight")
        public UserFlightBookingDetailsModel cancelingTheBookingFlight(@RequestParam Long flightBookingId);

        @GetMapping("/getingBookingFlightDetails/{flightBookingId}")
        public UserFlightBookingDetailsModel getingBookingFlightDetails(@PathVariable Long flightBookingId);

        @GetMapping("/getAllBookedFlights")
        public List<UserFlightBookingDetailsModel> getAllBookedFlights(@RequestParam Long userId);

        @PutMapping("/paymentSuccess")
        public void paymentSuccess(@RequestParam Long flightBookingId,@RequestParam Long paymentId);
}
