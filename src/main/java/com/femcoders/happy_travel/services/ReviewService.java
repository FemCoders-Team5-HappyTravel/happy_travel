package com.femcoders.happy_travel.services;
import com.femcoders.happy_travel.dtos.review.ReviewRequestDTO;
import com.femcoders.happy_travel.dtos.review.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO createReview(Long userId, ReviewRequestDTO dto);
    List<ReviewResponseDTO> getReviewsByDestination(Long destinationId);
    List<ReviewResponseDTO> getReviewsByUser(Long userId);
    void deleteReview(Long reviewId, Long userId);
}
