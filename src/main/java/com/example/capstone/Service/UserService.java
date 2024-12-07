package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.User;
import com.example.capstone.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ApiException("Error: User not found"));
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        if (!userRepository.existsById(id)) throw new ApiException("Error: User not found");
        user.setId(id);
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) throw new ApiException("Error: User not found");
        userRepository.deleteById(id);
    }
}