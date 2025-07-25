package com.femcoders.happy_travel.dtos.destination;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationResponse {
    private Long id;
    private String country;
    private String city;
    private String description;
    @JsonProperty("image_url")
    private String imageUrl;
}
