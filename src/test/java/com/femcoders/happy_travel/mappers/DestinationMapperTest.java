package com.femcoders.happy_travel.mappers;

import com.femcoders.happy_travel.dtos.destination.DestinationMapper;
import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import com.femcoders.happy_travel.models.Destination;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DestinationMapperTest {

    @Test
    void toEntity_shouldMapRequestToEntity() {
        DestinationRequest destinationRequest = new DestinationRequest();
        destinationRequest.setCountry("setcountry");
        destinationRequest.setCity("setcity");
        destinationRequest.setDescription("setdescription");

        String imageUrl = "barcelona.jpg";
        Destination entity = DestinationMapper.toEntity(destinationRequest, imageUrl);

        assertThat(entity.getCountry()).isEqualTo("setcountry");
        assertThat(entity.getCity()).isEqualTo("setcity");
        assertThat(entity.getDescription()).isEqualTo("setdescription");
        assertThat(entity.getImageUrl()).isEqualTo("barcelona.jpg");
    }

    @Test
    void toResponse_shouldMapEntityToResponse() {
        Destination destination = new Destination();
        destination.setId(1L);
        destination.setCountry("setcountry");
        destination.setCity("setcity");
        destination.setDescription("setdescription");
        destination.setImageUrl("barcelona.jpg");

        DestinationResponse response = DestinationMapper.toResponse(destination);

        //assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getCountry()).isEqualTo("setcountry");
        assertThat(response.getCity()).isEqualTo("setcity");
        assertThat(response.getDescription()).isEqualTo("setdescription");
        assertThat(response.getImageUrl()).isEqualTo("barcelona.jpg");
    }
}