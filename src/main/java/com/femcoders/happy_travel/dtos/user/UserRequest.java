package com.femcoders.happy_travel.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // genera constructor vacío (obligatorio para Spring)
@AllArgsConstructor // genera constructor con los 3 campos
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
