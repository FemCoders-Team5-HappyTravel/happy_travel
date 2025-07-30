package com.femcoders.happy_travel.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @Schema(description = "Email of the user requesting password reset", example = "user@example.com")
    private String email;
}
