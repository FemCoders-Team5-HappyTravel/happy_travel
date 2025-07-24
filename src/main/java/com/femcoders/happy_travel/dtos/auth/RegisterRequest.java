package com.femcoders.happy_travel.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor; // Add this if you don't have a constructor

@Data
@NoArgsConstructor // Often useful for DTOs with no-arg constructor for JSON deserialization
public class RegisterRequest {
    @NotBlank(message = "Username cannot be empty") // Added custom message for clarity
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") // Good practice to add size
    private String username; // <-- Changed from userName to username

    @Email(message = "Email should be valid") // Added custom message
    @NotBlank(message = "Email cannot be empty") // Added custom message
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long") // Added custom message
    @NotBlank(message = "Password cannot be empty") // Passwords should also be not blank
    private String password;

    // Remove the manually added public void setUsername(String testuserRegister) {}
    // Lombok's @Data will generate the correct setters and getters based on 'username', 'email', 'password'.
}
