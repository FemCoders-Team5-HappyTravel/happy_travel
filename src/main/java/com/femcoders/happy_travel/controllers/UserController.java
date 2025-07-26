package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.user.UserRequest;
import com.femcoders.happy_travel.dtos.user.UserResponse;
import com.femcoders.happy_travel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

        @RestController
        @RequestMapping("/users")
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
        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
            UserResponse createdUser = userService.createUser(userRequest);
            return ResponseEntity.ok(createdUser);
        }
        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
            UserResponse updatedUser = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(updatedUser);
        }
        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
            @GetMapping("/page")
            @PreAuthorize("hasRole('ADMIN')")
            public ResponseEntity<Page<UserResponse>> getUsersPage(
                    @PageableDefault(size = 10, sort = "id") Pageable pageable) {
                return ResponseEntity.ok(userService.getUsersPage(pageable));
            }
    }
