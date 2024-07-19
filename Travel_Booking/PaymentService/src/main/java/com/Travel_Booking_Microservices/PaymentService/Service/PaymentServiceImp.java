package com.Travel_Booking_Microservices.PaymentService.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Travel_Booking_Microservices.PaymentService.Entity.CarPayment;
import com.Travel_Booking_Microservices.PaymentService.Entity.FlightPayment;
import com.Travel_Booking_Microservices.PaymentService.Entity.HotelPayment;
import com.Travel_Booking_Microservices.PaymentService.Exception.PaymentCustomException;
import com.Travel_Booking_Microservices.PaymentService.Model.CarAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.FlightAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.HotelAmountModel;
import com.Travel_Booking_Microservices.PaymentService.Model.UserLoyaltyModel;
import com.Travel_Booking_Microservices.PaymentService.Repository.CarPaymentRepository;
import com.Travel_Booking_Microservices.PaymentService.Repository.FlightPaymentRepository;
import com.Travel_Booking_Microservices.PaymentService.Repository.HotelPaymentRepository;
import com.Travel_Booking_Microservices.PaymentService.feignClient.LoyalityFeignClient;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private LoyalityFeignClient loyalityFeignClient;

    @Autowired
    private FlightPaymentRepository flightPaymentRepository;

    @Autowired
    private CarPaymentRepository carPaymentRepository;

    @Autowired
    private HotelPaymentRepository hotelPaymentRepository;

    @Value("${stripe.apikey}")
    String stripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    @Override
    public FlightPayment flightPayment(FlightAmountModel amountModel) throws StripeException {

        LocalDate currentDate = LocalDate.now();

        long tierAmount = 0;

        log.info("sending to the Loyancy");
        UserLoyaltyModel userLoyaltyModel = loyalityFeignClient.getUserLoyalty(amountModel.getUserId());

        log.info("if condition is");
        if (userLoyaltyModel != null) {

            log.info("if Condition for the points greatrer than 100");
            if (amountModel.getPointsUsed() <= 100) {
                String tier = userLoyaltyModel.getTier();

                log.info("Redeeming the points based on tier");
                switch (tier) {
                    case "Gold":
                        tierAmount = amountModel.getPointsUsed() * 7;
                        break;
                    case "Silver":
                        tierAmount = amountModel.getPointsUsed() * 5;
                        break;
                    case "Bronze":
                        tierAmount = amountModel.getPointsUsed() * 3;
                        break;
                    default:
                        break;


                }
            } else {
                throw new PaymentCustomException("LIMITED_POINTS", "Points Shuold be less than 100");
            }

        }

        loyalityFeignClient.redeemPoints(userLoyaltyModel.getUserId(), amountModel.getPointsUsed());

        log.info("placing the total amount based on tier");
        Long totalAmount = amountModel.getAmount() - tierAmount;

        log.info("paying the amount based on");
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(totalAmount)
                .setCurrency("USD")
                .setPaymentMethod("pm_card_visa")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        log.info("sending to the Loyality Module and adding the points");
        loyalityFeignClient.addPoints(userLoyaltyModel.getUserId(), 10);

        int lifetimePoints = userLoyaltyModel.getLifetimePoints() + 10;

        log.info("checking the tier based on lifetime points");
        if (lifetimePoints >= 1000) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Gold");
        } else if (lifetimePoints >= 700 && lifetimePoints < 1000) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Silver");

        } else if (lifetimePoints >= 400 && lifetimePoints < 700) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Bronze");
        }

        log.info("saving the details");
        FlightPayment payment = FlightPayment.builder()
                .amount(totalAmount)
                .pointsUsed(amountModel.getPointsUsed())
                .date(currentDate)
                .userId(amountModel.getUserId())
                .flightBookingId(amountModel.getFlightBookingId())
                .success(true)
                .build();

        flightPaymentRepository.save(payment);

        return payment;

    }

    @Override
    public HotelPayment HotelPayment(HotelAmountModel amountModel) throws StripeException {

        LocalDate currentDate = LocalDate.now();

        long tierAmount = 0;

        log.info("sending to the Loyancy");
        UserLoyaltyModel userLoyaltyModel = loyalityFeignClient.getUserLoyalty(amountModel.getUserId());

        log.info("if condition is");
        if (userLoyaltyModel != null) {

            if (amountModel.getPointsUsed() <= 100) {
                String tier = userLoyaltyModel.getTier();

                log.info("Redeeming the points based on tier");
                switch (tier) {
                    case "Gold":
                        tierAmount = amountModel.getPointsUsed() * 5;
                        break;
                    case "Silver":
                        tierAmount = amountModel.getPointsUsed() * 3;
                        break;
                    case "Bronze":
                        tierAmount = amountModel.getPointsUsed() * 2;
                        break;
                    default:
                        break;


                }
            } else {
                throw new PaymentCustomException("LIMITED_POINTS", "Points Shuold be less than 100");
            }

        }

        loyalityFeignClient.redeemPoints(userLoyaltyModel.getUserId(), amountModel.getPointsUsed());

        log.info("placing the total amount based on tier");
        Long totalAmount = amountModel.getAmount() - tierAmount;

        log.info("paying the amount based on");
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(totalAmount)
                .setCurrency("USD")
                .setPaymentMethod("pm_card_visa")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        log.info("sending to the Loyality Module and adding the points");
        loyalityFeignClient.addPoints(userLoyaltyModel.getUserId(), 10);

        int lifetimePoints = userLoyaltyModel.getLifetimePoints() + 10;

        log.info("checking the tier based on lifetime points");
        if (lifetimePoints >= 1000) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Gold");
        } else if (lifetimePoints >= 700 && lifetimePoints < 1000) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Silver");

        } else if (lifetimePoints >= 400 && lifetimePoints < 700) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Bronze");
        }

        HotelPayment payment = HotelPayment.builder()
                .amount(totalAmount)
                .pointsUsed(amountModel.getPointsUsed())
                .date(currentDate)
                .reservationId(amountModel.getReservationId())
                .userId(amountModel.getUserId())
                .success(true)
                .build();

        hotelPaymentRepository.save(payment);

        return payment;

    }

    @Override
    public CarPayment carPayment(CarAmountModel amountModel) throws StripeException {

        LocalDate currentDate = LocalDate.now();

        long tierAmount = 0;

        log.info("sending to the Loyancy");
        UserLoyaltyModel userLoyaltyModel = loyalityFeignClient.getUserLoyalty(amountModel.getUserId());

        log.info("if condition is");
        if (userLoyaltyModel != null) {

            if (amountModel.getPointsUsed() <= 100) {
                String tier = userLoyaltyModel.getTier();

                log.info("Redeeming the points based on tier");
                switch (tier) {
                    case "Gold":
                        tierAmount = amountModel.getPointsUsed() * 4;
                        break;
                    case "Silver":
                        tierAmount = amountModel.getPointsUsed() * 3;
                        break;
                    case "Bronze":
                        tierAmount = amountModel.getPointsUsed() * 2;
                        break;
                    default:
                        break;


                }
            } else {
                throw new PaymentCustomException("LIMITED_POINTS", "Points Shuold be less than 100");
            }

        }

        loyalityFeignClient.redeemPoints(userLoyaltyModel.getUserId(), amountModel.getPointsUsed());
        log.info("placing the total amount based on tier");
        Long totalAmount = amountModel.getAmount() - tierAmount;

        log.info("paying the amount based on");
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(totalAmount)
                .setCurrency("USD")
                .setPaymentMethod("pm_card_visa")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        log.info("sending to the Loyality Module and adding the points");
        loyalityFeignClient.addPoints(userLoyaltyModel.getUserId(), 10);

        int lifetimePoints = userLoyaltyModel.getLifetimePoints() + 10;

        log.info("checking the tier based on lifetime points");
        if (lifetimePoints >= 1000) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Gold");
        } else if (lifetimePoints >= 700 && lifetimePoints < 1000) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Silver");

        } else if (lifetimePoints >= 400 && lifetimePoints < 700) {

            loyalityFeignClient.updateTier(userLoyaltyModel.getUserId(), "Bronze");
        }

        CarPayment payment = CarPayment.builder()
                .amount(totalAmount)
                .pointsUsed(amountModel.getPointsUsed())
                .date(currentDate)
                .carBookingId(amountModel.getCarBookingId())
                .userId(amountModel.getUserId())
                .success(true)
                .build();

        carPaymentRepository.save(payment);

        return payment;

    }
}
