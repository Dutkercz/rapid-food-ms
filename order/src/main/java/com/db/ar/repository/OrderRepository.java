package com.db.ar.repository;

import com.db.ar.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("""
           SELECT o FROM Order o
           where o.userId = :userId
           ORDER BY o.createdAt DESC
           """)
    Page<Order> findAllByUserOrderedDesc(@Param("userId") UUID userId, Pageable pageable);
}
