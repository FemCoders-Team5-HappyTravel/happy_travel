package com.femcoders.happy_travel.repositories;

import com.femcoders.happy_travel.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDestinationId(Long destinationId);
    List<Review> findByUserId(Long userId);
}