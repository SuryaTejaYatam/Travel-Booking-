package com.Travel_Booking_Microservices.EurekaServerTravelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerTravelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerTravelBookingApplication.class, args);
	}

}
