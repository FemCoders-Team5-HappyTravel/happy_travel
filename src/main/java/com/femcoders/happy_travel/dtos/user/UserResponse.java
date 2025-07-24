package com.femcoders.happy_travel.dtos.user;

import com.femcoders.happy_travel.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserResponse(long L, String mail, String test) {
    }
}
