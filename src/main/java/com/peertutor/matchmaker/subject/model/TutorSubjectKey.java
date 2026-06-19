package com.peertutor.matchmaker.subject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * A composite key class for the TutorSubject entity.
 * This is required for the many-to-many join table.
 */
@Embeddable
public class TutorSubjectKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "subject_id")
    private Long subjectId;

    // --- No Lombok ---

    public TutorSubjectKey() {
    }

    public TutorSubjectKey(Long userId, Long subjectId) {
        this.userId = userId;
        this.subjectId = subjectId;
    }

    // --- Getters and Setters ---
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    // --- equals() and hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorSubjectKey that = (TutorSubjectKey) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, subjectId);
    }
}
