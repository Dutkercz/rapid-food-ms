package com.db.ar.domain;

import br.com.db.rapid_food_api.order.domain.enums.OrderStatus;
import br.com.db.rapid_food_api.user.domain.User;
import br.com.db.rapid_food_api.vendors.domain.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column
    private String observation;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    private User user;

    @ManyToOne
    private Vendor vendor;

    public Order() {
        init();
    }

    private void init() {
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
        this.items = new ArrayList<>();
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
        if (this.status.cantBeCancelled()) {
            throw new IllegalStateException("Order cant be cancelled with status " + this.status);
        }

        this.status = OrderStatus.CANCELED;
        this.observation = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPreparing() {
        this.status = OrderStatus.PREPARING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDelivered() {
        this.status = OrderStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }
}
