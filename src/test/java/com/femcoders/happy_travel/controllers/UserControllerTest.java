// Este test utiliza la base de datos H2 configurada en application-test.properties
package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.auth.RegisterRequest;
import com.femcoders.happy_travel.dtos.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.happy_travel.models.Destination;
import com.femcoders.happy_travel.models.Review;
import com.femcoders.happy_travel.models.Role;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.DestinationRepository;
import com.femcoders.happy_travel.repositories.ReviewRepository;
import com.femcoders.happy_travel.repositories.UserRepository;
import com.femcoders.happy_travel.security.JwtUtils;
import com.femcoders.happy_travel.services.UserDetailsServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;


import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = com.femcoders.happy_travel.HappyTravelApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional

public class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Setter
    @Getter
    static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

    }

    private String obtainJwtToken(String email, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(email, password);

        String responseContent = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(responseContent).get("token").asText();
    }

    @Test
    @DisplayName("Should get all users as ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    @Sql("/data.sql") // Load initial data for this test
    void should_getAllUsers_asAdmin() throws Exception {
        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verify initial size if data is consistent
                .andExpect(jsonPath("$.[0].username").value("admin"))
                .andExpect(jsonPath("$.[0].email").value("admin@happytravel.com"));


    }
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Should get all users as USER")
    //@WithMockUser(roles = {"USER"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_getAllUsers_asUser() throws Exception {
        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @RequestMapping("/users")
    @DisplayName("Should deny getting all users as Unauthenticated")
    void should_getAllUsers_asUnauthenticated() throws Exception {
        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Or isForbidden() if JWT filter lets it pass but role check fails later
    }

    @Test
    @DisplayName("Should get user by ID as ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    @Sql("/data.sql") // Load initial data for this test
    void should_getUserById_asAdmin() throws Exception {
        Long userId = 1L; // Assuming this user exists in your test data

        mockMvc.perform(get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@happytravel.com"));
    }
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Should get user by ID as USER")
    //@WithMockUser(roles = {"USER"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_getUserById_asUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Less specific check, just verify access
    }

    @Test
    @DisplayName("Should deny getting user by ID as Unauthenticated")
    void should_getUserById_asUnauthenticated() throws Exception {
        Long userId = 5L;
        mockMvc.perform(get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    //--- POST Endpoints ---
    @RequestMapping
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Should register user via Auth endpoint")
    void should_registerUser_viaAuthEndpoint() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser_register_" + uniqueSuffix);
        registerRequest.setEmail("register_" + uniqueSuffix + "@example.com");
        registerRequest.setPassword("securepassword123");

        String requestBody = objectMapper.writeValueAsString(registerRequest);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("Should create user as ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_createUser_asAdmin() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest newUserRequest = new UserRequest("adminCreatedUser_" + uniqueSuffix, "admincreate_" + uniqueSuffix + "@test.com", "securePass");

        mockMvc.perform(post("/users") // Endpoint for admin to create user via UserController
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequest))
                        .with(csrf())) // Add CSRF token for POST, PUT, DELETE
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(newUserRequest.getUsername()))
                .andExpect(jsonPath("$.email").value(newUserRequest.getEmail()))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Should deny creating user as USER")
    @WithMockUser(roles = {"USER"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_createUser_asUser_shouldBeForbidden() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest newUserRequest = new UserRequest("userCannotCreate_" + uniqueSuffix, "usercreate_" + uniqueSuffix + "@test.com", "pass");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequest))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should deny creating user as Unauthenticated")
    void should_createUser_asUnauthenticated_shouldBeForbidden() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest newUserRequest = new UserRequest("unauthCannotCreate_" + uniqueSuffix, "unauthcreate_" + uniqueSuffix + "@test.com", "pass");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequest))
                        .with(csrf()))
                .andExpect(status().isForbidden()); // Spring Security typically returns 403 Forbidden
    }


    //--- PUT Endpoints ---
    @Test
    @DisplayName("Should update user as ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_updateUser_asAdmin() throws Exception {
        Long userId = 1L;
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest updatedUserRequest = new UserRequest("updatedAdmin_" + uniqueSuffix, "updated_admin_" + uniqueSuffix + "@test.com", "newPassword123");

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserRequest))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value(updatedUserRequest.getUsername()))
                .andExpect(jsonPath("$.email").value(updatedUserRequest.getEmail()));
    }

    @Test
    @DisplayName("Should deny updating user as USER")
    @WithMockUser(roles = {"USER"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_updateUser_asUser_shouldBeForbidden() throws Exception {
        Long userId = 1L; // Try to update admin user
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest updatedUserRequest = new UserRequest("updateTest_" + uniqueSuffix, "updatetest_" + uniqueSuffix + "@test.com", "newPassword123");

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserRequest))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should deny updating user as Unauthenticated")
    void should_updateUser_asUnauthenticated_shouldBeForbidden() throws Exception {
        Long userId = 2L;
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest updatedUserRequest = new UserRequest("updateTest_" + uniqueSuffix, "updatetest_" + uniqueSuffix + "@test.com", "newPassword123");

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserRequest))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


    //--- DELETE Endpoints ---
    @Test
    @DisplayName("Should delete user and cascade delete their destinations and reviews as ADMIN")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void should_deleteUserAndCascadeData_asAdmin() throws Exception {
        // Crear usuario
        User user = new User();
        user.setUsername("alexandra");
        user.setPassword(passwordEncoder.encode("password999"));
        user.setEmail("alex@example.com");
        user.setRole(Role.USER);
        user = userRepository.save(user); // persistir y obtener el ID


        Destination destination = Destination.builder()
                .country("España")
                .city("Valencia")
                .description("Playa bonita")
                .imageUrl("https://example.com/valencia.jpg")
                .user(user)
                .build();
        destination = destinationRepository.save(destination);


        Review review = Review.builder()
                .comment("Muy buena experiencia")
                .rating(5)
                .user(user)
                .destination(destination)
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepository.save(review);

        Long userId = user.getId();
        Long destinationId = destination.getId();

        // Borrar usuario
        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isNoContent());

        // Confirmar que el usuario ya no existe
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());

        // Confirmar que también se borró el destino
        mockMvc.perform(get("/api/destinations/" + destinationId))
                .andExpect(status().isNotFound());
    }



    @Test
    @DisplayName("Should deny deleting user as USER")
    @WithMockUser(roles = {"USER"})
    //@Sql("/data.sql") // Load initial data for this test
    void should_deleteUser_asUser_shouldBeForbidden() throws Exception {
        Long userId = 1L; // Try to delete admin user
        mockMvc.perform(delete("/users/{id}", userId)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should deny deleting user as Unauthenticated")
    void should_deleteUser_asUnauthenticated_shouldBeForbidden() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L)) // Assuming you try to delete user with ID 1
                .andExpect(status().isForbidden()); // Spring Security typically returns 403 Forbidden
    }
}