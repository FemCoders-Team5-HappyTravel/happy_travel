package com.femcoders.happy_travel.mappers;

import com.femcoders.happy_travel.dtos.UserDto;
import com.femcoders.happy_travel.models.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void shouldMapUserToDto() {
        User user = User.builder()
                .id(1L)
                .username("pico")
                .email("picopico@example.com")
                .roles(Set.of("USER"))
                .enabled(true)
                .build();

        UserDto dto = UserMapper.toDto(user);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getUsername()).isEqualTo("pico");
        assertThat(dto.getEmail()).isEqualTo("picopico@example.com");
        assertThat(dto.getRoles()).containsExactly("USER");
        assertThat(dto.isEnabled()).isTrue();
    }

    @Test
    void shouldMapDtoToUser() {
        UserDto dto = UserDto.builder()
                .id(2L)
                .username("momo")
                .email("momo@example.com")
                .roles(Set.of("ADMIN"))
                .enabled(false)
                .build();

        User user = UserMapper.toEntity(dto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getUsername()).isEqualTo("momo");
        assertThat(user.getEmail()).isEqualTo("momo@example.com");
        assertThat(user.getRoles()).containsExactly("ADMIN");
        assertThat(user.isEnabled()).isFalse();
    }

    @Test
    void shouldReturnNullWhenUserIsNull() {
        assertThat(UserMapper.toDto(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenDtoIsNull() {
        assertThat(UserMapper.toEntity(null)).isNull();
    }
}




