package com.db.ar.repository;

import com.db.ar.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByIdAndPaymentKey(Long id, String paymentKey);
}
