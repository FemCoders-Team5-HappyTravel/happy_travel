package com.femcoders.happy_travel.dtos.user;

import com.femcoders.happy_travel.models.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
