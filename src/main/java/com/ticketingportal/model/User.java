package com.ticketingportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 15, message = "Phone number cannot exceed 15 digits")
    private String phoneNumber;

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "USER|ADMIN|ORGANIZER", message = "Role must be USER, ADMIN, or ORGANIZER")
    private String role;

    @NotBlank(message = "Location is mandatory")
    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Size(max = 500, message = "Profile picture URL cannot exceed 500 characters")
    private String profilePictureUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isActive;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    private LocalDateTime lastLoginAt;

    // Default constructor
    public User() {}

    // Updated constructor
    public User(String username,
                String password,
                String email,
                String phoneNumber,
                String role,
                String location,
                String profilePictureUrl,
                boolean isActive,
                String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.location = location;
        this.profilePictureUrl = profilePictureUrl;
        this.isActive = isActive;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
