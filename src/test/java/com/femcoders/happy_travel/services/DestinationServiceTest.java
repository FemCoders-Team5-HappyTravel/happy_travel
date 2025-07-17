package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DestinationServiceImpl destinationService;

    @Test
    void createDestination_shouldReturnDestinationResponse() {
        Long userId = 1L;
        DestinationRequest destinationrequest = new DestinationRequest();
        destinationrequest.setCountry("setcountry");
        destinationrequest.setCity("setcity");
        destinationrequest.setDescription("setdescription");
        //destinationrequest.setImageUrl("barcelona.jpg");

        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        Destination savedDestination = new Destination();
        savedDestination.setId(1L);
        savedDestination.setCountry("setcountry");
        savedDestination.setCity("setcity");
        savedDestination.setDescription("Hermosa ciudad");
        savedDestination.setImageUrl("barcelona.jpg");
        savedDestination.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(destinationRepository.save(any(Destination.class))).thenReturn(savedDestination);

        var response = destinationService.createDestination(userId, destinationrequest);

        assertThat(response).isNotNull();
        assertThat(response.getCity()).isEqualTo("setcity");
        assertThat(response.getImageUrl()).isEqualTo("barcelona.jpg");
    }

    @Test
    void getAllDestinations_shouldReturnList() {
        Destination destination = new Destination();
        destination.setId(1L);
        destination.setCountry("setcountry");
        destination.setCity("setcity2");
        destination.setDescription("Capital de España");
        destination.setImageUrl("madrid.jpg");

        when(destinationRepository.findAll()).thenReturn(List.of(destination));

        var responses = destinationService.getAllDestinations();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getCity()).isEqualTo("setcity2");
    }

    @Test
    void getDestinationById_shouldReturnResponse() {
        Destination destination = new Destination();
        destination.setId(1L);
        destination.setCountry("setcountry2");
        destination.setCity("setcity3");
        destination.setDescription("Ciudad histórica");
        destination.setImageUrl("roma.jpg");

        when(destinationRepository.findById(1L)).thenReturn(Optional.of(destination));

        var response = destinationService.getDestinationById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getCity()).isEqualTo("setcity3");
    }

    @Test
    void getDestinationById_shouldThrowWhenNotFound() {
        when(destinationRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            destinationService.getDestinationById(99L);
        });

        assertThat(exception.getMessage()).contains("Destination not found");
    }

    @Test
    void updateDestination_shouldUpdateFields() {
        DestinationRequest destinationrequest = new DestinationRequest();
        destinationrequest.setCountry("setcountry3");
        destinationrequest.setCity("setcity4");
        destinationrequest.setDescription("Ciudad del amor");
        //destinationrequest.setImageUrl("paris.jpg");

        Destination destination = new Destination();
        destination.setId(1L);
        destination.setCountry("setcountry3");
        destination.setCity("setcity5");
        destination.setDescription("Otra ciudad");
        //destination.setImageUrl("vieja.jpg");

        when(destinationRepository.findById(1L)).thenReturn(Optional.of(destination));
        when(destinationRepository.save(any(Destination.class))).thenReturn(destination);

        var response = destinationService.updateDestination(1L, destinationrequest);

        assertThat(response.getCity()).isEqualTo("setcity3");
        assertThat(response.getImageUrl()).isEqualTo("paris.jpg");
    }

    @Test
    void deleteDestination_shouldCallRepositoryDelete() {
        Destination destination = new Destination();
        destination.setId(1L);

        when(destinationRepository.findById(1L)).thenReturn(Optional.of(destination));

        destinationService.deleteDestination(1L);

        verify(destinationRepository, times(1)).delete(destination);
    }
}