CREATE TABLE bank_user (
                      id BIGSERIAL PRIMARY KEY,
                      customer_id VARCHAR(50) NOT NULL UNIQUE,
                      name VARCHAR(100) NOT NULL,
                      status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE/INACTIVE
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP
);
