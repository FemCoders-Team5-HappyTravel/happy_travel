package com.femcoders.happy_travel.dtos;

import com.femcoders.happy_travel.models.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
