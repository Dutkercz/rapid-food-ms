package com.db.ar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID vendorId;

    @Column(nullable = false)
    private String vendorName;

    @Column(nullable = false)
    private String productName;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, unique = true)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Product() {
        init();
    }

    private void init() {
        createdAt = LocalDateTime.now();
    }
}
