package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.repositories.ReviewRepository;
import com.femcoders.happy_travel.repositories.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewServiceImpl reviewService;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private UserRepository userRepository;

}
