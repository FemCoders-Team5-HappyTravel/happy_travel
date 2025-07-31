package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.destination.DestinationMapper;
import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.repositories.UserRepository;
import com.femcoders.happy_travel.services.DestinationService;
import com.femcoders.happy_travel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {


    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;


    @Override
    public List<DestinationResponse> getDestinationsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Destination> destinations = destinationRepository.findAllByUserId(user.getId());
        return destinations.stream().map(DestinationMapper::toResponse).toList();
    }

    @Override
    public DestinationResponse createDestination(Long userId, DestinationRequest destinationRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String imageUrl = destinationRequest.getImage();

        Destination destination = DestinationMapper.toEntity(destinationRequest, imageUrl);
        destination.setUser(user);

        Destination saved = destinationRepository.save(destination);
        return DestinationMapper.toResponse(saved);
    }

    @Override
    public List<DestinationResponse> getFilteredDestinations(String searchTerm) {
        List<Destination> destinations = destinationRepository.findBySearchTerm(searchTerm);
        return destinations.stream().map(DestinationMapper::toResponse).toList();
    }

    @Override
    public DestinationResponse getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        return DestinationMapper.toResponse(destination);
    }

    @Override
    public DestinationResponse updateDestination(Long id, DestinationRequest destinationRequest) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        destination.setCountry(destinationRequest.getCountry());
        destination.setCity(destinationRequest.getCity());
        destination.setDescription(destinationRequest.getDescription());

        if (destinationRequest.getImage() != null && !destinationRequest.getImage().isEmpty()) {
            destination.setImageUrl(destinationRequest.getImage());
        }

        Destination updated = destinationRepository.save(destination);
        return DestinationMapper.toResponse(updated);
    }

    @Override
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        destinationRepository.delete(destination);
    }

    @Override
    public boolean isUserOwner(Long userId, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        return user.getId().equals(userId);
    }

    @Override
    public List<DestinationResponse> getDestinationsByUserId(Long userId) {
        List<Destination> destinations = destinationRepository.findAllByUserId(userId);
        // Convert entities to DTOs
        return destinations.stream()
                .map(DestinationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> getDestinationsPage(Pageable pageable) {
        return destinationRepository.findAll(pageable)
                .map(DestinationMapper::toResponse);
    }
}











