package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        User user1 = User.builder().id(1L).username("alexa").email("alexa@example.com").password("pass").build();
        User user2 = User.builder().id(2L).username("momo").email("momo@example.com").password("pass").build();
        List<User> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);
        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(User::getUsername).containsExactly("alexa", "momo");
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void shouldReturnUserById() {
        User user = User.builder().id(1L).username("alexa").email("alexa@example.com").password("pass").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("alexa");
        verify(userRepository, times(1)).findById(1L);

    }

    @Test
    void shouldSaveUser() {
        User user = User.builder().username("alexa").email("alexa@example.com").password("pass").build();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getUsername()).isEqualTo("alexa");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("alexa").email("alexa@example.com").password("pass").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldReturnUserByUsername() {
        User user = User.builder().username("alexa").email("alexa@example.com").password("pass").build();

        when(userRepository.findByUsername("alexa")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("alexa");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("alexa@example.com");
        verify(userRepository, times(1)).findByUsername("alexa");
    }

    @Test
    void shouldReturnUserByEmail() {
        User user = User.builder().username("momo").email("momo@example.com").password("pass").build();
        when(userRepository.findByEmail("momo@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail("momo@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("momo");
        verify(userRepository, times(1)).findByEmail("momo@example.com");

    }
}


