package com.femcoders.happy_travel.dtos.review;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.Review;
import com.femcoders.happy_travel.models.User;


public class ReviewMapper {

    public static ReviewResponseDTO toDto(Review reviewDTO) {
        return ReviewResponseDTO.builder()
                .id(reviewDTO.getId())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .createdAt(reviewDTO.getCreatedAt())
                .userName(reviewDTO.getUser().getUsername())
                .destinationId(reviewDTO.getDestination().getId())
                .build();
    }

    public static Review toEntity(ReviewRequestDTO dto, User user, Destination destination) {
        return Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .user(user)
                .destination(destination)
                .build();
    }
}