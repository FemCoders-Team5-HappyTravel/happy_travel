package com.femcoders.happy_travel.dtos.review;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;
    @NotBlank(message = "Comment is required")
    @Size(max = 500, message = "Comment must be at most 500 characters")
    private String comment;
    @NotNull(message = "Destination ID is required")
    private Long destinationId;
}
