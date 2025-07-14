package com.femcoders.happy_travel.dtos.destination;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record DestinationRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Country must contain min 2 and max 50 characters")
        String country,

        @NotBlank(message = "City is required")
        @Size(min = 2, max = 50, message = "City must contain min 2 and max 50 characters")
        String city,

        @NotBlank(message = "Description is required")
        @Size(min = 2, max = 300, message = "Description must contain min 2 and max 300 characters")
        String description,

        @NotNull
        MultipartFile image
) {
}
