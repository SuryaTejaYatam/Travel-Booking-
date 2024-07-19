package com.Travel_Booking_Microservices.CarRentalService.Controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.http.HttpStatus;
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

import com.Travel_Booking_Microservices.CarRentalService.Entity.BookingCar;
import com.Travel_Booking_Microservices.CarRentalService.Entity.Car;

import com.Travel_Booking_Microservices.CarRentalService.Service.CarService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarControler {

    @Autowired
    private CarService carService;

    //used by the admin
    @PostMapping("/addNewCar")
    public ResponseEntity<Car> addNewCar(@RequestBody Car car) {

        log.info("Sending to the service layer");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(carService.addNewCar(car));
    }

    //used by the admin
    @PutMapping("/updateingTheCarAvalibility/{carId}")
    public ResponseEntity<Car> updateingTheCarAvalibility(@PathVariable Long carId) {

        log.info("Sending to the service layer");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(carService.updateingTheCarAvalibility(carId));
    }

    @GetMapping("/getListOfCarByLocation")
    public List<Car> findListOfCarByLocation(@RequestParam String location) {

        log.info("Sending to the service layer");
        return carService.getListOfCarByLocation(location);
    }

    @GetMapping("/findListOfCarByLocationWithThePriceRange/{minPrice}/{maxPrice}")
    public List<Car> findListOfCarByLocationWithThePriceRange(@RequestParam String location,
            @PathVariable double minPrice, @PathVariable double maxPrice) {

        log.info("Sending to the service layer");
        return carService.findListOfCarByLocationWithThePriceRange(location, minPrice, maxPrice);
    }

    @GetMapping("/findListOfCarByLocationWithYear/{year}")
    public List<Car> findListOfCarByLocationWithYear(@RequestParam String location, @PathVariable int year) {

        log.info("Sending to the service layer");
        return carService.findListOfCarByLocationWithYear(location, year);
    }

    @GetMapping("/findTheCarByCarId/{carId}")
    public Car findTheCarByCarId(@PathVariable Long carId) {

        log.info("Sending to the service layer");
        return carService.findTheCarByCarId(carId);
    }

    @PostMapping("/bookingCar/{carId}/{startingDate}/{endingDate}")
    public BookingCar bookingCar(@PathVariable Long carId, @RequestParam Long userId,
            @RequestParam String firstName, @PathVariable LocalDate startingDate, @PathVariable LocalDate endingDate) {

        log.info("Sending to the service layer");
        return carService.bookingCar(carId,userId,firstName,startingDate,endingDate);
    }

    @PutMapping("/paymentSuccessForCar")
    public void paymentSuccessForCar(@RequestParam Long carBookingId,@RequestParam Long paymentId) {

        log.info("Sending to the Flight Service");
        carService.paymentSuccess(carBookingId,paymentId);

    }

    @GetMapping("/findingTheBookedCar")
    public BookingCar findingTheBookedCar(@RequestParam Long carBookingId) {

        log.info("Sending to the service layer");
        return carService.findingTheBookedCar(carBookingId);
    }

    @PutMapping("/cancelingTheCarBooking/{carbookingId}")
    public ResponseEntity<BookingCar> cancelingTheCarBooking(@PathVariable Long carbookingId) {

        log.info("Sending to the service layer");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(carService.cancelingTheCarBooking(carbookingId));
    }

}