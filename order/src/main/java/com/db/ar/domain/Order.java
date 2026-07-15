package com.db.ar.domain;


import com.db.ar.domain.enums.PaymentStatus;
import com.db.ar.domain.enums.OrderStatus;
import com.db.ar.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column
    private String observation;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(updatable = false, nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(updatable = false, nullable = false)
    private Long vendorId;

    @Column(nullable = false)
    private String vendorName;

    public Order() {
        init();
    }

    private void init() {
        this.orderStatus = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
        if(items == null){
            this.items = new ArrayList<>();
        }
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
        item.setOrder(null);
    }

    public void cancel(String reason) {
        if (this.orderStatus.cantBeCancelled()) {
            throw new IllegalStateException("Order cant be cancelled with status " + this.orderStatus);
        }
        this.orderStatus = OrderStatus.CANCELED;
        this.paymentStatus = PaymentStatus.CANCELLED;
        this.observation = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPreparing() {
        if (!this.paymentStatus.verifyToPrepareOrder()) {
            throw new IllegalStateException("Order cant be started to prepare with payment status: " + this.paymentStatus);
        }
        this.orderStatus = OrderStatus.PREPARING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDelivered() {
        this.orderStatus = OrderStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }
}
