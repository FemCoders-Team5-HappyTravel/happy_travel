package com.femcoders.happy_travel.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UpdateUserRoleRequest {
    private String role;
}
