package com.femcoders.happy_travel.services;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User>getAllUsers() {
        return userRepository.findAll();

    }

    public Optional<User>getUserById(Long id) {
        return userRepository.findById(id);
    }
    public User saveUser(User user) {
        //aquí cifraremos password antes de guardar
        return userRepository.save(user);
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User>getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }

    }




}




