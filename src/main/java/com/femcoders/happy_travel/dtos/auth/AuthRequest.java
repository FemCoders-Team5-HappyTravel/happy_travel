package com.femcoders.happy_travel.dtos.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AuthRequest {
    @Email
    private String email;

    private String password;
}
