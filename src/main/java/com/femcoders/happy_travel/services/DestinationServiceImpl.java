package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.destination.DestinationMapper;
import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.services.DestinationService;
import com.femcoders.happy_travel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService{
    private final DestinationRepository destinationRepository;
    private final UserService userService;

    @Override
    public DestinationResponse createDestination(@org.jetbrains.annotations.NotNull DestinationRequest request) {
        String imageUrl = saveImage(request.getImage());

        User currentUser = userService.getCurrentAuthenticatedUser();

        Destination destination = DestinationMapper.toEntity(request, imageUrl);
        destination.setUser(currentUser);

        Destination saved = destinationRepository.save(destination);

        return DestinationMapper.toResponse(saved);
    }

    @Override
    public List<DestinationResponse> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(DestinationMapper::toResponse)
                .collect(Collectors.toList());
    }

    private String saveImage(MultipartFile image) {
        return "https://example.com/images/" + image.getOriginalFilename();
    }
}






