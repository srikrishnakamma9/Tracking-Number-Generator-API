package com.tracking.number.generator.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracking.number.generator.model.TrackingNumberResponse;
import com.tracking.number.generator.service.TrackingNumberService;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api")
public class TrackingNumberController {

    @Autowired
    private TrackingNumberService trackingNumberService;

    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingNumberResponse> getNextTrackingNumber(
        @RequestParam @Pattern(regexp = "^[A-Z]{2}$") String origin_country_id,
        @RequestParam @Pattern(regexp = "^[A-Z]{2}$") String destination_country_id,
        @RequestParam @DecimalMin("0.001") @DecimalMax("999.999") BigDecimal weight,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime created_at,
        @RequestParam @NotBlank String customer_id,
        @RequestParam @NotBlank String customer_name,
        @RequestParam @NotBlank String customer_slug
    ) {
        String trackingNumber = trackingNumberService.generateTrackingNumber(
            origin_country_id, destination_country_id, weight, created_at, customer_id, customer_name, customer_slug
        );
        return ResponseEntity.ok(new TrackingNumberResponse(trackingNumber, LocalDateTime.now()));
    }
}
