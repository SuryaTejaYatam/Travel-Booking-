package com.Travel_Booking_Microservices.HotelBookingService.Controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
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

import com.Travel_Booking_Microservices.HotelBookingService.Entity.Hotels;
import com.Travel_Booking_Microservices.HotelBookingService.Entity.Reservation;
import com.Travel_Booking_Microservices.HotelBookingService.Model.ReservationModel;
import com.Travel_Booking_Microservices.HotelBookingService.Service.ReservationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hotel")
@Slf4j
public class HotelController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/gettingHotelsByLocation")
    public List<Hotels> gettingHotelsByLocation(@RequestParam String location) {

        log.info("Sending to the service layer");
        List<Hotels> hotels = reservationService.gettingHotelsByLocation(location);

        log.info("Finding the hotels are present ");
        if (hotels != null) {
            log.info("returing");
            return hotels;
        }
        return null;

    }

    @GetMapping("/gettingHotelsDetailsByHotelName")
    public List<Hotels> gettingHotelsDetailsByHotelName(@RequestParam String hotelName) {

        log.info("Sending to the service layer");
        List<Hotels> reservationModels = reservationService.gettingHotelsDetailsByHotelName(hotelName);

        return reservationModels;

    }

    @PostMapping("/addingHotel")
    public ResponseEntity<String> addingHotel(@RequestBody Hotels hotels) {
        reservationService.addingHotel(hotels);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Hotel Details Are Added");
    }

    @PostMapping("/reservation")
    public Reservation reservation(@RequestBody Reservation reservation,@RequestParam Long userId,@RequestParam String firstName) {

        log.info("Sending to the service layer");
        Reservation reservation1 = reservationService.reservation(reservation,userId,firstName);

        return reservation1;
    }

    @GetMapping("/gettingTheResevationDetails/{reservationId}")
    public Reservation gettingTheResevationDetails(@PathVariable Long reservationId) {

        log.info("Sending to the service layer");
        Reservation reservation1 = reservationService.gettingTheResevationDetails(reservationId);

        return reservation1;
    }

    @PutMapping("/updateReservation/{reservationId}")
    public Reservation updateReservation(@PathVariable Long reservationId,
            @RequestBody ReservationModel reservationModel) {

        log.info("Sending to the service layer");
        Reservation reservation1 = reservationService.updateReservation(reservationId, reservationModel);

        return reservation1;
    }

    @PutMapping("/paymentSuccess")
    public void paymentSuccess(@RequestParam Long reservationId,@RequestParam Long paymentId) {

        log.info("Sending to the service Service");
        reservationService.paymentSuccess(reservationId,paymentId);

    }

    @GetMapping("/findBookedHotel")
    public Reservation findBookedHotel(@RequestParam Long reservationId) {

        log.info("Sending to the service layer");
        Reservation reservation1 = reservationService.findBookedHotel(reservationId);
        return reservation1;
    }


    @PutMapping("/cancelingReservation/{reservationId}")
    public Reservation cancelingReservation(@PathVariable Long reservationId) {

        log.info("Sending to the service layer");
        Reservation reservation1 = reservationService.cancelingReservation(reservationId);

        return reservation1;
    }
}
