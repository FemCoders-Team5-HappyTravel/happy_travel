package com.femcoders.happy_travel.mappers;
import com.femcoders.happy_travel.dtos.UserMapper;
import com.femcoders.happy_travel.dtos.UserRequest;
import com.femcoders.happy_travel.dtos.UserResponse;
import com.femcoders.happy_travel.models.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {
    void toEntity_shouldMapRequestToEntity() {
        UserRequest request = new UserRequest();
        request.setUsername("alex");
        request.setEmail("alex@example.com");
        request.setPassword("pwd");

        User entity = UserMapper.toEntity(request);

        assertThat(entity.getUsername()).isEqualTo("alex");
        assertThat(entity.getEmail()).isEqualTo("alex@example.com");
        assertThat(entity.getPassword()).isEqualTo("pwd");
    }

    @Test
    void toResponse_shouldMapEntityToResponse() {
        User user = new User();
        user.setId(1L);
        user.setUsername("alex");
        user.setEmail("alex@example.com");
        user.getRoles().add("USER");

        UserResponse response = UserMapper.toResponse(user);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).isEqualTo("alex");
        assertThat(response.getEmail()).isEqualTo("alex@example.com");
        assertThat(response.getRoles()).contains("USER");
    }
}