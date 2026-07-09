package com.db.ar.repository;

import com.db.ar.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByVendorId(UUID vendorId);

    List<Product> findAllByVendorId(UUID vendorId, Pageable pageable);

    Optional<Product> findByIdAndVendorId(UUID productId, UUID vendorId);
}
