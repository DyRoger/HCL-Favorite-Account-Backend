CREATE TABLE bank_mapping (
                              code VARCHAR(10) PRIMARY KEY,   -- extracted from IBAN
                              bank_name VARCHAR(150) NOT NULL,
                              is_active BOOLEAN NOT NULL DEFAULT TRUE,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);