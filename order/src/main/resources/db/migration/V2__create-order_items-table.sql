CREATE TABLE order_items(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(150),
    quantity INT NOT NULL CHECK (quantity > 0),
    product_price DECIMAL(10, 2),
    total DECIMAL(10, 2),

    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
);
