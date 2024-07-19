package com.Travel_Booking_Microservices.RegistartionTravelBooking.Service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.AdminRegistration;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Entity.UserRegistration;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Exception.RegistartionCustomException;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Repository.AdminRegistrationRepository;
import com.Travel_Booking_Microservices.RegistartionTravelBooking.Repository.UserRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImp implements AdminService {

    @Autowired
    private AdminRegistrationRepository adminRegistrationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminRegistration adminRegistration(AdminRegistration adminRegistration) {

        AdminRegistration existingAdmin = adminRegistrationRepository.findByEmail(adminRegistration.getEmail());

        if (existingAdmin == null) {
            log.info("Encoding the password");
            adminRegistration.setPassword(passwordEncoder.encode(adminRegistration.getPassword()));

            log.info("Saving the details");
            adminRegistrationRepository.save(adminRegistration);
            return adminRegistration;
        }

        throw new RegistartionCustomException("EMAIL_PRESENT", "The email is already present");
    }

    @Override
    public AdminRegistration adminLogin(String email, String password) {

        log.info("finding the user");
        AdminRegistration admin = adminRegistrationRepository.findByEmail(email);

        log.info("decoding the password");
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            return admin;
        } else {
            throw new RegistartionCustomException("INVALID_CREDITAILS", "INVALID EMIAL OR PASSWORD");

        }
    }
}
