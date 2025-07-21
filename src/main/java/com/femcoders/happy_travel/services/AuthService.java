package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.auth.AuthRequest;
import com.femcoders.happy_travel.dtos.auth.AuthResponse;
import com.femcoders.happy_travel.dtos.auth.RegisterRequest;
import com.femcoders.happy_travel.models.Role;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.UserRepository;
import com.femcoders.happy_travel.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtils.generateToken((UserDetails) user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken((UserDetails) user);
        return new AuthResponse(token);
    }
}

