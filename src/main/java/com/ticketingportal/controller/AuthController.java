package com.ticketingportal.controller;

import com.ticketingportal.dto.LoginRequest;
import com.ticketingportal.dto.SignUpRequest;
import  com.ticketingportal.model.User;
import  com.ticketingportal.repository.UserRepository;
import  com.ticketingportal.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        Optional<User> userOptional = userRepository.findAll()
                .stream()
                .filter(u -> u.getUsername().equals((loginRequest.getUsername())))
                .findFirst();

        if (userOptional.isEmpty()){
            throw new RuntimeException(("User not found"));
        }

        User user = userOptional.get();
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException(("Invalid password"));
        }

        return jwtUtil.generateToken((user.getUsername()));

    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignUpRequest signUpRequest){
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        User newUser = new User();
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        newUser.setRole("USER");

        return userRepository.save(newUser);

    }
}
