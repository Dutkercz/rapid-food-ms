CREATE TABLE orders(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    total_amount DECIMAL(10,2),
    observation VARCHAR(255),
    order_status VARCHAR(30) NOT NULL CHECK (
        status IN ('CREATED', 'SENT_TO_VENDOR', 'ACCEPTED', 'PREPARING', 'DELIVERED', 'CANCELED')),
    payment_status VARCHAR(30) NOT NULL CHECK (
        status IN ( 'PENDING', 'APPROVED', 'REJECTED', 'CANCELLED', 'REFUNDED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    vendor_id BIGINT NOT NULL,
    vendor_name VARCHAR(255) NOT NULL

);