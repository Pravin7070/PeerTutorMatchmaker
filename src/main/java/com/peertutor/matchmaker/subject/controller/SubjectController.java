package com.peertutor.matchmaker.subject.controller;

import com.peertutor.matchmaker.subject.model.Subject;
import com.peertutor.matchmaker.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * API: GET /api/subjects
     * Objective: Get a list of all available subjects.
     * Access: Public (so students can see them)
     */
    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    
    // Note: You would later add an admin-only endpoint
    // to create new subjects for tutors to choose from.
}
