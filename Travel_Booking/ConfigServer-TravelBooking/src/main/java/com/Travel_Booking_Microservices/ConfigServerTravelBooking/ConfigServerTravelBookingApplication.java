package com.Travel_Booking_Microservices.ConfigServerTravelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerTravelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerTravelBookingApplication.class, args);
	}

}
