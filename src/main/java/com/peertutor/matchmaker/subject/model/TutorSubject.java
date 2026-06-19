package com.peertutor.matchmaker.subject.model;

import com.peertutor.matchmaker.user.model.User;
import jakarta.persistence.*;

/**
 * This is the join table entity between a User (Tutor) and a Subject.
 * It stores the skill level for that specific subject.
 */
@Entity
@Table(name = "tutor_subjects")
public class TutorSubject {

    @EmbeddedId
    private TutorSubjectKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // Maps the 'userId' field from TutorSubjectKey
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId") // Maps the 'subjectId' field from TutorSubjectKey
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "skill_level", length = 50)
    private String skillLevel; // e.g., "Beginner", "Intermediate", "Expert"

    // --- No Lombok ---

    public TutorSubject() {
    }

    public TutorSubject(User user, Subject subject, String skillLevel) {
        this.id = new TutorSubjectKey(user.getId(), subject.getId());
        this.user = user;
        this.subject = subject;
        this.skillLevel = skillLevel;
    }

    // --- Getters and Setters ---
    public TutorSubjectKey getId() {
        return id;
    }

    public void setId(TutorSubjectKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}
