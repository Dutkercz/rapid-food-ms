package com.db.ar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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