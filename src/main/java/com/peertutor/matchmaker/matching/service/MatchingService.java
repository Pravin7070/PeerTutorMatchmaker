package com.peertutor.matchmaker.matching.service;

import com.peertutor.matchmaker.matching.dto.TutorDTO;
import com.peertutor.matchmaker.user.model.User;
import com.peertutor.matchmaker.user.model.UserRole;
import com.peertutor.matchmaker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    private final UserRepository userRepository;
    // We will inject other repositories (TutorSubjectRepository, AvailabilityRepository)
    // here later as we add more filtering logic.

    @Autowired
    public MatchingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The main logic to find tutors.
     * For now, it just finds all users with the role 'ROLE_TUTOR'.
     */
    @Transactional(readOnly = true)
    public List<TutorDTO> findAllTutors() {
        // 1. Find all users who are tutors
        List<User> tutors = userRepository.findByRole(UserRole.ROLE_TUTOR);
        
        // 2. Convert the User entities to TutorDTOs
        return tutors.stream()
                .map(TutorDTO::new) // Uses the constructor that takes a User
                .collect(Collectors.toList());
    }

    // Later, we will add more complex methods like:
    // public List<TutorDTO> findTutorsBySubject(Long subjectId) { ... }
    
    // public List<TutorDTO> findTutorsByAvailability(Long subjectId, DayOfWeek day, LocalTime time) { ... }
}
