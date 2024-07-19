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

import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.BookingCarModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.CarModel;

@FeignClient(name = "carFeignClient", url = "http://localhost:8083/car")
public interface CarFeignClient {

        @GetMapping("/getListOfCarByLocation")
        public List<CarModel> findListOfCarByLocation(@RequestParam String location);

        @GetMapping("/findListOfCarByLocationWithThePriceRange/{minPrice}/{maxPrice}")
        public List<CarModel> findListOfCarByLocationWithThePriceRange(@RequestParam String location,
                        @PathVariable double minPrice, @PathVariable double maxPrice);

        @GetMapping("/findListOfCarByLocationWithYear/{year}")
        public List<CarModel> findListOfCarByLocationWithYear(@RequestParam String location, @PathVariable int year);

        @GetMapping("/findTheCarByCarId/{carId}")
        public CarModel findTheCarByCarId(@PathVariable Long carId);

        @PostMapping("/bookingCar/{carId}/{startingDate}/{endingDate}")
        public BookingCarModel bookingCar(@PathVariable Long carId, @RequestParam Long userId,
                        @RequestParam String firstName,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate);

        @GetMapping("/findingTheBookedCar")
        public BookingCarModel findingTheBookedCar(@RequestParam Long carBookingId);

        @PutMapping("/cancelingTheCarBooking/{carBookingId}")
        public BookingCarModel cancelingTheCarBooking(@PathVariable Long carBookingId);

        @PutMapping("/paymentSuccessForCar")
        public void paymentSuccessForCar(@RequestParam Long carBookingId,@RequestParam Long paymentId);
}
