package com.Travel_Booking_Microservices.HotelBookingService.Entity;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String hotelName;
    private String location;
    @ElementCollection
    @CollectionTable(name = "room_prices", joinColumns = @JoinColumn(name = "reservation_id"))
    @MapKeyColumn(name = "room_type")
    @Column(name = "price")
    private Map<String, Double> roomPrices = new HashMap<>();

}
