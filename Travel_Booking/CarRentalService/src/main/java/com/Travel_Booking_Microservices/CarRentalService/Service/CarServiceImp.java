package com.Travel_Booking_Microservices.CarRentalService.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.Travel_Booking_Microservices.CarRentalService.Entity.BookingCar;
import com.Travel_Booking_Microservices.CarRentalService.Entity.Car;
import com.Travel_Booking_Microservices.CarRentalService.Entity.Status;
import com.Travel_Booking_Microservices.CarRentalService.Exception.CarCustomException;
import com.Travel_Booking_Microservices.CarRentalService.Repository.BookingCarRepository;
import com.Travel_Booking_Microservices.CarRentalService.Repository.CarRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarServiceImp implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingCarRepository bookingCarRepository;

    public Car addNewCar(Car car) {

        Car car2 = carRepository.findByNumberPlate(car.getNumberPlate());
        if (car2 == null) {
            log.info("Saving thr Car details");
            car.setAvailable(true);
            carRepository.save(car);
            return car;
        }
        throw new CarCustomException("CAR_PRESENT", "CAR IS PRESENT WITH THE SAME NUMBER");
    }

    @Override
    public Car updateingTheCarAvalibility(Long carId) {

        Car car = carRepository.findByCarId(carId);
        if (car != null) {

            log.info("updating the details");
            car.setAvailable(true);
            carRepository.save(car);
            return car;
        }
        throw new CarCustomException("INVALID_CARID", "NO CAR IS PRESENT WITH THE CARID");

    }

    @Override
    public List<Car> getListOfCarByLocation(String location) {

        List<Car> cars = carRepository.findByLocation(location);
        if (cars != null) {
            return cars;
        }
        throw new CarCustomException("NO_CARS_PRESENT", "NO CAR IS PRESENT WITH IN THE LOCATION");

    }

    @Override
    public List<Car> findListOfCarByLocationWithThePriceRange(String location, double minPrice, double maxPrice) {

        List<Car> cars = carRepository.findByLocationAndPricePerDayBetween(location, minPrice, maxPrice);
        if (cars != null) {
            return cars;
        }
        throw new CarCustomException("NO_CARS_PRESENT", "NO CAR IS PRESENT WITH IN  THE PRICE RANGE");

    }

    @Override
    public List<Car> findListOfCarByLocationWithYear(String location, int year) {

        List<Car> cars = carRepository.findByLocationAndYear(location, year);
        if (cars != null) {
            return cars;
        }
        throw new CarCustomException("NO_CARS_PRESENT", "NO CAR IS PRESENT WITH THE YEAR MODEL");
    }

    @Override
    public Car findTheCarByCarId(Long carId) {

        Car car = carRepository.findByCarId(carId);
        if (car != null) {
            return car;
        }
        throw new CarCustomException("INVALID_CARID", "NO CAR IS PRESENT WITH THE CARID");

    }

    @Override
    public BookingCar bookingCar(Long carId, Long userId, String firstName, LocalDate startingDate,
            LocalDate endingDate) {

        log.info("Finding the car details");
        Car car = carRepository.findByCarId(carId);

        log.info("if condition");
        if (car != null && car.isAvailable()) {

            long numberOfDays = ChronoUnit.DAYS.between(startingDate, endingDate);

            log.info("seting the amount");
            Double price = car.getPricePerDay() * numberOfDays;

            BookingCar bookingCar = BookingCar.builder()
                    .carId(carId)
                    .userId(userId) // add userId from request
                    .name(firstName) // add name from request
                    .startingDate(startingDate) // add date from request
                    .endingDate(endingDate)
                    .totalPrice(price)
                    .make(car.getMake())
                    .model(car.getModel())
                    .year(car.getYear())
                    .location(car.getLocation())
                    .pricePerDay(car.getPricePerDay())
                    .fuelType(car.getFuelType())
                    .transmission(car.getTransmission())
                    .mileage(car.getMileage())
                    .numberPlate(car.getNumberPlate())
                    .color(car.getColor())
                    .paymentId(null)
                    .build();

            bookingCar.setCarStatus(Status.PENDING);

            log.info("saving the deatils");
            bookingCarRepository.save(bookingCar);

            log.info("seting the false and saving it");
            car.setAvailable(false);
            carRepository.save(car);

            return bookingCar;
        }
        throw new CarCustomException("INVALID_CARID", "NO CAR IS PRESENT WITH THE CARID OR THE CAR IS NOT AVAILABLE");
    }

    @Override
    public void paymentSuccess(Long carBookingId,@RequestParam Long paymentId) {
   
        BookingCar bookingCar = bookingCarRepository.findByCarBookingId(carBookingId);
        if (bookingCar != null) {
            bookingCar.setPaymentId(paymentId);
            bookingCar.setPaymentStatus(Status.CONFIRMED);
            bookingCar.setCarStatus(Status.CONFIRMED);
            bookingCarRepository.save(bookingCar);
        }

    }


    @Override
    public BookingCar findingTheBookedCar(Long carBookingId) {

        BookingCar bookingCar = bookingCarRepository.findByCarBookingId(carBookingId);
        if (bookingCar != null && bookingCar.getCarStatus().equals(Status.CONFIRMED)) {
            return bookingCar;
        }

        throw new CarCustomException("INVALID_BOOKINGID", "NO CAR IS BOOK WITH THE BOOKINGID ");
    }

    @Override
    public BookingCar cancelingTheCarBooking(Long carBookingId) {

        BookingCar bookingCar = bookingCarRepository.findByCarBookingId(carBookingId);

        if (bookingCar != null) {

            bookingCar.setPaymentId(null);
            bookingCar.setPaymentStatus(null);
            bookingCar.setCarStatus(Status.CANCELLED);
            bookingCarRepository.save(bookingCar);

            Car car = carRepository.findByCarId(bookingCar.getCarId());

            if (car != null) {
                car.setAvailable(false);
                carRepository.save(car);
            } else {
                throw new CarCustomException("INVALID_CARID", "NO CAR IS PRESENT WITH THE CARID ");
            }

            return bookingCar;
        }
        throw new CarCustomException("INVALID_BOOKINGID", "NO CAR IS BOOK WITH THE BOOKINGID ");

    }


}