package com.db.ar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private Boolean active;

    @Column(nullable = false, unique = true)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime deleteAt;

    private LocalDateTime updatedAt;

    public User( String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public void update(String name, String email, String passwordHash, Boolean active) {
        if (name != null) {
            this.name = name;
        }
        if (email != null) {
            this.email = email;
        }
        if (passwordHash != null) {
            this.passwordHash = passwordHash;
        }
        if (active != null) {
            this.active = active;
        }
        this.updatedAt = LocalDateTime.now();
    }
}



