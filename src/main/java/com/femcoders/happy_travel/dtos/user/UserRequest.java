package com.femcoders.happy_travel.dtos.user;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
