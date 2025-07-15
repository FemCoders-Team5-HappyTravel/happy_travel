package com.femcoders.happy_travel.dtos.destination;

import com.femcoders.happy_travel.models.Destination;
import org.springframework.stereotype.Component;



@Component
public class DestinationMapper {
    public static Destination toEntity(DestinationRequest destinationRequest, String imageUrl) {
        return Destination.builder()
                .country(destinationRequest.getCountry())
                .city(destinationRequest.getCity())
                .description(destinationRequest.getDescription())
                .imageUrl(imageUrl)
                .build();
    }

    public static DestinationResponse toResponse(Destination destination) {
        return new DestinationResponse(destination.getCountry(), destination.getCity(), destination.getDescription(), destination.getImageUrl());
    }

}
