package com.peertutor.matchmaker.subject.repository;

import com.peertutor.matchmaker.subject.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    /**
     * Finds all Availability slots associated with a specific Tutor ID.
     * This method uses the JPA naming convention: findBy{RelationshipName}_{RelationshipField}.
     * It requires the Availability entity to have a field named 'tutor'.
     *
     * @param tutorId The ID of the Tutor (User) whose schedule is being retrieved.
     * @return List of Availability entities.
     */
    List<Availability> findByTutor_Id(Long tutorId);
    
    // NOTE: Any methods here using numeric comparisons (e.g., findByDayOfWeekGreaterThan) 
    // must be removed/commented out to prevent the 'character varying >= integer' error.
}