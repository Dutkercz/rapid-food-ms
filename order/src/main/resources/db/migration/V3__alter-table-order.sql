-- Adicionar chave de pagamento em orders

ALTER TABLE orders ADD COLUMN payment_key VARCHAR(255) NOT NULL AFTER payment_status;