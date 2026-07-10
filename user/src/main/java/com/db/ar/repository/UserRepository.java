package com.db.ar.repository;

import com.db.ar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailEqualsIgnoreCase(String email);

    boolean existsByEmailEqualsIgnoreCaseAndIdNot(String email, Long id);
}
