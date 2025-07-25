package com.femcoders.happy_travel.dtos.user;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
