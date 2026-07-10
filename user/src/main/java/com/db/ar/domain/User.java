package com.db.ar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
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

    public User() {
        init();
    }

    private void init() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }
}



