package com.peertutor.matchmaker.subject.repository;

import com.peertutor.matchmaker.subject.model.TutorSubject;
import com.peertutor.matchmaker.subject.model.TutorSubjectKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TutorSubjectRepository extends JpaRepository<TutorSubject, TutorSubjectKey> {
    
    // Custom query to find all subjects for a specific tutor
    List<TutorSubject> findByUser_Id(Long userId);
    
    // Custom query to find all tutors who teach a specific subject
    List<TutorSubject> findBySubject_Id(Long subjectId);
}
