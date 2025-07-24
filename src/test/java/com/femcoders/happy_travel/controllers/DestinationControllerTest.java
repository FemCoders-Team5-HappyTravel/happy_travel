package com.femcoders.happy_travel.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.happy_travel.cloudinary.CloudinaryService;
import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import com.femcoders.happy_travel.security.JwtAuthenticationFilter;
import com.femcoders.happy_travel.security.JwtUtils;
import com.femcoders.happy_travel.services.DestinationService;
import com.femcoders.happy_travel.services.DestinationServiceImpl;
import com.femcoders.happy_travel.services.UserService;
import jakarta.annotation.Generated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<DestinationResponse> destinationResponses;

    @MockBean
    private DestinationServiceImpl destinationServiceImp;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {

        destinationResponses = new ArrayList<>();

        DestinationResponse dest1 = new DestinationResponse( 1L, "Spain", "Madrid", "Beautiful city", "spain.jpg");
        DestinationResponse dest2 = new DestinationResponse(2L,"France", "Paris", "Romantic place", "paris.jpg");

        destinationResponses.add(dest1);
        destinationResponses.add(dest2);
    }

    @Test
    void getAllDestinations_returnsList() throws Exception {

        given(destinationServiceImp.getAllDestinations()).willReturn(destinationResponses);


        mockMvc.perform(get("/destinations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("Spain"))
                .andExpect(jsonPath("$[0].city").value("Madrid"))
                .andExpect(jsonPath("$[0].description").value("Beautiful city"))
                .andExpect(jsonPath("$[0].imageUrl").value("spain.jpg"))
                .andExpect(jsonPath("$[1].country").value("France"))
                .andExpect(jsonPath("$[1].city").value("Paris"))
                .andExpect(jsonPath("$[1].description").value("Romantic place"))
                .andExpect(jsonPath("$[1].imageUrl").value("paris.jpg"));
    }
    @Test
    void getDestinationById_returnsOne() throws Exception {
         DestinationResponse dest = new DestinationResponse(1L, "Italy", "Rome", "Historic place", "rome.jpg");

        Mockito.when(destinationServiceImp.getDestinationById(1L)).thenReturn(dest);

        mockMvc.perform(get("/destinations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("Italy"))
                .andExpect(jsonPath("$.city").value("Rome"))
                .andExpect(jsonPath("$.description").value("Historic place"))
                .andExpect(jsonPath("$.imageUrl").value("rome.jpg"));
    }

    @Test
    void createDestination_returnsCreated() throws Exception {
       DestinationResponse response = new DestinationResponse(1L, "Japan", "Tokyo", "Modern and traditional", "tokyo.jpg");

        MockMultipartFile image = new MockMultipartFile("image", "tokyo.jpg", "image/jpeg", "dummy".getBytes());
        MockMultipartFile country = new MockMultipartFile("country", "", "text/plain", "Japan".getBytes());
        MockMultipartFile city = new MockMultipartFile("city", "", "text/plain", "Tokyo".getBytes());
        MockMultipartFile description = new MockMultipartFile("description", "", "text/plain", "Modern and traditional".getBytes());

        Mockito.when(destinationServiceImp.createDestination(eq(1L), any(DestinationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(multipart("/destinations/users/1")
                        .file(image)
                        .file(country)
                        .file(city)
                        .file(description))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country").value("Japan"))
                .andExpect(jsonPath("$.imageUrl").value("tokyo.jpg"));
    }

    @Test
    void updateDestination_returnsUpdated() throws Exception {
        DestinationRequest request = new DestinationRequest();
        request.setCountry("Germany");
        request.setCity("Berlin");
        request.setDescription("Capital city");

        DestinationResponse response = new DestinationResponse(1L, "Germany", "Berlin", "Capital city", "berlin.jpg");

        Mockito.when(destinationServiceImp.updateDestination(eq(1L), any(DestinationRequest.class))).thenReturn(response);

        mockMvc.perform(put("/destinations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Berlin"));
    }

    @Test
    void deleteDestination_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/destinations/1"))
                .andExpect(status().isNoContent());
    }
}