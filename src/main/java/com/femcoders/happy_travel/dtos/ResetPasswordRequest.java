package com.femcoders.happy_travel.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @Schema(description = "Password reset token", example = "1234567890abcdef1234567890abcdef")
    private String token;

    @Schema(description = "New password", example = "MyNewSecurePass123!")
    private String newPassword;
}
