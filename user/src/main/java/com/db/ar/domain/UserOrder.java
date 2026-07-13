package com.db.ar.domain;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_orders")
public class UserOrder {

    @Id
    private Long orderId;

    private BigDecimal totalAmount;
    private Long userId;
    private Long vendorId;

    @Enumerated(EnumType.STRING)
    private OrderStatusRep orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatusRep  paymentStatus;

    private LocalDateTime createdAt;
}
