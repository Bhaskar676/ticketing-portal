package com.ticketingportal.controller;

import com.ticketingportal.model.User;
import com.ticketingportal.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null)); // remove password from response
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setPassword(null); // remove password from response
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        user.setPassword(null); // remove password from response
        return ResponseEntity.ok(user);
    }
    @GetMapping("/location/{location}")
    public ResponseEntity<List<User>> getUsersByLocation(@PathVariable String location) {
        List<User> users = userRepository.findByLocationIgnoreCase(location);
        users.forEach(user -> user.setPassword(null)); // remove password from response
        return ResponseEntity.ok(users);
    }
    @GetMapping("/search/username")
    public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam String query) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(query);
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }
    @GetMapping("/search/email")
    public ResponseEntity<List<User>> searchUsersByEmail(@RequestParam String query) {
        List<User> users = userRepository.findByEmailContainingIgnoreCase(query);
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }
    @GetMapping("/search/location")
    public ResponseEntity<List<User>> searchUsersByLocation(@RequestParam String query) {
        List<User> users = userRepository.findByLocationContainingIgnoreCase(query);
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }

    /*  Implemented using auth
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userRepository.save(user);
            savedUser.setPassword(null); // remove password from response
            return ResponseEntity.ok(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Username or Email already exists!");
        }
    }
    */
}
