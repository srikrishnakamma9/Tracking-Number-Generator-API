package com.tracking.number.generator.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TrackingNumberService {
    String generateTrackingNumber(
        String originCountryId,
        String destinationCountryId,
        BigDecimal weight,
        LocalDateTime createdAt,
        String customerId,
        String customerName,
        String customerSlug
    );
}
