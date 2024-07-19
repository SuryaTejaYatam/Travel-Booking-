package com.Travel_Booking_Microservices.PaymentService.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long paymentId;
    private Long userId;
    private int pointsUsed;
    private Long amount;
    private Boolean success;
    private LocalDate date;
    private Long carBookingId;
    
}
