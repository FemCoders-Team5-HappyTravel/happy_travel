package com.femcoders.happy_travel.dtos.controllers;

import com.femcoders.happy_travel.dtos.review.ReviewRequestDTO;
import com.femcoders.happy_travel.dtos.review.ReviewResponseDTO;
import com.femcoders.happy_travel.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequestDTO dto
    ) {
        return ResponseEntity.ok(reviewService.createReview(userId, dto));
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByDestination(
            @PathVariable Long destinationId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByDestination(destinationId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long userId
    ) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }
}