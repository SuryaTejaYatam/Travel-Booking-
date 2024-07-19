package com.Travel_Booking_Microservices.CarRentalService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Travel_Booking_Microservices.CarRentalService.Entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByLocation(String location);

    List<Car> findByLocationAndPricePerDayBetween(String location, double minPrice, double maxPrice);

    List<Car> findByLocationAndYear(String location, int year);

    Car findByCarId(Long carId);

    Car findByNumberPlate(String numberPlate);

    // @Query("SELECT c FROM Car c WHERE c.make = ?1 AND c.model = ?2 AND c.year = ?3 AND c.location = ?4 AND c.pricePerDay = ?5 AND c.fuelType = ?6 AND c.color = ?7")
    // Car findCarsByAllParameters(String make, String model, int year, String location, double pricePerDay,
    //         String fuelType, String color);

}
