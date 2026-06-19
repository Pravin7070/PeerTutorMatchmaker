package com.peertutor.matchmaker.subject.controller;

import com.peertutor.matchmaker.subject.dto.AvailabilityDTO;
import com.peertutor.matchmaker.subject.model.Availability;
import com.peertutor.matchmaker.subject.service.SubjectService;
import com.peertutor.matchmaker.user.model.User;
import com.peertutor.matchmaker.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
// FIX: Set base path to /api/match to correctly handle API structure
@RequestMapping("/api/match")
// FIX: Add CORS support for frontend (adjust port 3000 if necessary)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class AvailabilityController {

    private final SubjectService subjectService;
    private final UserService userService;

    @Autowired
    public AvailabilityController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService;
        this.userService = userService;
    }

    // --- 1. PUBLIC VIEW ENDPOINT (Fixes the 404 error from the frontend) ---
    /**
     * API: GET /api/match/tutors/{tutorId}/availability
     * Objective: Get all availability slots for a specific tutor.
     * Access: Authenticated (Student/Any Logged-in User)
     */
    @GetMapping("/tutors/{tutorId}/availability") 
    public ResponseEntity<List<AvailabilityDTO>> getTutorAvailability(@PathVariable Long tutorId) {
        // This relies on SubjectService having the method getAvailabilityForTutor(Long tutorId)
        List<AvailabilityDTO> slots = subjectService.getAvailabilityForTutor(tutorId);
        return ResponseEntity.ok(slots);
    }
    // ------------------------------------------------------------------------


    // --- 2. TUTOR MANAGEMENT ENDPOINTS ---

    /**
     * API: POST /api/match/tutors/availability
     * Objective: Add a new availability slot (for the logged-in tutor).
     * Access: Authenticated (Tutor)
     */
    @PostMapping("/tutors/availability")
    public ResponseEntity<?> addAvailability(Principal principal, @Valid @RequestBody AvailabilityDTO dto) {
        User tutor = getAuthenticatedTutor(principal);
        
        try {
            Availability newSlot = subjectService.addAvailability(tutor.getId(), dto);
            
            // FIX: Ensure responseDto is defined in scope before the return statement
            AvailabilityDTO responseDto = new AvailabilityDTO(
                newSlot.getId(), 
                newSlot.getDayOfWeek(), 
                newSlot.getStartTime(), 
                newSlot.getEndTime()
            );
            
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * API: GET /api/match/tutors/availability/me
     * Objective: Get all availability slots for the logged-in tutor.
     * Access: Authenticated (Tutor)
     */
    @GetMapping("/tutors/availability/me")
    public ResponseEntity<List<AvailabilityDTO>> getMyAvailability(Principal principal) {
        User tutor = getAuthenticatedTutor(principal);
        // This relies on SubjectService having the method getAvailabilityForTutor(Long tutorId)
        List<AvailabilityDTO> slots = subjectService.getAvailabilityForTutor(tutor.getId());
        return ResponseEntity.ok(slots);
    }

    /**
     * API: DELETE /api/match/tutors/availability/{id}
     * Objective: Delete an availability slot by its ID.
     * Access: Authenticated (Tutor)
     */
    @DeleteMapping("/tutors/availability/{id}")
    public ResponseEntity<?> deleteAvailability(@PathVariable Long id, Principal principal) {
        User tutor = getAuthenticatedTutor(principal);
        
        try {
            subjectService.deleteAvailability(id, tutor.getId());
            return ResponseEntity.ok("Availability slot deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Helper method to get the User entity from the security principal.
     */
    private User getAuthenticatedTutor(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User must be logged in");
        }
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }
}