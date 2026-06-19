package com.peertutor.matchmaker.matching.controller;

import com.peertutor.matchmaker.matching.dto.TutorDTO;
import com.peertutor.matchmaker.matching.dto.BookingRequestDTO; // 🌟 NEW IMPORT
import com.peertutor.matchmaker.matching.service.MatchingService;
import com.peertutor.matchmaker.matching.service.BookingService; // 🌟 NEW IMPORT (Need to create this Service)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/match")
// Ensure CORS is enabled if not globally configured
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class MatchingController {

    private final MatchingService matchingService;
    private final BookingService bookingService; // 🌟 NEW DEPENDENCY

    @Autowired
    public MatchingController(MatchingService matchingService, BookingService bookingService) {
        this.matchingService = matchingService;
        this.bookingService = bookingService;
    }

    /**
     * API: GET /api/match/tutors
     * Objective: Find all available tutors.
     */
    @GetMapping("/tutors")
    public ResponseEntity<List<TutorDTO>> getAllTutors() {
        List<TutorDTO> tutors = matchingService.findAllTutors();
        return ResponseEntity.ok(tutors);
    }
    
    // ----------------------------------------------------------------------
    // 🌟 FIX: NEW ENDPOINT TO HANDLE BOOKING REQUESTS (Fixes the 404 error)
    // ----------------------------------------------------------------------
    /**
     * API: POST /api/match/book
     * Objective: Creates a new booking session when the user clicks "Book Now."
     * Access: Authenticated
     */
    @PostMapping("/book")
    public ResponseEntity<?> bookSlot(Principal principal, @RequestBody BookingRequestDTO bookingRequest) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User must be logged in to book.");
        }
        
        // Use the authenticated username to verify/fetch the Student's ID
        String studentUsername = principal.getName();
        
        try {
            // Service layer handles fetching IDs, validation, and saving the booking
            Long bookingId = bookingService.createBooking(studentUsername, bookingRequest.getAvailabilityId());

            return new ResponseEntity<>("Booking successful! Booking ID: " + bookingId, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Catches validation errors (e.g., slot taken, user not found)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}