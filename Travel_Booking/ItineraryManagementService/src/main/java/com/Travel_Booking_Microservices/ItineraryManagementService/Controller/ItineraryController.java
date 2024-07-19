package com.Travel_Booking_Microservices.ItineraryManagementService.Controller;

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

import com.Travel_Booking_Microservices.ItineraryManagementService.Entity.Itinerary;
import com.Travel_Booking_Microservices.ItineraryManagementService.Exception.ItineraryCustomException;
import com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient.CarFeignClient;
import com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient.FlightFeignClient;
import com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient.HotelFeignClient;
import com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient.LoyalityFeignClient;
import com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient.PaymentFeignClient;
import com.Travel_Booking_Microservices.ItineraryManagementService.FeignClient.UserRegistrationFeignClient;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.TourModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.UserRegistrationModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.BookingCarModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Car.CarModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.FlightModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Flight.UserFlightBookingDetailsModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.HotelsModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel.ReservationModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Loyality.UserLoyaltyModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment.CarPaymentModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment.FlightPaymentModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Model.Payment.HotelPaymentModel;
import com.Travel_Booking_Microservices.ItineraryManagementService.Service.ItineraryService;
import com.stripe.exception.StripeException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/itinerary")
@Slf4j
public class ItineraryController {

    @Autowired
    private UserRegistrationFeignClient userRegistrationFeignClient;

    @Autowired
    private FlightFeignClient flightFeignClient;

    @Autowired
    private HotelFeignClient hotelFeignClient;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private LoyalityFeignClient loyalityFeignClient;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    private UserRegistrationModel userRegistrationModel;

    private String startingPlace;
    private String endingPlace;

    //send all feilds except useId
    @PostMapping("/userRegistration")
    public ResponseEntity<UserRegistrationModel> userRegistration(@RequestBody UserRegistrationModel userRegistration) {

        log.info("Sending to the Registation Module");
        UserRegistrationModel userRegistrationModel1 = userRegistrationFeignClient.userRegistration(userRegistration);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(userRegistrationModel1);
    }

    // sends email ,password
    @GetMapping("/userLogin/{email}/{password}")
    public ResponseEntity<String> userLogin(@PathVariable String email, @PathVariable String password) {

        log.info("Sending to the Registation Module");
        userRegistrationModel = userRegistrationFeignClient.userLogin(email, password);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("LOGIN");

    }

    // send startingPlace,endingPlace
    @PostMapping("/addingDetails")
    public ResponseEntity<String> addingDetails(@RequestBody TourModel tourModel) {

        log.info("saving the Travel Details");

        startingPlace = tourModel.getStartingPlace();
        endingPlace = tourModel.getEndingPlace();

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Details are added");

    }

    // send date yyyy-mm-dd
    @GetMapping("/getFlightByDate/{date}")
    public ResponseEntity<List<FlightModel>> getFlightByDate(@PathVariable LocalDate date) {

        if (startingPlace == null || endingPlace == null) {

            throw new ItineraryCustomException("ADD_DETAILS", "PLEASE ADD THE DETAILS AGAIN ...");
        }

        log.info("Sending to the Flight Module");
        List<FlightModel> flights = flightFeignClient.getFlightByDate(startingPlace, endingPlace, date);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(flights);
    }

    @GetMapping("/getFlightByTodayDate")
    public ResponseEntity<List<FlightModel>> getFlightByTodayDate() {

        log.info("Sending to the Flight Module");

        if (startingPlace != null && endingPlace != null) {
            List<FlightModel> flights = flightFeignClient.getFlightByTodayDate(startingPlace, endingPlace);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(flights);
        }

        throw new ItineraryCustomException("RESET_THE_DESTINATION", "ADD THE DESTINATION DETAILS ");
    }

    // send flightNumber
    @GetMapping("/getFlightByFlightNumber/{flightNumber}")
    public ResponseEntity<FlightModel> getFlightByFlightNumber(@PathVariable("flightNumber") int flightNumber) {

        log.info("Sending to the Flight Module");
        FlightModel flight = flightFeignClient.getFlightByFlightNumber(flightNumber);
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(flight);

    }

    // send flightNumber,seatType,numberOfSeats
    @PostMapping("/bookFlight/{flightNumber}/{seatType}/{numberOfSeats}")
    public ResponseEntity<UserFlightBookingDetailsModel> bookFlight(@PathVariable int flightNumber,
            @PathVariable String seatType, @PathVariable Integer numberOfSeats) {

        if (userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Flight Module");
        UserFlightBookingDetailsModel user = flightFeignClient.bookFlight(flightNumber, seatType, numberOfSeats,
                userRegistrationModel.getUserId());

        if (user != null) {

            log.info("Sending to the service layer");

            itineraryService.addingBookedFlightDetails(userRegistrationModel.getUserId(), user, startingPlace,
                    endingPlace);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(user);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);

    }

    @GetMapping("/getUserLoyaltyFromFlightSide")
    public ResponseEntity<UserLoyaltyModel> getUserLoyaltyFromFlightSide() {
        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Loyalty Module");
        UserLoyaltyModel userLoyaltyModel = loyalityFeignClient.getUserLoyalty(userRegistrationModel.getUserId());

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(userLoyaltyModel);
    }

    @GetMapping("/pointsHistoryFromFlightSide")
    public ResponseEntity<String> pointsHistoryFromFlightSide() {

        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Loyalty Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED)
                .body(loyalityFeignClient.getPointHistory(userRegistrationModel.getUserId()));
    }

    // userId,pointsUsed,amount,flightBookingId
    @PostMapping("/flightPayment")
    public ResponseEntity<FlightPaymentModel> flightPayment(@RequestBody FlightPaymentModel amountModel)
            throws StripeException {

        log.info("Sending to the Payment Module");
        FlightPaymentModel amountModel1 = paymentFeignClient.flightPayment(amountModel);

        log.info("sending the success status to the flight module");
        log.info("paymentId" + amountModel1.getPaymentId());
        log.info("flightBookingId"+amountModel1.getFlightBookingId());
        flightFeignClient.paymentSuccess(amountModel1.getFlightBookingId(), amountModel1.getPaymentId());

        log.info("sending the success status to the Service");

        itineraryService.addingFlightPaymentDetails(amountModel.getFlightBookingId(), amountModel1.getPaymentId());

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(amountModel1);
    }

    @GetMapping("/getAllBookedFlights")
    public ResponseEntity<List<UserFlightBookingDetailsModel>> getAllBookedFlights() {

        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Flight Module");
        List<UserFlightBookingDetailsModel> user = flightFeignClient
                .getAllBookedFlights(userRegistrationModel.getUserId());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(user);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);

    }

    @GetMapping("/getingBookingFlightDetails/{flightBookingId}")
    public ResponseEntity<UserFlightBookingDetailsModel> getingBookingFlightDetails(
            @PathVariable Long flightBookingId) {

        log.info("Sending to the Flight Module");
        UserFlightBookingDetailsModel user = flightFeignClient.getingBookingFlightDetails(flightBookingId);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(user);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);

    }

    @PutMapping("/cancelingTheBookingFlight/{flightBookingId}")
    public ResponseEntity<UserFlightBookingDetailsModel> cancelingTheBookingFlight(@PathVariable Long flightBookingId) {

        log.info("Sending to the Flight Module");
        UserFlightBookingDetailsModel user = flightFeignClient.cancelingTheBookingFlight(flightBookingId);

        if (user != null) {

            log.info("Sending to the service layer");

            itineraryService.cancelingTheBookingFlight(flightBookingId);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(user);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);

    }

    @GetMapping("/gettingHotelsByLocation")
    public ResponseEntity<List<HotelsModel>> gettingHotelsByLocation() {

        if (startingPlace == null || endingPlace == null) {

            throw new ItineraryCustomException("ADD_DETAILS", "PLEASE ADD THE DETAILS AGAIN ...");
        }

        log.info("Ending Place: " + endingPlace);

        log.info("Sending to the Hotel Module");
        List<HotelsModel> hotels = hotelFeignClient.gettingHotelsByLocation(endingPlace);
        if (hotels != null) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(hotels);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);
    }

    // send hotelName
    @GetMapping("/gettingHotelsDetailsByHotelName/{hotelName}")
    public ResponseEntity<List<HotelsModel>> gettingHotelsDetailsByHotelName(@PathVariable String hotelName) {

        log.info("Hotel Name: " + hotelName);

        log.info("Sending to the Hotel Module");
        List<HotelsModel> hotels = hotelFeignClient.gettingHotelsDetailsByHotelName(hotelName);
        if (hotels != null) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(hotels);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);
    }

    // sends hotelId,checkin,checkout,roomType,numberOfGuests
    @PostMapping("/reservation")
    public ResponseEntity<ReservationModel> reservation(@RequestBody ReservationModel reservation) {

        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Hotel Module");
        ReservationModel reservationModel = hotelFeignClient.reservation(reservation, userRegistrationModel.getUserId(),
                userRegistrationModel.getFirstName());

        if (reservationModel != null) {

            log.info("Sending to the service layer");

            itineraryService.reservation(userRegistrationModel.getUserId(), reservationModel, startingPlace,
                    endingPlace);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(reservationModel);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
    }

    @GetMapping("/getUserLoyaltyFromHotelSide")
    public ResponseEntity<UserLoyaltyModel> getUserLoyaltyFromHotelSide() {

        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Loyality Module");
        UserLoyaltyModel userLoyaltyModel = loyalityFeignClient.getUserLoyalty(userRegistrationModel.getUserId());

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(userLoyaltyModel);
    }

    @GetMapping("/getPointHistoryFromHotelSide")
    public ResponseEntity<String> getPointHistoryFromHotelSide() {

        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Loyality Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED)
                .body(loyalityFeignClient.getPointHistory(userRegistrationModel.getUserId()));
    }

    @PostMapping("/hotelPayment")
    public ResponseEntity<HotelPaymentModel> PaymentFromHotelSide(@RequestBody HotelPaymentModel amountModel)
            throws StripeException {

        if (userRegistrationModel == null || userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }
        if (startingPlace == null || endingPlace == null) {

            throw new ItineraryCustomException("ADD_DETAILS", "PLEASE ADD THE DETAILS AGAIN ...");
        }


        log.info("Sending to the Payment Module");
        HotelPaymentModel amountModel1 = paymentFeignClient.HotelPayment(amountModel);

        log.info("sending the succesful status to the hotel module");
        hotelFeignClient.paymentSuccess(amountModel.getReservationId(), amountModel1.getPaymentId());

        log.info("sending the succesful status to the service layer");
        itineraryService.addingHotelPaymentDetails(userRegistrationModel.getUserId(),amountModel.getReservationId(),
                amountModel1.getPaymentId());

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(amountModel1);
    }

    @GetMapping("/gettingTheResevationDetails/{reservationId}")
    public ResponseEntity<ReservationModel> gettingTheResevationDetails(@PathVariable Long reservationId) {

        log.info("Sending to the Hotel Module");
        ReservationModel reservationModel = hotelFeignClient.gettingTheResevationDetails(reservationId);

        if (reservationModel != null) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(reservationModel);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);
    }

    // sends checkin,checkout,roomType,numberOfGuests as per requried
    @PutMapping("/updateReservation/{reservationId}")
    public ResponseEntity<ReservationModel> updateReservation(@PathVariable Long reservationId,
            @RequestBody ReservationModel reservationModel) {

        log.info("Sending to the Hotel Module");
        ReservationModel reservationModel2 = hotelFeignClient.updateReservation(reservationId, reservationModel);
        if (reservationModel2 != null) {

            log.info("sending to the service layer");
            itineraryService.updateReservation(userRegistrationModel.getUserId(), reservationId, reservationModel2);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(reservationModel2);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);
    }

    @PutMapping("/cancelingReservation/{reservationId}")
    public ResponseEntity<ReservationModel> cancelingReservation(@PathVariable Long reservationId) {

        log.info("Sending to the Hotel Module");
        ReservationModel reservationModel2 = hotelFeignClient.cancelingReservation(reservationId);
        if (reservationModel2 != null) {

            log.info("sending to the service layer");
            itineraryService.cancelingReservation(userRegistrationModel.getUserId(), reservationId);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(reservationModel2);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);
    }

    @GetMapping("/getListOfCarByLocation")
    public ResponseEntity<List<CarModel>> findListOfCarByLocation() {

        if (startingPlace == null || endingPlace == null) {

            throw new ItineraryCustomException("RESET_THE_DESTINATION", "ADD THE DESTINATION DETAILS ");
        }

        log.info("Sending to the Car Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(carFeignClient.findListOfCarByLocation(endingPlace));
    }

    // sends minPrice ,maxPrice
    @GetMapping("/findListOfCarByLocationWithThePriceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<List<CarModel>> findListOfCarByLocationWithThePriceRange(
            @PathVariable double minPrice, @PathVariable double maxPrice) {

        if (startingPlace == null || endingPlace == null) {

            throw new ItineraryCustomException("RESET_THE_DESTINATION", "ADD THE DESTINATION DETAILS ");
        }

        log.info("Sending to the Car Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED)
                .body(carFeignClient.findListOfCarByLocationWithThePriceRange(endingPlace, minPrice, maxPrice));
    }

    // sends year
    @GetMapping("/findListOfCarByLocationWithYear/{year}")
    public ResponseEntity<List<CarModel>> findListOfCarByLocationWithYear(@PathVariable int year) {

        if (startingPlace == null || endingPlace == null) {

            throw new ItineraryCustomException("RESET_THE_DESTINATION", "ADD THE DESTINATION DETAILS ");
        }

        log.info("Sending to the Car Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED)
                .body(carFeignClient.findListOfCarByLocationWithYear(endingPlace, year));
    }

    // sends carId
    @GetMapping("/findTheCarByCarId/{carId}")
    public ResponseEntity<CarModel> findTheCarByCarId(@PathVariable Long carId) {

        log.info("Sending to the Car Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(carFeignClient.findTheCarByCarId(carId));
    }

    // sends carId,startingDate,endingDate
    @PostMapping("/bookingCar/{carId}/{startingDate}/{endingDate}")
    public ResponseEntity<BookingCarModel> bookingCar(@PathVariable Long carId, @PathVariable LocalDate startingDate,
            @PathVariable LocalDate endingDate) {

        if (userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Car Module");

        BookingCarModel bookingCarModel = carFeignClient.bookingCar(carId,
                userRegistrationModel.getUserId(), userRegistrationModel.getFirstName(), startingDate, endingDate);
        if (bookingCarModel != null) {

            log.info("sending to the Service layer for booking car");
            itineraryService.bookingCar(userRegistrationModel.getUserId(), bookingCarModel, startingPlace, endingPlace);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(bookingCarModel);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
    }

    @GetMapping("/getUserLoyaltyFromCar")
    public ResponseEntity<UserLoyaltyModel> getUserLoyaltyFromCarSide() {

        if (userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Loyality Module");
        UserLoyaltyModel userLoyaltyModel = loyalityFeignClient.getUserLoyalty(userRegistrationModel.getUserId());

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(userLoyaltyModel);
    }

    @GetMapping("/getPointHistoryFromCarSide")
    public ResponseEntity<String> getPointHistoryFromCarSide(@RequestParam Long userId) {

        if (userRegistrationModel.getUserId() == null) {
            // Handle the case when userId is not provided
            throw new ItineraryCustomException("RELOGIN_AGAIN", "PLEASE RELOGIN AGAIN ...");
        }

        log.info("Sending to the Loyality Module");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED)
                .body(loyalityFeignClient.getPointHistory(userRegistrationModel.getUserId()));
    }

    // sends userId,pointsUsed,amount,carBookingId
    @PostMapping("/carPayment")
    public ResponseEntity<CarPaymentModel> PaymentFromCarSide(@RequestBody CarPaymentModel amountModel)
            throws StripeException {

        log.info("Sending to the Payment Module");
        CarPaymentModel amountModel1 = paymentFeignClient.carPayment(amountModel);

        log.info("Sending the succesful status to car module");

        carFeignClient.paymentSuccessForCar(amountModel.getCarBookingId(), amountModel1.getPaymentId());

        log.info("Sending the succesful status to Service layer");

        itineraryService.addingCarPaymentDetails(userRegistrationModel.getUserId(),amountModel1.getCarBookingId(),amountModel1.getPaymentId());

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(amountModel1);
    }

    @GetMapping("/findingTheBookedCar/{carBookingId}")
    public ResponseEntity<BookingCarModel> findingTheBookedCar(@PathVariable Long carBookingId) {
        log.info("sending to the Car Module");

        BookingCarModel bookingCarModel = carFeignClient.findingTheBookedCar(carBookingId);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(bookingCarModel);
    }

    @PutMapping("/cancelingTheCarBooking/{carBookingId}")
    public ResponseEntity<BookingCarModel> cancelingTheCarBooking(@PathVariable Long carBookingId) {

        log.info("sending to the Car Module");

        BookingCarModel bookingCarModel = carFeignClient.cancelingTheCarBooking(carBookingId);
        if (bookingCarModel != null) {

            log.info("sending to the service layer");
            itineraryService.cancelingTheCarBooking(userRegistrationModel.getUserId(), carBookingId);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(bookingCarModel);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);

    }

    @GetMapping("/getDetailsByItineraryId/{itineraryId}")
    public ResponseEntity<Itinerary> getDetailsByItineraryId(@PathVariable Long itineraryId) {

        log.info("sending to the service layer");
        Itinerary itinerary = itineraryService.getDetailsByItineraryId(itineraryId);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(itinerary);
    }
}
