package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.user.UserRequest;
import com.femcoders.happy_travel.dtos.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserRequest request);
    UserResponse updateUserRole(Long id, String role);
    void deleteUser(Long id);
    Page<UserResponse>getUsersPage(Pageable pageable);
}


