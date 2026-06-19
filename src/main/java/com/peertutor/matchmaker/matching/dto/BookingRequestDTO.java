package com.peertutor.matchmaker.matching.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for handling the booking request body from the frontend.
 * This DTO receives the availability slot ID and the student ID making the booking.
 */
public class BookingRequestDTO implements Serializable {

    private Long availabilityId;
    private Long studentId;

    // Default constructor
    public BookingRequestDTO() {
    }

    // Constructor with fields
    public BookingRequestDTO(Long availabilityId, Long studentId) {
        this.availabilityId = availabilityId;
        this.studentId = studentId;
    }

    // --- Getters and Setters ---

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Optional: toString, equals, hashCode methods can be added for completeness
}
