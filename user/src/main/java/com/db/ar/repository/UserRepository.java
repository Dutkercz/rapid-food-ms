package com.db.ar.repository;

import com.db.ar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmailEqualsIgnoreCase(String email);

    boolean existsByEmailEqualsIgnoreCaseAndIdNot(String email, UUID id);
}
