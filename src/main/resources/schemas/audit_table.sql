CREATE TABLE audit_log (
                           id BIGSERIAL PRIMARY KEY,
                           customer_id VARCHAR(50),
                           action VARCHAR(50) NOT NULL,         -- LOGIN, ADD_FAVORITE, UPDATE_FAVORITE, DELETE_FAVORITE
                           resource VARCHAR(50),                -- FAVORITE_ACCOUNT
                           resource_id BIGINT,                  -- favorite_account.id
                           status VARCHAR(20) NOT NULL,         -- SUCCESS / FAIL
                           message TEXT,                        -- error or info
                           ip_address VARCHAR(50),
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);