package com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.HotelsModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.ReservationModel;

@FeignClient(name = "hotelFeignClient", url = "http://localhost:8082/hotel")
public interface HotelFeignClient {

    @GetMapping("/gettingHotelsByLocation")
    public List<HotelsModel> gettingHotelsByLocation(@RequestParam String location);

    @GetMapping("/gettingHotelsDetailsByHotelName")
    public List<HotelsModel> gettingHotelsDetailsByHotelName(@RequestParam String hotelName);

    @PostMapping("/reservation")
    public ReservationModel reservation(@RequestBody ReservationModel reservation, @RequestParam Long userId,
            @RequestParam String firstName);

    @PutMapping("/paymentSuccess")
    public void paymentSuccess(@RequestParam Long reservationId,@RequestParam Long paymentId);

    @GetMapping("/gettingTheResevationDetails/{reservationId}")
    public ReservationModel gettingTheResevationDetails(@PathVariable Long reservationId);

    @PutMapping("/updateReservation/{reservationId}")
    public ReservationModel updateReservation(@PathVariable Long reservationId,
            @RequestBody ReservationModel reservationModel);

    @PutMapping("/cancelingReservation/{reservationId}")
    public ReservationModel cancelingReservation(@PathVariable Long reservationId);

}
