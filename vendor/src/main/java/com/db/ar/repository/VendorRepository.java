package com.db.ar.repository;

import com.db.ar.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {
    boolean existsByCnpj(String cnpj);
}