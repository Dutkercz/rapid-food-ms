package com.db.ar.domain;

import com.db.ar.messaging.representation.PaymentMethod;
import com.db.ar.messaging.representation.OrderStatusRep;
import com.db.ar.messaging.representation.PaymentStatusRep;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long vendorId;

    @Enumerated(EnumType.STRING)
    private OrderStatusRep orderStatus;

    @Column(nullable = false)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatusRep paymentStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
