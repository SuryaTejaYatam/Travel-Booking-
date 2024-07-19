package com.Travel_Booking_Microservices.ItineraryManagementService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TourModel {

    private String startingPlace;
    private String endingPlace;
}
