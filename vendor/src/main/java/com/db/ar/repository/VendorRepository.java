package com.db.ar.repository;

import com.db.ar.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorRepository extends JpaRepository<Vendor, Long> {
    boolean existsByCnpj(String cnpj);
}