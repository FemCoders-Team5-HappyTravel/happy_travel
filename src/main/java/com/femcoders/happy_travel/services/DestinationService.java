package com.femcoders.happy_travel.services;
import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import java.util.List;

public interface DestinationService {
    DestinationResponse createDestination(Long userId, DestinationRequest destinationRequest);
    DestinationResponse getDestinationById(Long id);
    DestinationResponse updateDestination(Long id, DestinationRequest request);
    void deleteDestination(Long id);
    public boolean isUserOwner(Long userId, String username);
    public List<DestinationResponse> getDestinationsByUserId(Long userId);
    List<DestinationResponse> getDestinationsByUsername(String username);
    List<DestinationResponse> getFilteredDestinations(String searchTerm);
}






