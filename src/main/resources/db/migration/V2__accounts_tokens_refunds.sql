ALTER TABLE orders ADD COLUMN refunded BOOLEAN NOT NULL DEFAULT FALSE;
CREATE TABLE password_reset_tokens (id BIGINT AUTO_INCREMENT PRIMARY KEY, token VARCHAR(128) NOT NULL UNIQUE, account_id BIGINT NOT NULL, expires_at TIMESTAMP(6) NOT NULL, used BOOLEAN NOT NULL DEFAULT FALSE, CONSTRAINT fk_reset_account FOREIGN KEY (account_id) REFERENCES accounts(id));
CREATE INDEX idx_reset_token ON password_reset_tokens(token);
