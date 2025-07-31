package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.review.ReviewRequestDTO;
import com.femcoders.happy_travel.dtos.review.ReviewResponseDTO;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.security.UserDetailsImpl;
import com.femcoders.happy_travel.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Create a new review // Accessible by OWNER and ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewResponseDTO> createReview(
            @Parameter(description = "User ID of the reviewer") @RequestParam Long userId,
            @Valid @RequestBody ReviewRequestDTO dto,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        if (!userId.equals(currentUser.getUser().getId()) && !currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(reviewService.createReview(userId, dto));
    }

    @Operation(summary = "Get reviews for a destination // Public")
    @ApiResponse(responseCode = "200", description = "List of reviews for the destination")
    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByDestination(
            @Parameter(description = "Destination ID") @PathVariable Long destinationId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByDestination(destinationId));
    }

    @Operation(summary = "Get all reviews by a user // Public")
    @ApiResponse(responseCode = "200", description = "List of reviews by the user")
    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(
            @Parameter(description = "User ID") @RequestParam Long userId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @Operation(summary = "Delete a review // Accessible by OWNER and ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden - not owner or admin"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "Review ID to delete") @PathVariable Long reviewId,
            @Parameter(description = "User ID of the requester") @RequestParam Long userId,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        boolean isAdmin = currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = currentUser.getUser().getId().equals(userId);

        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }
}
