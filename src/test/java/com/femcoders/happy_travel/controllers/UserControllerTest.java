// Este test utiliza la base de datos H2 configurada en application-test.properties
package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.auth.RegisterRequest;
import com.femcoders.happy_travel.dtos.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.happy_travel.models.Role;
import com.femcoders.happy_travel.models.User;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = com.femcoders.happy_travel.HappyTravelApplication.class)

@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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

        String responseContent = mockMvc.perform(post("/api/auth/login")
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
        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7))) // Verify initial size if data is consistent
                .andExpect(jsonPath("$.[0].username").value("sonara"))
                .andExpect(jsonPath("$.[0].email").value("sonara09@gmail.com"));
    }

    @Test
    @DisplayName("Should get all users as USER")
    @WithMockUser(roles = {"USER"})
    @Sql("/data.sql") // Load initial data for this test
    void should_getAllUsers_asUser() throws Exception {
        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    @DisplayName("Should deny getting all users as Unauthenticated")
    void should_getAllUsers_asUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Or isForbidden() if JWT filter lets it pass but role check fails later
    }

    @Test
    @DisplayName("Should get user by ID as ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    @Sql("/data.sql") // Load initial data for this test
    void should_getUserById_asAdmin() throws Exception {
        Long userId = 5L; // Assuming this user exists in your test data

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mike_wilson"))
                .andExpect(jsonPath("$.email").value("mike@example.com"));
    }

    @Test
    @DisplayName("Should get user by ID as USER")
    @WithMockUser(roles = {"USER"})
    @Sql("/data.sql") // Load initial data for this test
    void should_getUserById_asUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Less specific check, just verify access
    }

    @Test
    @DisplayName("Should deny getting user by ID as Unauthenticated")
    void should_getUserById_asUnauthenticated() throws Exception {
        Long userId = 5L;
        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    //--- POST Endpoints ---
    @Test
    @DisplayName("Should register user via Auth endpoint")
    void should_registerUser_viaAuthEndpoint() throws Exception {
        // 1. Create a RegisterRequest object with unique data
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser_register_" + uniqueSuffix); // Provide a unique username
        registerRequest.setEmail("register_" + uniqueSuffix + "@example.com"); // Provide a unique email
        registerRequest.setPassword("securepassword123"); // Provide a valid password

        String requestBody = objectMapper.writeValueAsString(registerRequest);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("Should create user as ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    @Sql("/data.sql") // Load initial data for this test
    void should_createUser_asAdmin() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest newUserRequest = new UserRequest("adminCreatedUser_" + uniqueSuffix, "admincreate_" + uniqueSuffix + "@test.com", "securePass");

        mockMvc.perform(post("/api/users") // Endpoint for admin to create user via UserController
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
    @Sql("/data.sql") // Load initial data for this test
    void should_createUser_asUser_shouldBeForbidden() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest newUserRequest = new UserRequest("userCannotCreate_" + uniqueSuffix, "usercreate_" + uniqueSuffix + "@test.com", "pass");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequest))
                        .with(csrf()))
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
    @Sql("/data.sql") // Load initial data for this test
    void should_updateUser_asAdmin() throws Exception {
        Long userId = 1L; // Update the 'admin' user (or another existing user)
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest updatedUserRequest = new UserRequest("updatedAdmin_" + uniqueSuffix, "updated_admin_" + uniqueSuffix + "@test.com", "newPassword123");

        mockMvc.perform(put("/api/users/{id}", userId)
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
    @Sql("/data.sql") // Load initial data for this test
    void should_updateUser_asUser_shouldBeForbidden() throws Exception {
        Long userId = 1L; // Try to update admin user
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest updatedUserRequest = new UserRequest("updateTest_" + uniqueSuffix, "updatetest_" + uniqueSuffix + "@test.com", "newPassword123");

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserRequest))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should deny updating user as Unauthenticated")
    void should_updateUser_asUnauthenticated_shouldBeForbidden() throws Exception {
        Long userId = 2L; // Update an existing user
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest updatedUserRequest = new UserRequest("updateTest_" + uniqueSuffix, "updatetest_" + uniqueSuffix + "@test.com", "newPassword123");

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserRequest))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


    //--- DELETE Endpoints ---
    @AutoConfigureMockMvc(addFilters = false)
    @Test
    @DisplayName("Should delete a user as ADMIN")
    @Sql("/data.sql") // Load initial data for this test (ensures admin exists)
    void should_deleteUser_asAdmin() throws Exception {
        // 1. Obtener un token JWT válido para el usuario ADMIN ya existente en data.sql
        // Usamos el email "admin@happytravel.com" porque UserDetailsServiceImpl busca por email.
        UserDetails adminUserDetails = userDetailsService.loadUserByUsername("admin@happytravel.com");
        // Generamos el token pasando el objeto UserDetails completo
        String adminJwtToken = jwtUtils.generateToken(adminUserDetails);

        // 2. Crear manualmente un usuario para eliminar solo para esta prueba.
        // Usa un nombre de usuario/correo electrónico único para evitar DataIntegrityViolationException
        User userToDeleteEntity = new User();
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        userToDeleteEntity.setUsername("userToDelete_" + uniqueSuffix);
        userToDeleteEntity.setEmail("delete_me_" + uniqueSuffix + "@example.com");
        userToDeleteEntity.setPassword(passwordEncoder.encode("1234")); // Encriptar la contraseña
        userToDeleteEntity.setRole(Role.USER); // Asignar un rol
        User savedUser = userRepository.save(userToDeleteEntity); // Guardar el usuario en la base de datos
        Long userIdToDelete = savedUser.getId();

        // Optional: Verify the user was saved (for debugging)
        assertTrue(userRepository.findById(userIdToDelete).isPresent(), "El usuario debería existir antes del intento de eliminación.");

        // 3. Realizar la eliminación con el token JWT real
        mockMvc.perform(delete("/api/users/{id}", userIdToDelete)
                        .header("Authorization", "Bearer " + adminJwtToken) // Usar el token real
                        .with(csrf())) // Añadir token CSRF
                .andExpect(status().isNoContent()); // 204 No Content es común para una eliminación exitosa

        // 4. Verificar que el usuario realmente fue eliminado
        boolean exists = userRepository.existsById(userIdToDelete);
        assertFalse(exists, "El usuario debería haber sido eliminado.");
    }


    @Test
    @DisplayName("Should deny deleting user as USER")
    @WithMockUser(roles = {"USER"})
    @Sql("/data.sql") // Load initial data for this test
    void should_deleteUser_asUser_shouldBeForbidden() throws Exception {
        Long userId = 1L; // Try to delete admin user
        mockMvc.perform(delete("/api/users/{id}", userId)
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