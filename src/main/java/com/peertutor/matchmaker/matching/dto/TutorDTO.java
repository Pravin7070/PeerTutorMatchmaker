package com.peertutor.matchmaker.matching.dto;

import com.peertutor.matchmaker.user.model.User;
import com.peertutor.matchmaker.user.model.UserRole;

/**
 * A DTO for sending public Tutor information to the frontend.
 * We do NOT want to send the password.
 */
public class TutorDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private UserRole role;
    // We can add more fields later, like averageRating or subjects

    // --- No Lombok ---

    public TutorDTO() {
    }

    public TutorDTO(Long id, String username, String firstName, String lastName, String email, String bio, UserRole role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.role = role;
    }

    /**
     * A helper constructor to easily convert a User entity to a TutorDTO
     */
    public TutorDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.bio = user.getBio();
        this.role = user.getRole();
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
