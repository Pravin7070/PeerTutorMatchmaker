package com.peertutor.matchmaker.subject.repository;

import com.peertutor.matchmaker.subject.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
