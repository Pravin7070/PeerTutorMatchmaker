package com.peertutor.matchmaker.user.dto;

import com.peertutor.matchmaker.user.model.User;
import com.peertutor.matchmaker.user.model.UserRole;

/**
 * DTO for sending basic authenticated user information back to the client (/api/auth/me).
 * This DTO ensures the 'role' field is included in the JSON response.
 */
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    // 🌟 FIX: Include the role field
    private UserRole role; 

    public UserResponseDTO() {
    }

    // Convenience constructor to map from the User entity
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole(); // Map the role from the entity
    }

    // --- Getters and Setters (Required for JSON serialization) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}