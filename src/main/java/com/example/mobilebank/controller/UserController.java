package com.example.mobilebank.controller;

import com.example.mobilebank.entity.User;
import com.example.mobilebank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users.
     * @return list of users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Get a user by id.
     * @param id the id of the user
     * @return the user if found
     */
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Add a new user.
     * @param user the user to be added
     * @return the added user
     */
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Update an existing user.
     * @param id the id of the user to be updated
     * @param user the user details to be updated
     * @return the updated user
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    /**
     * Delete a user.
     * @param id the id of the user to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}