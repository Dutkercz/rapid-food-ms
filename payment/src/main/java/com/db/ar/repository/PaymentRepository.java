package com.db.ar.repository;

import com.db.ar.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    //Optional<Payment> findByOrderId(UUID orderId);
}
