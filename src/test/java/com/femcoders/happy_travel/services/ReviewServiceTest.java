package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.review.ReviewRequestDTO;
import com.femcoders.happy_travel.dtos.review.ReviewResponseDTO;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.Review;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.repositories.ReviewRepository;
import com.femcoders.happy_travel.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private User testUser;
    private Destination testDestination;
    private Review testReview;
    private ReviewRequestDTO reviewRequestDTO;
    private ReviewResponseDTO reviewResponseDTO;

    @BeforeEach
    public void setUp() {
        reviewService = Mockito.spy(new ReviewServiceImpl(reviewRepository, userRepository, destinationRepository));
        testUser = new User();
        testUser.setId(10L);
        testUser.setUsername("testUser");
        testDestination = Destination.builder()
                .id(100L)
                .country("Spain")
                .city("Barcelona")
                .description("Very cool!")
                .imageUrl("img.jpg")
                .build();
        testReview = Review.builder()
                .id(1L)
                .rating(5)
                .comment("Great city!")
                .destination(testDestination)
                .user(testUser)
                .build();
        reviewRequestDTO = new ReviewRequestDTO(5, "Great city!", 100L);
    }

    @Test
    void getReviewByUserIdTest_Success() {
        Mockito.when(reviewRepository.findByUserId(10L))
                .thenReturn(List.of(testReview));

        List<ReviewResponseDTO> responses = reviewService.getReviewsByUser(10L);

        assertEquals(1, responses.size());
        assertEquals("Great city!", responses.getFirst().getComment());
    }
    @Test
    void getReviewsByDestinationIdTest_Success() {
        Mockito.when(reviewRepository
                        .findByDestinationId(100L))
                .thenReturn(List.of(testReview));

        List<ReviewResponseDTO> responses = reviewService.getReviewsByDestination(100L);

        assertEquals(1, responses.size());
        assertEquals("Great city!", responses.getFirst().getComment());
    }
}
