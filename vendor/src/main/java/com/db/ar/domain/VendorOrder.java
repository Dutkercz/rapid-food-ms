package com.db.ar.domain;

import com.db.ar.messaging.representation.OrderStatusRep;
import com.db.ar.messaging.representation.PaymentStatusRep;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorOrder {

    @Id
    private Long orderId;

    private BigDecimal totalAmount;
    private Long vendorId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatusRep orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatusRep paymentStatus;

    private LocalDateTime createdAt;

}
