package com.db.ar.repository;

import com.db.ar.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

    //Optional<Payment> findByOrderId(UUID orderId);
}
