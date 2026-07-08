package com.db.ar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String cnpj;

    @Column(nullable = false)
    private Boolean active;

    public  void deactivate() {
        if (!this.active) {
            throw new IllegalStateException("Restaurante está inativo no momento");
        }

        this.active = false;
    }
}