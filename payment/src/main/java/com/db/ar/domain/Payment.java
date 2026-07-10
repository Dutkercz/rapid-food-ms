package com.db.ar.domain;

import com.db.ar.domain.enums.PaymentMethod;
import com.db.ar.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //order id aqui

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Payment() {
        this.status = PaymentStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

//    public Payment(Order order, PaymentMethod paymentMethod) {
//        this.order = order;
//        this.amount = order.getTotalAmount();
//        this.paymentMethod = paymentMethod;
//        this.status = PaymentStatus.CREATED;
//        this.createdAt = LocalDateTime.now();
//    }

    public void authorize() {
        this.status = PaymentStatus.AUTHORIZED;
        this.updatedAt = LocalDateTime.now();
    }

    public void pay() {
        this.status = PaymentStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void refund() {
        this.status = PaymentStatus.REFUNDED;
        this.updatedAt = LocalDateTime.now();
    }
}
