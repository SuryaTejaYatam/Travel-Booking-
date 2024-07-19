package com.Travel_Booking_Microservices.ItineraryManagementService.Exception;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignClientException implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        switch (response.status()) {
            case 400:
                log.info("Error message received from Feign client: {}",
                        message != null ? message.getErrorMessage() : "Unknown error");
                return new ItineraryCustomException(message.getErrorMessage(), message.getErrorCode());
            case 404:
                log.info("Error message received from Feign client: {}",
                        message != null ? message.getErrorMessage() : "Unknown error");
                return new ItineraryCustomException(message.getErrorMessage(), message.getErrorCode());
            case 500:
                log.info("Error message received from Feign client: {}",
                        message != null ? message.getErrorMessage() : "Unknown error");
                return new ItineraryCustomException(message.getErrorMessage(), message.getErrorCode());
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
