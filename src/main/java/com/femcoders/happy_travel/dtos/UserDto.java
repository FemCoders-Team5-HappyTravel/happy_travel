package com.femcoders.happy_travel.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
    private boolean enabled;
}
