package com.femcoders.happy_travel.services;

import com.femcoders.happy_travel.dtos.UserRequest;
import com.femcoders.happy_travel.dtos.UserResponse;
import com.femcoders.happy_travel.models.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);

    User getCurrentAuthenticatedUser(); // Añadido para por May
}
