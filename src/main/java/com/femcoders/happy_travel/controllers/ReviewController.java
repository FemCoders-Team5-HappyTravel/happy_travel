package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.review.ReviewRequestDTO;
import com.femcoders.happy_travel.dtos.review.ReviewResponseDTO;
import com.femcoders.happy_travel.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(
            @Parameter(description = "User ID of the reviewer") @RequestParam Long userId,
            @Valid @RequestBody ReviewRequestDTO dto
    ) {
        return ResponseEntity.ok(reviewService.createReview(userId, dto));
    }

    @Operation(summary = "Get reviews for a specific destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews for the destination")
    })
    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByDestination(
            @Parameter(description = "Destination ID") @PathVariable Long destinationId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByDestination(destinationId));
    }

    @Operation(summary = "Get all reviews by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews by the user")
    })
    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(
            @Parameter(description = "User ID") @RequestParam Long userId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @Operation(summary = "Delete a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - not the owner"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "Review ID to delete") @PathVariable Long reviewId,
            @Parameter(description = "User ID of the requester") @RequestParam Long userId
    ) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }
}
