package com.Travel_Booking_Microservices.ApiGatewayTravelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayTravelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayTravelBookingApplication.class, args);
	}

}
