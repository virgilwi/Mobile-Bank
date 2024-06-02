package com.example.mobilebank.service;

import com.example.mobilebank.entity.User;
import com.example.mobilebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all users.
     * @return list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by id.
     * @param id the id of the user
     * @return the user if found, else Optional.empty()
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Add a new user.
     * @param user the user to be added
     * @return the added user
     */
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Update an existing user.
     * @param id the id of the user to be updated
     * @param user the user details to be updated
     * @return the updated user
     */
    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Delete a user.
     * @param id the id of the user to be deleted
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}