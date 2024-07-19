package com.Travel_Booking_Microservices.ItineraryManagementService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ItineraryManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItineraryManagementServiceApplication.class, args);
	}

}
