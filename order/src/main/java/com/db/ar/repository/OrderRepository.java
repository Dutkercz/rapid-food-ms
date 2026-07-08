package com.db.ar.repository;

import br.com.db.rapid_food_api.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("""
           SELECT o FROM Order o
           where o.user.id = :userId
           ORDER BY o.createdAt DESC
           """)
    Page<Order> findAllByUserOrderedDesc(@Param("userId") UUID userId, Pageable pageable);
}
