CREATE TABLE favorite_account (
                                  id BIGSERIAL PRIMARY KEY,
                                  customer_id VARCHAR(50) NOT NULL,
                                  account_name VARCHAR(100) NOT NULL,
                                  iban VARCHAR(20) NOT NULL,
                                  bank_code VARCHAR(10) NOT NULL,

                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP,

                                  CONSTRAINT fk_fav_user
                                      FOREIGN KEY (customer_id) REFERENCES bank_user(customer_id),

                                  CONSTRAINT fk_fav_bank
                                      FOREIGN KEY (bank_code) REFERENCES bank_mapping(code),

                                  CONSTRAINT uq_customer_iban
                                      UNIQUE (customer_id, iban)
);