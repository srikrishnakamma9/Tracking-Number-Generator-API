package com.tracking.number.generator.service.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tracking.number.generator.model.TrackingNumberEntity;
import com.tracking.number.generator.repository.TrackingNumberRepository;
import com.tracking.number.generator.service.TrackingNumberService;

import jakarta.transaction.Transactional;

@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {
	private static final Logger logger = LoggerFactory.getLogger(TrackingNumberService.class);

	@Autowired
	private TrackingNumberRepository trackingNumberRepository;

	public TrackingNumberEntity saveTrackingNumber(String trackingNumber) {
		TrackingNumberEntity entity = new TrackingNumberEntity();
		entity.setTrackingNumber(trackingNumber);
		entity.setCreatedAt(LocalDateTime.now());

		return trackingNumberRepository.save(entity);
	}

	public boolean isTrackingNumberExists(String trackingNumber) {
		return trackingNumberRepository.findByTrackingNumber(trackingNumber).isPresent();
	}

	@Override
	@Transactional
	public String generateTrackingNumber(String originCountryId, String destinationCountryId, BigDecimal weight,
			LocalDateTime createdAt, String customerId, String customerName, String customerSlug) {
		String baseString = String.join("-", customerId, createdAt.toString(), originCountryId, destinationCountryId,
				weight.toPlainString(), customerSlug);
		logger.info("Generating tracking number for customer: {}", customerId);
		String trackingNumber = generateHash(baseString);
		logger.debug("Generated tracking number: {}", trackingNumber);
		// Attempt to persist the tracking number
		boolean success = false;
		while (!success) {
			try {
				TrackingNumberEntity entity = new TrackingNumberEntity();
				entity.setTrackingNumber(trackingNumber);
				entity.setCreatedAt(LocalDateTime.now());

				// Save to the database
				trackingNumberRepository.save(entity);
				success = true; // Commit the transaction
			} catch (DataIntegrityViolationException e) {
				// Retry if the tracking number already exists (due to a race condition)
				trackingNumber = generateHash(baseString + UUID.randomUUID());
				logger.error("Error generating tracking number for customer: {}", customerId, e);
			}
		}

		return trackingNumber;
	}

	private String generateHash(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				hexString.append(String.format("%02X", b));
			}

			return hexString.toString().substring(0, 16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating hash", e);
		}
	}
}
