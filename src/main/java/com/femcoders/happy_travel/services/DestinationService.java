package com.femcoders.happy_travel.services;
import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DestinationService {
    DestinationResponse createDestination(Long userId, DestinationRequest destinationRequest);
    List<DestinationResponse> getAllDestinations();
    DestinationResponse getDestinationById(Long id);
    DestinationResponse updateDestination(Long id, DestinationRequest request);
    void deleteDestination(Long id);
    public boolean isUserOwner(Long userId, String username);
    public List<DestinationResponse> getDestinationsByUserId(Long userId);
    List<DestinationResponse> getDestinationsByUsername(String username);


    Page<DestinationResponse> getDestinationsPage(Pageable pageable);
}







