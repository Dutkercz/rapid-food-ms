package com.db.ar.repository;

import com.db.ar.domain.VendorOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorOrderRepository extends JpaRepository<VendorOrder, Long> {
}
