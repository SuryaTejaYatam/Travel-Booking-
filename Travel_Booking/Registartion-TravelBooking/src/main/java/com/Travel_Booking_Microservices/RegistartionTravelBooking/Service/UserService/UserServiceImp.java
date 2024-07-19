package com.Travel_Booking_Microservices.RegistartionTravelBooking.Service.UserService;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.UserRegistration;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Exception.RegistartionCustomException;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Repository.UserRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

@Override
    public UserRegistration userRegistration(UserRegistration userRegistration) {
        UserRegistration existingUser = userRegistrationRepository.findByEmail(userRegistration.getEmail());

        if (existingUser == null) {
            log.info("Encoding the password");
            userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));

            log.info("Saving the details");
            userRegistrationRepository.save(userRegistration);
            return userRegistration;
        }

        throw new RegistartionCustomException("EMAIL_PRESENT", "The email is already present");
    }

    @Override
    public UserRegistration userLogin(String email, String password) {

        log.info("finding the user");
        UserRegistration user = userRegistrationRepository.findByEmail(email);

        log.info("decoding the password");
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new RegistartionCustomException("INVALID_CREDITAILS", "INVALID EMIAL OR PASSWORD");

        }
    }

}
