package com.ticketingportal.controller;

import com.ticketingportal.dto.LoginRequest;
import com.ticketingportal.dto.SignUpRequest;
import com.ticketingportal.model.User;
import com.ticketingportal.repository.UserRepository;
import com.ticketingportal.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional;

        if (loginRequest.getLoginIdentifier().contains("@")) {
            // Try email
            userOptional = userRepository.findByEmail(loginRequest.getLoginIdentifier());
        } else {
            // Assume it's a phone number
            userOptional = userRepository.findByPhoneNumber(loginRequest.getLoginIdentifier());
        }

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    @PostMapping("/signup")
    public User signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        if (userRepository.findByPhoneNumber(signUpRequest.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists!");
        }

        User newUser = new User();
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        newUser.setPhoneNumber(signUpRequest.getPhoneNumber());
        newUser.setLocation(signUpRequest.getLocation());
        newUser.setProfilePictureUrl(signUpRequest.getProfilePictureUrl());
        newUser.setAddress(signUpRequest.getAddress());
        newUser.setRole("USER");
        newUser.setActive(true);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(newUser);
    }
}
