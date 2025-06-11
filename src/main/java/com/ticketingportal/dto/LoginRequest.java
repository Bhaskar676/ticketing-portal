package com.ticketingportal.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Login identifier is mandatory (email or phone)")
    private String loginIdentifier;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
