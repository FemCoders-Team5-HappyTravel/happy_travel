package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.review.ReviewMapper;
import com.femcoders.happy_travel.dtos.review.ReviewRequestDTO;
import com.femcoders.happy_travel.dtos.review.ReviewResponseDTO;
import com.femcoders.happy_travel.exceptions.UserNotFoundException;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.Review;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.repositories.ReviewRepository;
import com.femcoders.happy_travel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;

    @Override
    public ReviewResponseDTO createReview(Long userId, ReviewRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Destination destination = destinationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Review review = ReviewMapper.toEntity(dto, user, destination);
        Review saved = reviewRepository.save(review);

        return ReviewMapper.toDto(saved);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByDestination(Long destinationId) {
        return reviewRepository.findByDestinationId(destinationId)
                .stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this review");
        }

        reviewRepository.delete(review);
    }
}