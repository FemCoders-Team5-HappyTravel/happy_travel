package com.femcoders.happy_travel.dtos.review;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private String userName;
    private Long destinationId;
}
