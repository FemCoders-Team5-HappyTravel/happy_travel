package com.femcoders.happy_travel.dtos.destination;

public record DestinationResponse(
        String country,
        String city,
        String description,
        String imageUrl) {
}
