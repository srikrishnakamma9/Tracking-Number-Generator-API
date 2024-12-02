package com.tracking.number.generator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tracking.number.generator.model.TrackingNumberEntity;

@Repository
public interface TrackingNumberRepository extends JpaRepository<TrackingNumberEntity, Long> {
    // Optional: Find by tracking number (not necessary in this approach)
    Optional<TrackingNumberEntity> findByTrackingNumber(String trackingNumber);
}
