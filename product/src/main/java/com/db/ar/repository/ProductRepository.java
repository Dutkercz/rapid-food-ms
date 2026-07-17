package com.db.ar.repository;

import com.db.ar.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByVendorId(Long vendorId);

    List<Product> findAllByVendorId(Long vendorId, Pageable pageable);

    Optional<Product> findByIdAndVendorId(Long productId, Long vendorId);
}
