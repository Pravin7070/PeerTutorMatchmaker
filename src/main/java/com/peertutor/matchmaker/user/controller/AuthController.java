package com.peertutor.matchmaker.user.controller;

import com.peertutor.matchmaker.user.dto.RegisterUserDTO;
import com.peertutor.matchmaker.user.model.User;
import com.peertutor.matchmaker.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * API: POST /api/auth/register
     * Objective: Register a new student or tutor.
     * Access: Public
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDTO registerDto) {
        try {
            User registeredUser = userService.registerNewUser(registerDto);
            // Don't return the full user object, just a success message or a UserDTO
            return new ResponseEntity<>("User registered successfully! ID: " + registeredUser.getId(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // This will be caught by our GlobalExceptionHandler
            throw e; 
        }
    }

    /**
     * API: GET /api/auth/me
     * Objective: A simple endpoint to test if authentication is working.
     * Access: Authenticated
     */
    @GetMapping("/me")
    public ResponseEntity<String> getMyDetails(Principal principal) {
        // 'principal' is injected by Spring Security and contains the authenticated user's name
        return ResponseEntity.ok("You are logged in as: " + principal.getName());
    }
}