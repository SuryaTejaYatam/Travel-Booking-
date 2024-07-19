package com.Travel_Booking_Microservices.CarRentalService.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.CarRentalService.Entity.BookingCar;
import com.Travel_Booking_Microservices.CarRentalService.Entity.Car;


public interface CarService {

    Car addNewCar(Car car);

    List<Car> getListOfCarByLocation(String location);

    List<Car> findListOfCarByLocationWithThePriceRange(String location, double minPrice, double maxPrice);

    List<Car> findListOfCarByLocationWithYear(String location, int year);

    Car findTheCarByCarId(Long carId);

    Car updateingTheCarAvalibility(Long carId);

    BookingCar bookingCar(Long carId, Long userId, String firstName, LocalDate startingDate, LocalDate endingDate);

    BookingCar cancelingTheCarBooking(Long bookingId);

    BookingCar findingTheBookedCar(Long carBookingId);

    void paymentSuccess(Long carBookingId,@RequestParam Long paymentId);
    
}
