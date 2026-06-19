package com.peertutor.matchmaker.user.repository;

import com.peertutor.matchmaker.user.model.User;
import com.peertutor.matchmaker.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Make sure to import this
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA automatically creates the query from the method name
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // --- ADD THIS NEW METHOD ---
    List<User> findByRole(UserRole role);
}
