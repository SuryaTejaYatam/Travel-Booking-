package com.Travel_Booking_Microservices.ItineraryManagementService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRegistrationModel {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String reTypePassword;

    private String phoneNumber;

    private String gender;
}
