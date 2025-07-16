package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.user.UserRequest;
import com.femcoders.happy_travel.dtos.user.UserResponse;
import com.femcoders.happy_travel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor

    public class UserController {

        private final UserService userService;

        @GetMapping
        public ResponseEntity<List<UserResponse>> getAllUsers() {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }

        @PostMapping
        public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
            UserResponse createdUser = userService.createUser(userRequest);
            return ResponseEntity.ok(createdUser);
        }

        @PutMapping("/{id}")
        public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
            UserResponse updatedUser = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(updatedUser);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
    }
