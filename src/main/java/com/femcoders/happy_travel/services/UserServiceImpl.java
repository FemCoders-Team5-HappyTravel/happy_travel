package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.UserRequest;
import com.femcoders.happy_travel.dtos.UserResponse;
import com.femcoders.happy_travel.dtos.UserMapper;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserRequest request) {
        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       // user.getRoles().add("USER");
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return UserMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public User getCurrentAuthenticatedUser() { // ingresado por may para añadir este metodo
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Manejar caso donde no hay usuario autenticado (ej. lanzar una excepción)
            throw new RuntimeException("No user is currently authenticated.");
        }

        // Dependiendo de cómo configures Spring Security y JWT,
        // el principal puede ser un UserDetails object, o directamente tu User.
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            // Si tu UserDetails es el mismo User de tu modelo, puedes castearlo directamente.
            // O si solo tienes el username, tendrías que buscarlo en el repositorio.
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username) // Asumiendo que tienes un findByUsername en UserRepository
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in database."));
        } else if (principal instanceof String) {
            // Caso donde el principal es solo el username como String (menos común con JWT completo)
            String username = (String) principal;
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in database."));
        }
        // Puedes añadir más casos si el principal es de otro tipo (ej. tu propio User object directamente)
        // O si tu token JWT ya incluye el ID del usuario, podrías buscar por ID.

        throw new RuntimeException("Could not retrieve authenticated user from security context.");
    }
}
