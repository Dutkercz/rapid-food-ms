-- Adiciona coluna de paymentId
ALTER TABLE orders ADD COLUMN payment_id BIGINT AFTER order_status;