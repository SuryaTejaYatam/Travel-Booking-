package com.Travel_Booking_Microservices.ItineraryManagementService.Model.Hotel;

import java.util.HashMap;
import java.util.Map;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HotelsModel {

    private Long hotelId;
    private String hotelName;
    private String location;
    private Map<String, Double> roomPrices = new HashMap<>();
}
