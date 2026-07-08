package com.db.ar.domain;

import br.com.db.rapid_food_api.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }


}
