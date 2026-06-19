package com.peertutor.matchmaker.subject.service;

import com.peertutor.matchmaker.subject.dto.AvailabilityDTO;
import com.peertutor.matchmaker.subject.model.Availability;
import com.peertutor.matchmaker.subject.model.Subject;
import com.peertutor.matchmaker.subject.repository.AvailabilityRepository;
import com.peertutor.matchmaker.subject.repository.SubjectRepository;
import com.peertutor.matchmaker.user.model.User;
// 🌟 FIX: Ensure this import is present, as UserRole is used for validation
import com.peertutor.matchmaker.user.model.UserRole; 
import com.peertutor.matchmaker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, 
                          AvailabilityRepository availabilityRepository,
                          UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    // --- Subject Logic ---

    @Transactional(readOnly = true)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // --- Availability Logic ---

    @Transactional
    public Availability addAvailability(Long tutorId, AvailabilityDTO dto) {
        // 1. Validation: Find tutor and ensure role is TUTOR
        User tutor = userRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor not found with id: " + tutorId));
        
        if (!tutor.getRole().equals(UserRole.ROLE_TUTOR)) {
             throw new RuntimeException("Only users with the TUTOR role can set availability.");
        }
        
        // 2. Validation: Ensure start time is before end time
        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
             throw new RuntimeException("Start time must be before end time.");
        }

        // 3. Create and Save
        Availability newSlot = new Availability();
        newSlot.setTutor(tutor); 
        newSlot.setDayOfWeek(dto.getDayOfWeek());
        newSlot.setStartTime(dto.getStartTime());
        newSlot.setEndTime(dto.getEndTime());
        
        return availabilityRepository.save(newSlot);
    }

    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getAvailabilityForTutor(Long tutorId) {
        // Fetch slots using the correct repository method (findByTutor_Id)
        List<Availability> slots = availabilityRepository.findByTutor_Id(tutorId);
        
        if (slots.isEmpty()) {
             return Collections.emptyList();
        }
        
        // FIX: Sort the slots by DayOfWeek using Java's built-in chronological order
        slots.sort((a, b) -> a.getDayOfWeek().compareTo(b.getDayOfWeek()));
        
        // Convert the list of Availability entities to a list of AvailabilityDTOs
        return slots.stream()
                .map(slot -> new AvailabilityDTO(
                        slot.getId(), 
                        slot.getDayOfWeek(), 
                        slot.getStartTime(), 
                        slot.getEndTime()))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteAvailability(Long slotId, Long tutorId) {
        // Find the slot
        Availability slot = availabilityRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Availability slot not found"));
        
        // Security Check: Ensure the person deleting the slot is the tutor who owns it
        if (!slot.getTutor().getId().equals(tutorId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own availability slots");
        }
        
        availabilityRepository.delete(slot);
    }
}