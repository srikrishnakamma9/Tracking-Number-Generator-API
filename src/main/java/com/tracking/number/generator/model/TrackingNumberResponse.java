package com.tracking.number.generator.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class TrackingNumberResponse {
	private String tracking_number;
	private String created_at;

	public TrackingNumberResponse(String trackingNumber, LocalDateTime createdAt) {
		this.tracking_number = trackingNumber;
		this.created_at = createdAt.format(DateTimeFormatter.ISO_DATE_TIME);
	}

}
